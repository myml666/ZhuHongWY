package com.zhwy.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.zhwy.app.R;
import com.zhwy.app.activity.base.BaseActivity;
import com.zhwy.app.utils.CircularAnimUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.activity
 * @ClassName: ChoiceActivity
 * @Description: java类作用描述 ：身份选择的界面
 * @CreateDate: 2019/3/11 11:25
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/3/11 11:25
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class ChoiceActivity extends BaseActivity {
    public static final String IDENTITY = "identity";//身份
    @BindView(R.id.activity_choice_layout_yz)
    RelativeLayout activityChoiceLayoutYz;
    @BindView(R.id.activity_choice_layout_wy)
    RelativeLayout activityChoiceLayoutWy;

    @Override
    protected int LayoutRes() {
        return R.layout.activity_choice;
    }

    @Override
    protected boolean isHaveTitle() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarVisibility(this,false);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.activity_choice_layout_yz, R.id.activity_choice_layout_wy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_choice_layout_yz:
                SPUtils.getInstance().put(IDENTITY, "yz");
                CircularAnimUtil.startActivity(ChoiceActivity.this, getStartActivityIntent(LoginActivity.class), activityChoiceLayoutYz, R.color.colorWhite);
                break;
            case R.id.activity_choice_layout_wy:
                SPUtils.getInstance().put(IDENTITY, "wy");
                CircularAnimUtil.startActivity(ChoiceActivity.this, getStartActivityIntent(LoginActivity.class), activityChoiceLayoutWy, R.color.colorWhite);
                break;
        }
    }
}
