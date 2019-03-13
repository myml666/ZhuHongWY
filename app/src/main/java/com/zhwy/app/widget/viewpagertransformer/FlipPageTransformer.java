package com.zhwy.app.widget.viewpagertransformer;

import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * @ProjectName: Hehuidai
 * @Package: com.hehuidai.application.widget.viewpagertransformer
 * @ClassName: FlipPageTransformer
 * @Description: java类作用描述 ：
 * @Author: 作者名：lml
 * @CreateDate: 2018/12/5 15:34
 * @UpdateUser: 更新者：
 * @UpdateDate: 2018/12/5 15:34
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class FlipPageTransformer extends BasePageTransformer {
    private static final float ROTATION = 180.0f;

    @Override
    public void handleInvisiblePage(View view, float position) {
    }

    @Override
    public void handleLeftPage(View view, float position) {
        ViewCompat.setTranslationX(view, -view.getWidth() * position);
        float rotation = (ROTATION * position);
        ViewCompat.setRotationY(view, rotation);

        if (position > -0.5) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void handleRightPage(View view, float position) {
        ViewCompat.setTranslationX(view, -view.getWidth() * position);
        float rotation = (ROTATION * position);
        ViewCompat.setRotationY(view, rotation);

        if (position < 0.5) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }
}
