package com.zhwy.app.fragment.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @ProjectName: Hehuidai
 * @Package: com.hehuidai.application.base
 * @ClassName: BaseFragment
 * @Description: java类作用描述：Fragment基类
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public abstract class BaseFragment extends Fragment {
    @LayoutRes
    protected abstract int LayoutRes();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (LayoutRes() != 0) {
            return inflater.inflate(LayoutRes(), null, false);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    /**
     * @method  getStartActivityIntent
     * @description 用于返回跳转Activity的Intent
     * @author: LML
     * @param clazz Activity的类名
     * @return android.content.Intent
     */
    protected Intent getStartActivityIntent(Class clazz){
        return new Intent(getContext(),clazz);
    }
    /**
     * @method  gotoActivity
     * @description 描述一下方法的作用：跳转Activity
     * @date: 2018/12/4 9:53
     * @author: 作者名：lml
     * @param clazz Activity类名
     * @return void
     */
    protected void gotoActivity(Class clazz){
        startActivity(new Intent(getContext(),clazz));
    }
}
