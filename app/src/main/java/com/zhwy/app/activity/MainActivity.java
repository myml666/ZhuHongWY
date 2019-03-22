package com.zhwy.app.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.zhihu.matisse.Matisse;
import com.zhwy.app.R;
import com.zhwy.app.activity.base.BaseActivity;
import com.zhwy.app.adapter.MainFragmentPagerAdapter;
import com.zhwy.app.beans.TabEntity;
import com.zhwy.app.fragment.PYQFragment;
import com.zhwy.app.fragment.callback.ChoicePhotoCallback;
import com.zhwy.app.fragment.owner.OwnerMainHomeFragment;
import com.zhwy.app.fragment.owner.OwnerMainMineFragment;
import com.zhwy.app.fragment.property.PropertyMainHomeFragment;
import com.zhwy.app.fragment.property.PropertyMainMineFragment;
import com.zhwy.app.utils.FileUtil;
import com.zhwy.app.utils.ValuesUtils;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 物业主界面
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.activity_main_viewpager)
    ViewPager activityMainViewpager;
    @BindView(R.id.activity_main_bottomlayout)
    CommonTabLayout activityMainBottomlayout;
    private String[] mTitles = {"首页","圈子", "我的"};
    private int[] mIconUnselectIds = {
            R.drawable.ic_mainhome,R.drawable.ic_qz, R.drawable.ic_mainmine};
    private int[] mIconSelectIds = {
            R.drawable.ic_mainhomeselect,R.drawable.ic_qzselect, R.drawable.ic_mainmineselect};
    private ArrayList<CustomTabEntity> mTabEntities ;
    private ArrayList<Fragment> mFragments;
    private MainFragmentPagerAdapter mainFragmentPagerAdapter;
    private ChoicePhotoCallback choicePhotoCallback;

    private ChoicePhotoCallback getChoicePhotoCallback() {
        return choicePhotoCallback;
    }
    /**
     * 设置选择图片的回调事件
     * @param choicePhotoCallback
     */
    public void setChoicePhotoCallback(ChoicePhotoCallback choicePhotoCallback) {
        this.choicePhotoCallback = choicePhotoCallback;
    }
    @Override
    protected int LayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isHaveTitle() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        BarUtils.setStatusBarAlpha(this,0);//状态栏透明
        ButterKnife.bind(this);
        initDatas();
        initListener();
    }

    private void initListener() {
        activityMainBottomlayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                activityMainViewpager.setCurrentItem(position);
                switch (position){
                    case 0:
                        setTitle("首页",R.color.colorWhite);
                        break;
                    case 1:
                        setTitle("圈子",R.color.colorWhite);
                        break;
                    case 2:
                        setTitle("我的",R.color.colorWhite);
                        break;
                }
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
                switch (position){
                    case 0:
                        setTitle("首页",R.color.colorWhite);
                        break;
                    case 1:
                        setTitle("圈子",R.color.colorWhite);
                        break;
                    case 2:
                        setTitle("我的",R.color.colorWhite);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        ActivityUtils.startHomeActivity();
    }

    private void initDatas() {
        setTitle("首页",R.color.colorWhite);
        setBackIcon(R.drawable.ic_back_white,false);//隐藏返回按钮
        if(mFragments==null){
            mFragments=new ArrayList<>();
        }
        mFragments.clear();
        String identity = ValuesUtils.getIdentity();
        if(identity.equals(ValuesUtils.IDENTITY_WY)){
            mFragments.add(new PropertyMainHomeFragment());
            mFragments.add(new PYQFragment());
            mFragments.add(new PropertyMainMineFragment());
        }else {
            mFragments.add(new OwnerMainHomeFragment());
            mFragments.add(new PYQFragment());
            mFragments.add(new OwnerMainMineFragment());
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
                            if(choicePhotoCallback!=null){
                                choicePhotoCallback.onPhotoChoice(file);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                        }
                    }).launch();
        }
    }
}
