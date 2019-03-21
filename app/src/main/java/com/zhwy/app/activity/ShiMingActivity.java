package com.zhwy.app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zhwy.app.R;
import com.zhwy.app.activity.base.BaseActivity;
import com.zhwy.app.api.Api;
import com.zhwy.app.utils.RetrofitUtils;
import com.zhwy.app.widget.TakePictureCameraView;
import com.zhwy.app.widget.TakePictureMaskView;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.activity
 * @ClassName: ShiMingActivity
 * @Description: java类作用描述 ：实名认证
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class ShiMingActivity extends BaseActivity implements TakePictureCameraView.TakePictureCallBack{
    private TakePictureCameraView camera;
    private TakePictureMaskView mask;
    private View viewTakepicture;
    private AlertDialog alertDialog;
    @Override
    protected int LayoutRes() {
        return R.layout.activity_shiming;
    }

    @Override
    protected boolean isHaveTitle() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        camera = (TakePictureCameraView) findViewById(R.id.camera);
        mask = (TakePictureMaskView) findViewById(R.id.mask);
        viewTakepicture = (View) findViewById(R.id.view_takepicture);
        camera.setTakePictureCallBack(this);
        viewTakepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture();//拍照
            }
        });
    }

    @Override
    public void onShutter() {

    }

    @Override
    public void onPictureTaken(boolean isSuccess, Bitmap filepath) {
        if(isSuccess){
            showLoadingDialog();
            paseInfo(filepath);
        }else {
            ToastUtils.showShort("获取图像失败，请重试");
        }
    }

    /**
     * 展示加载框
     */
    private void showLoadingDialog() {
        if(alertDialog==null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View inflate = View.inflate(this, R.layout.dialog_loading, null);
            TextView textView = inflate.findViewById(R.id.tv_msg);
            textView.setText("识别中请稍候");
            builder.setView(inflate);
            alertDialog = builder.create();
        }
        alertDialog.show();
        alertDialog.getWindow().setLayout( ScreenUtils.getScreenWidth()/4*2, LinearLayout.LayoutParams.WRAP_CONTENT);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(camera!=null){
            camera.stopPreview();
        }
    }

    /**
     * 解析身份证信息
     * @param filepath
     */
    private void paseInfo(Bitmap filepath) {
        try {
            final byte[] img = ConvertUtils.bitmap2Bytes(filepath, Bitmap.CompressFormat.JPEG);
            String base64img = new String(Base64.encodeBase64(img), "UTF-8");
            RetrofitUtils.getInstance().getApiServier(Api.class)
                    .ocrIdcard(base64img)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<JsonObject>() {
                        @Override
                        public void accept(JsonObject jsonObject) throws Exception {
                            if(alertDialog!=null){
                                alertDialog.dismiss();
                            }
                            LogUtils.eTag("测试",jsonObject.toString());
                            JsonElement code = jsonObject.get("code");
                            String asString = code.getAsString();
                            if(asString.trim().equals("0")){
                                showIdCardInfo(jsonObject);
                            }else {
                                ToastUtils.showShort("识别失败，请重试");
                                if(camera!=null){
                                    camera.startPreview();
                                }
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            if(alertDialog!=null){
                                alertDialog.dismiss();
                            }
                            LogUtils.eTag("测试",throwable.getMessage());
                            ToastUtils.showShort("识别失败，请重试");
                            if(camera!=null){
                                camera.startPreview();
                            }
                        }
                    });
        } catch (UnsupportedEncodingException e) {
            if(alertDialog!=null){
                alertDialog.dismiss();
            }
            LogUtils.eTag("测试",e.getMessage());
            ToastUtils.showShort("识别失败，请重试");
            if(camera!=null){
                camera.startPreview();
            }
        }
    }

    /**
     * 展示身份证信息
     */
    private void showIdCardInfo(JsonObject info) {
        final JsonObject data = info.getAsJsonObject("data");
        final String name = data.get("name").getAsString();
        final String idnum = data.get("id_number").getAsString();
        AlertDialog.Builder builder = new AlertDialog.Builder(ShiMingActivity.this);
        builder.setTitle("提示");
        builder.setMessage("姓名："+name+"\n"+"身份证号："+idnum);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //进行实名认证
                AVUser currentUser = AVUser.getCurrentUser();
                currentUser.put("isRealName",true);
                currentUser.put("idCardNum",idnum);
                currentUser.put("realName",name);
                currentUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ShiMingActivity.this);
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
                                    if(camera!=null){
                                        camera.startPreview();
                                    }
                                }
                            });
                            alertDialog.show();
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("重新识别", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(camera!=null){
                    camera.startPreview();
                }
            }
        });
        alertDialog.show();
    }
}
