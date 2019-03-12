package com.zhwy.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.blankj.utilcode.util.BarUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.zhwy.app.R;
import com.zhwy.app.activity.base.BaseActivity;
import com.zhwy.app.adapter.MainFragmentPagerAdapter;
import com.zhwy.app.beans.TabEntity;
import com.zhwy.app.fragment.MainMineFragment;
import com.zhwy.app.fragment.owner.OwnerMainHomeFragment;
import com.zhwy.app.fragment.property.PropertyMainHomeFragment;
import com.zhwy.app.utils.ValuesUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 物业主界面
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.activity_main_viewpager)
    ViewPager activityMainViewpager;
    @BindView(R.id.activity_main_bottomlayout)
    CommonTabLayout activityMainBottomlayout;
    private String[] mTitles = {"首页", "我的"};
    private int[] mIconUnselectIds = {
            R.drawable.ic_mainhome, R.drawable.ic_mainmine};
    private int[] mIconSelectIds = {
            R.drawable.ic_mainhomeselect, R.drawable.ic_mainmineselect};
    private ArrayList<CustomTabEntity> mTabEntities ;
    private ArrayList<Fragment> mFragments;
    private MainFragmentPagerAdapter mainFragmentPagerAdapter;
    @Override
    protected int LayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isHaveTitle() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarAlpha(this,0);//状态栏透明
        ButterKnife.bind(this);
        initDatas();
        initListener();
    }

    private void initListener() {
        activityMainBottomlayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                activityMainViewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        activityMainViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                activityMainBottomlayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initDatas() {
        if(mFragments==null){
            mFragments=new ArrayList<>();
        }
        mFragments.clear();
        String identity = ValuesUtils.getIdentity();
        if(identity.equals(ValuesUtils.IDENTITY_WY)){
            mFragments.add(new PropertyMainHomeFragment());
            mFragments.add(new MainMineFragment());
        }else {
            mFragments.add(new OwnerMainHomeFragment());
            mFragments.add(new MainMineFragment());
        }
        if(mTabEntities==null){
            mTabEntities=new ArrayList<>();
        }
        mTabEntities.clear();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        activityMainBottomlayout.setTabData(mTabEntities);
        mainFragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        activityMainViewpager.setAdapter(mainFragmentPagerAdapter);//设置适配器数据
        activityMainViewpager.setOffscreenPageLimit(2);
    }

}
