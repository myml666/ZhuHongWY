package com.zhwy.app.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhwy.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.widget
 * @ClassName: PagerIndefitor
 * @Description: java类作用描述 ：指示器
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class PageIndicator implements ViewPager.OnPageChangeListener {
    private int mPageCount;//页数
    private List<ImageView> mImgList;//保存img总个数
    private int img_select;
    private int img_unSelect;

    public PageIndicator(Context context, LinearLayout linearLayout, int pageCount) {
        this.mPageCount = pageCount;

        mImgList = new ArrayList<>();
        img_select = R.drawable.shape_indicatorselector;
        img_unSelect = R.drawable.shape_indicatorunselector;

        for (int i = 0; i < mPageCount; i++) {
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            //为小圆点左右添加间距
            params.leftMargin = 10;
            params.rightMargin = 10;
            //给小圆点一个默认大小
            params.height = 20;
            params.width = 20;
            if (i == 0) {
                imageView.setBackgroundResource(img_select);
            } else {
                imageView.setBackgroundResource(img_unSelect);
            }
            //为LinearLayout添加ImageView
            linearLayout.addView(imageView, params);
            mImgList.add(imageView);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < mPageCount; i++) {
            //选中的页面改变小圆点为选中状态，反之为未选中
            if ((position % mPageCount) == i) {
                (mImgList.get(i)).setBackgroundResource(img_select);
            } else {
                (mImgList.get(i)).setBackgroundResource(img_unSelect);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
