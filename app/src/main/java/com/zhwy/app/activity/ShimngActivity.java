package com.zhwy.app.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.blankj.utilcode.util.ToastUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhwy.app.R;
import com.zhwy.app.activity.base.BaseActivity;
import com.zhwy.app.utils.GetTextUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.activity
 * @ClassName: ChoiceActivity
 * @Description: java类作用描述 ：实名认证
 * @CreateDate: 2019/3/11 11:25
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/3/11 11:25
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class ShimngActivity extends BaseActivity {

    @BindView(R.id.activity_shimings_et_name)
    EditText activityShimingsEtName;
    @BindView(R.id.activity_shiming_et_number)
    EditText activityShimingEtNumber;
    private RxPermissions rxPermissions;
    @Override
    protected int LayoutRes() {
        return R.layout.activity_shiming;
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
        setTitle("实名认证", R.color.colorWhite);
        rxPermissions = new RxPermissions(this);
        //是否认证
        boolean isRealName = AVUser.getCurrentUser().getBoolean("isRealName");
        if(isRealName){
            activityShimingEtNumber.setText(AVUser.getCurrentUser().getString("idCardNum"));
            activityShimingsEtName.setText(AVUser.getCurrentUser().getString("realName"));
        }
    }


    @OnClick({R.id.activity_shimings_img_takepicture, R.id.activity_shiming_tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_shimings_img_takepicture:
                rxPermissions.requestEachCombined(Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Permission>() {
                            @Override
                            public void accept(Permission permission) throws Exception {
                                if (permission.granted) {
                                    startActivityForResult(getStartActivityIntent(ShiMingTakePictureActivity.class),0);
                                } else if (permission.shouldShowRequestPermissionRationale) {
                                    ToastUtils.showShort("您已拒绝权限申请");
                                } else {
                                    ToastUtils.showShort("您已拒绝权限申请，请前往设置>应用管理>权限管理打开权限");
                                }
                            }
                        });
                break;
            case R.id.activity_shiming_tv_submit:
                shiming();
                break;
        }
    }

    private void shiming() {
        //进行实名认证
        String name = GetTextUtils.getText(activityShimingsEtName);
        String idnum = GetTextUtils.getText(activityShimingEtNumber);
        if(TextUtils.isEmpty(name)){
            ToastUtils.showShort("请输入真实姓名");
            return;
        }
        if(TextUtils.isEmpty(idnum)){
            ToastUtils.showShort("请输入身份证号");
            return;
        }
        AVUser currentUser = AVUser.getCurrentUser();
        currentUser.put("isRealName",true);
        currentUser.put("idCardNum",idnum);
        currentUser.put("realName",name);
        currentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShimngActivity.this);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                if(e==null){
                    builder.setTitle("提示");
                    builder.setMessage("认证成功");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    alertDialog.show();
                }else {
                    builder.setTitle("提示");
                    builder.setMessage("认证失败，请重试");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0&&resultCode==1){
            activityShimingsEtName.setText(data.getStringExtra("name"));
            activityShimingEtNumber.setText(data.getStringExtra("idnum"));
        }
    }
}
