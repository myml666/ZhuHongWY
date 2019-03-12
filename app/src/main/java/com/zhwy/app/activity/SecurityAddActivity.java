package com.zhwy.app.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhwy.app.R;
import com.zhwy.app.activity.base.BaseActivity;
import com.zhwy.app.utils.FileUtil;
import com.zhwy.app.utils.GetTextUtils;
import com.zhwy.app.utils.ValuesUtils;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.activity
 * @ClassName: LoginActivity
 * @Description: java类作用描述 ：安保人员录入
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class SecurityAddActivity extends BaseActivity {

    @BindView(R.id.activity_addsecurity_img_photo)
    ImageView activityAddsecurityImgPhoto;
    @BindView(R.id.activity_addsecurity_et_name)
    EditText activityAddsecurityEtName;
    private RxPermissions rxPermissions;
    private File mPhtoFile;
    @Override
    protected int LayoutRes() {
        return R.layout.activity_addsecurity;
    }

    @Override
    protected boolean isHaveTitle() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initDatas();
    }

    private void initDatas() {
        setTitle("安保录入", R.color.colorWhite);
        if(rxPermissions==null){
            rxPermissions = new RxPermissions(this);
        }
    }

    @OnClick({R.id.activity_addsecurity_img_photo, R.id.activity_addsecurity_tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_addsecurity_img_photo:
                //选择头像
                choicePhoto();
                break;
            case R.id.activity_addsecurity_tv_submit:
                //提交信息
                submitInfo();
                break;
        }
    }

    /**
     * 提交信息
     */
    private void submitInfo() {
        String name = GetTextUtils.getText(activityAddsecurityEtName);
        if(TextUtils.isEmpty(name)){
            ToastUtils.showShort("请输入安保人员姓名");
            return;
        }
        if(mPhtoFile==null){
            ToastUtils.showShort("请选择安保人员头像");
            return;
        }
        try {
            AVFile f = AVFile.withAbsoluteLocalPath("temp.png", mPhtoFile.getAbsolutePath());
            AVObject security = new AVObject("Security");// 构建对象
            security.put("photo",f);
            security.put("name",name);
            security.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if(e==null){
                        ToastUtils.showShort("提交成功");
                        finish();
                    }else {
                        ToastUtils.showShort("提交失败请重试");
                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 选择头像
     */
    private void choicePhoto() {
        rxPermissions.requestEachCombined(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            Matisse.from(SecurityAddActivity.this)
                                    .choose(MimeType.ofImage())
                                    .countable(true)
                                    .captureStrategy(new CaptureStrategy(true, "com.zhwy.app.fileprovider")) //是否拍照功能，并设置拍照后图片的保存路径
                                    .maxSelectable(1)
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                    .thumbnailScale(0.8f)
                                    .forResult(ValuesUtils.REQUEST_CODE_CHOOSE);
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            ToastUtils.showShort("您已拒绝权限申请");
                        } else {
                            ToastUtils.showShort("您已拒绝权限申请，请前往设置>应用管理>权限管理打开权限");
                        }
                    }
                });
    }

    /**
     * 选择头像返回的数据
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ValuesUtils.REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            File fileByUri = FileUtil.getFileByUri(Matisse.obtainResult(data).get(0), this);
//            压缩文件
            Luban.with(this)
                    .load(fileByUri)
                    .ignoreBy(100)
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                        }
                    })
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onSuccess(File file) {
                            //转换成功后的文件
                            mPhtoFile = file;
                            Glide.with(SecurityAddActivity.this).load(file).into(activityAddsecurityImgPhoto);
                        }

                        @Override
                        public void onError(Throwable e) {
                        }
                    }).launch();
        }
    }

}
