package com.zhwy.app.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.zhwy.app.R;
import com.zhwy.app.fragment.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.fragment
 * @ClassName: MainMineFragment
 * @Description: java类作用描述 ：主页我的页面
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class MainMineFragment extends BaseFragment {
    @Override
    protected int LayoutRes() {
        return R.layout.fragment_mainmine;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }
}
