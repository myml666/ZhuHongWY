package com.zhwy.app.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhwy.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.widget
 * @ClassName: WelcomTopView
 * @Description: java类作用描述 ：欢迎页顶部View
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class WelcomTopView extends FrameLayout {
    @BindView(R.id.view_welcomtop_tv_title)
    TextView viewWelcomtopTvTitle;
    @BindView(R.id.view_welcomtop_tv_desc)
    TextView viewWelcomtopTvDesc;
    @BindView(R.id.view_welcomtop_img)
    ImageView viewWelcomtopImg;

    public WelcomTopView(@NonNull Context context) {
        this(context, null);
    }

    public WelcomTopView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WelcomTopView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.view_welcome, this);
        ButterKnife.bind(this);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setWelcomTopTitle(String title) {
        viewWelcomtopTvTitle.setText(title);
    }

    /**
     * 设置描述
     */
    public void setWelcomTopDesc(String desc) {
        viewWelcomtopTvDesc.setText(desc);
    }
    /**
     * 设置描述
     */
    public void setImage(@DrawableRes int img) {
        viewWelcomtopImg.setImageResource(img);
    }
}
