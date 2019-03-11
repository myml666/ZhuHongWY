package com.zhwy.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @ProjectName: Hehuidai
 * @Package: com.hehuidai.application.adapter
 * @ClassName: MainFragmentPagerAdapter
 * @Description: java类作用描述 ：主页Fragment适配器
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public List<Fragment> getmFragments() {
        return mFragments;
    }

    public MainFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments=fragments;

    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
