package com.zhwy.app.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zhwy.app.R;
import com.zhwy.app.activity.base.BaseActivity;
import com.zhwy.app.utils.GetTextUtils;
import com.zhwy.app.utils.ValuesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.activity
 * @ClassName: LoginActivity
 * @Description: java类作用描述 ：登录
 * @CreateDate: 2019/3/11 10:10
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/3/11 10:10
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.activity_login_username)
    EditText activityLoginUsername;
    @BindView(R.id.activity_login_password)
    EditText activityLoginPassword;
    private AlertDialog alertDialog;

    @Override
    protected int LayoutRes() {
        return R.layout.activity_login;
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
        setBackIcon(R.drawable.ic_back_white,false);
        setTitle("登录",R.color.colorWhite);
    }

    @OnClick({R.id.activity_login_tv_regist, R.id.activity_login_tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_login_tv_regist:
                gotoActivity(RegistActivity.class);
                break;
            case R.id.activity_login_tv_login:
                login();
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        if(alertDialog==null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(View.inflate(this,R.layout.dialog_loading,null));
            alertDialog = builder.create();
        }
        alertDialog.show();
        alertDialog.getWindow().setLayout( ScreenUtils.getScreenWidth()/4*2, LinearLayout.LayoutParams.WRAP_CONTENT);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        String userName = GetTextUtils.getText(activityLoginUsername);
        String passWord = GetTextUtils.getText(activityLoginPassword);
        if(TextUtils.isEmpty(userName)){
            ToastUtils.showShort("请输入用户名");
            return;
        }
        if(TextUtils.isEmpty(passWord)){
            ToastUtils.showShort("请输入密码");
            return;
        }
        final String identity = ValuesUtils.getIdentity();//物业还是业主
        AVUser.logInInBackground(identity+userName, passWord, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if(alertDialog!=null){
                    alertDialog.dismiss();
                }
                if(e==null){
                    //登录成功
                    ToastUtils.showShort("登录成功");
                    if(identity.equals(ValuesUtils.IDENTITY_WY)){
//                        物业
                        gotoActivity(MainActivity.class);
                    }else{
//                        业主
                        gotoActivity(MainActivity.class);
                    }
                }else {
                    //登录失败
                    ToastUtils.showShort(e.getMessage());
                }
            }
        });
    }
}
