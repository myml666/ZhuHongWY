package com.zhwy.app.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.BarUtils;
import com.zhwy.app.R;
import com.zhwy.app.activity.base.BaseActivity;
import com.zhwy.app.utils.ValuesUtils;

import java.lang.ref.WeakReference;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.activity
 * @ClassName: SplashActivity
 * @Description: java类作用描述 ：Splash
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class SplashActivity extends BaseActivity {
    private Handler handler;
    private MyRunnable myRunnable;
    @Override
    protected int LayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected boolean isHaveTitle() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarVisibility(this,false);
        initDatas();
    }

    private void initDatas() {
        if(handler==null){
            handler = new Handler();
        }
        if(myRunnable == null){
            myRunnable = new MyRunnable(this);
        }
        handler.postDelayed(myRunnable,2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(handler!=null){
            handler.removeCallbacks(myRunnable);
        }
    }

    private class MyRunnable implements Runnable{
        private WeakReference<SplashActivity> splashActivityWeakReference ;

        public MyRunnable(SplashActivity splashActivity) {
            this.splashActivityWeakReference = new WeakReference<SplashActivity>(splashActivity);
        }

        @Override
        public void run() {
            SplashActivity activity=splashActivityWeakReference.get();
            if(activity!=null){
                if(ValuesUtils.getWelcomFlag()){
                    activity.startActivity(getStartActivityIntent(ChoiceActivity.class));
                }else {
                    activity.startActivity(getStartActivityIntent(WelcomeActivity.class));
                }
                finish();
            }
        }
    }
}
