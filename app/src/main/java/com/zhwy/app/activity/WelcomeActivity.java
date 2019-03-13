package com.zhwy.app.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhwy.app.R;
import com.zhwy.app.activity.base.BaseActivity;
import com.zhwy.app.widget.PageIndicator;
import com.zhwy.app.widget.WelcomTopView;
import com.zhwy.app.widget.viewpagertransformer.ScaleInTransformer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.activity
 * @ClassName: WelcomeActivity
 * @Description: java类作用描述 ：欢迎页
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.activity_welcome_vptop)
    ViewPager activityWelcomeVptop;
    @BindView(R.id.ctivity_welcome_mci)
    LinearLayout activityWelcomeMci;
    private ArrayList<View> mTopViews;
    private ArrayList<View> mBottomViews;
    private String[] mTitles = {"界面简单", "通知公告", "缴费记录"};
    private String[] mDescs = {"简单的界面让您更容易操作", "让业主不错过重大消息", "记录您的每一次缴费"};
    private int [] mImgs = {R.drawable.ic_welcomjj,R.drawable.ic_welcomgg,R.drawable.ic_welcomjf};
    @Override
    protected int LayoutRes() {
        return R.layout.activity_welcome;
    }

    @Override
    protected boolean isHaveTitle() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initDatas();
    }

    private void initDatas() {
        initBanner();
        initListener();
    }

    private void initListener() {
        activityWelcomeVptop.addOnPageChangeListener(new PageIndicator(this, activityWelcomeMci, 3));
    }

    /**
     * 添加轮播数据
     */
    private void initBanner() {
        if (mTopViews == null) {
            mTopViews = new ArrayList<>();
        }
        mTopViews.clear();
        for (int x = 0; x < mTitles.length; x++) {
            WelcomTopView welcomTopView = new WelcomTopView(this);
            welcomTopView.setWelcomTopTitle(mTitles[x]);
            welcomTopView.setWelcomTopDesc(mDescs[x]);
            welcomTopView.setImage(mImgs[x]);
            mTopViews.add(welcomTopView);
        }
        activityWelcomeVptop.setPageTransformer(true, new ScaleInTransformer());
        activityWelcomeVptop.setPageMargin(50);
        activityWelcomeVptop.setOffscreenPageLimit(2);
        activityWelcomeVptop.setAdapter(new MyPagerAdapter(mTopViews));
    }

    @OnClick(R.id.ctivity_welcome_ty)
    public void onViewClicked() {
        gotoActivity(ChoiceActivity.class);
    }


    /**
     * 适配器
     */
    private class MyPagerAdapter extends PagerAdapter {
        private ArrayList<View> mDatas;

        public MyPagerAdapter(ArrayList<View> mDatas) {
            this.mDatas = mDatas;
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mDatas.get(position));
            return mDatas.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
