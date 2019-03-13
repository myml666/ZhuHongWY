package com.zhwy.app.widget.viewpagertransformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @ProjectName: Hehuidai
 * @Package: com.hehuidai.application.widget.viewpagertransformer
 * @ClassName: BasePageTransformer
 * @Description: java类作用描述 ：
 * @Author: 作者名：lml
 * @CreateDate: 2018/12/5 15:32
 * @UpdateUser: 更新者：
 * @UpdateDate: 2018/12/5 15:32
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public abstract class BasePageTransformer implements ViewPager.PageTransformer {
    public void transformPage(View view, float position) {
        if (position < -1.0f) {
            // [-Infinity,-1)
            // This page is way off-screen to the left.
            handleInvisiblePage(view, position);
        } else if (position <= 0.0f) {
            // [-1,0]
            // Use the default slide transition when moving to the left page
            handleLeftPage(view, position);
        } else if (position <= 1.0f) {
            // (0,1]
            handleRightPage(view, position);
        } else {
            // (1,+Infinity]
            // This page is way off-screen to the right.
            handleInvisiblePage(view, position);
        }
    }

    public abstract void handleInvisiblePage(View view, float position);

    public abstract void handleLeftPage(View view, float position);

    public abstract void handleRightPage(View view, float position);
}
