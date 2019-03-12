package com.zhwy.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SignUpCallback;
import com.blankj.utilcode.util.ToastUtils;
import com.zhwy.app.R;
import com.zhwy.app.activity.base.BaseActivity;
import com.zhwy.app.utils.GetTextUtils;
import com.zhwy.app.utils.ValuesUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.activity
 * @ClassName: RegistActivity
 * @Description: java类作用描述 ：
 * @Author: 作者名：lml
 * @CreateDate: 2019/3/11 12:44
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/3/11 12:44
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class RegistActivity extends BaseActivity {
    @BindView(R.id.activity_regist_username)
    EditText activityRegistUsername;
    @BindView(R.id.activity_regist_password)
    EditText activityRegistPassword;
    @BindView(R.id.activity_regist_passwordagain)
    EditText activityRegistPasswordagain;
    @BindView(R.id.activity_regist_wypass)
    EditText activityRegistWypass;

    @Override
    protected int LayoutRes() {
        return R.layout.activity_regist;
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
        setTitle("注册", R.color.colorWhite);
        if(ValuesUtils.getIdentity().equals(ValuesUtils.IDENTITY_WY)){
            activityRegistWypass.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.activity_regist_tv_regist)
    public void onViewClicked() {
        //注册
        final String identity = ValuesUtils.getIdentity();//物业还是业主
        final String userName = GetTextUtils.getText(activityRegistUsername);
        final String passWord = GetTextUtils.getText(activityRegistPassword);
        String passWordAgain = GetTextUtils.getText(activityRegistPasswordagain);
        final String wypass = GetTextUtils.getText(activityRegistWypass);
        if (TextUtils.isEmpty(userName)) {
            ToastUtils.showShort("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(passWord)) {
            ToastUtils.showShort("请输入密码");
            return;
        }
        if (TextUtils.isEmpty(passWordAgain)) {
            ToastUtils.showShort("请输入确认密码");
            return;
        }
        if (!passWordAgain.equals(passWord)) {
            ToastUtils.showShort("两次密码不不一致");
            return;
        }
        if(identity.equals(ValuesUtils.IDENTITY_WY)){
            if (TextUtils.isEmpty(wypass)) {
                ToastUtils.showShort("请输入物业公司密码");
                return;
            }else {
                AVQuery<AVObject> query = new AVQuery<>("PassWord");
                query.whereEqualTo("name","wypass");
                query.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {
                        if(e==null){
                            AVObject avObject = list.get(0);
                            String pass = avObject.getString("pass");
                            if(pass.equals(wypass)){
                                AVUser user = new AVUser();// 新建 AVUser 对象实例
                                user.setUsername(identity + userName);// 设置用户名
                                user.setPassword(passWord);// 设置密码
                                user.signUpInBackground(new SignUpCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        if (e == null) {
                                            // 注册成功
                                            ToastUtils.showShort("注册成功");
                                            finish();
                                        } else {
                                            // 失败的原因可能有多种，常见的是用户名已经存在。
                                            ToastUtils.showShort(e.getMessage());
                                        }
                                    }
                                });
                            }else {
                                ToastUtils.showShort("注册失败，物业公司密码不正确");
                            }

                        }
                    }
                });
            }
        }else {
            AVUser user = new AVUser();// 新建 AVUser 对象实例
            user.setUsername(identity + userName);// 设置用户名
            user.setPassword(passWord);// 设置密码
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        // 注册成功
                        ToastUtils.showShort("注册成功");
                        finish();
                    } else {
                        // 失败的原因可能有多种，常见的是用户名已经存在。
                        ToastUtils.showShort(e.getMessage());
                    }
                }
            });
        }
    }
}
