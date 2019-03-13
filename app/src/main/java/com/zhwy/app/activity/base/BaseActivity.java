package com.zhwy.app.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.zhwy.app.R;

/**
 * @ClassName: BaseActivity
 * @Description: java类作用描述：Activity基类
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected ImageView mImageView_Back,mImageView_RightIcon;
    private TextView mTextView_Title,mTextView_RightTitle;
    private RelativeLayout mRelativeLayout_Container;
    @LayoutRes
    protected abstract int LayoutRes();
    protected abstract boolean isHaveTitle();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LayoutRes());
        BarUtils.setStatusBarColor(this, getResources().getColor(R.color.colorMain),0);
        if(isHaveTitle()){
            mImageView_Back=findViewById(R.id.view_title_img_back);
            mTextView_Title=findViewById(R.id.view_title_tv_zTitle);
            mTextView_RightTitle=findViewById(R.id.view_title_tv_fTitle);
            mImageView_RightIcon=findViewById(R.id.view_title_img_rightIcon);
            mRelativeLayout_Container=findViewById(R.id.view_title_container);
            mImageView_Back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }
    protected void setTitle(@NonNull String title, @ColorRes int titleColor){
        if(isHaveTitle()){
            mTextView_Title.setText(title);
            if(titleColor!=0){
                mTextView_Title.setTextColor(getResources().getColor(titleColor));
            }
        }
    }
    /**
     * @method  setTitleShow
     * @description 描述一下方法的作用：设置右标题是否显示
     * @date: 2018/12/7 15:56
     * @author: 作者名：lml
     * @param isShow 是否显示
     * @return void
     */
    protected void setRightTitleShow(boolean isShow){
        if(isHaveTitle()){
            if(isShow){
                mTextView_RightTitle.setVisibility(View.VISIBLE);
            }else {
                mTextView_RightTitle.setVisibility(View.GONE);
            }
        }
    }
    protected void setRightTitle(@NonNull String title, @ColorRes int titleColor, @Nullable View.OnClickListener onClickListener){
        if(isHaveTitle()){
            mImageView_RightIcon.setVisibility(View.GONE);
            mTextView_RightTitle.setVisibility(View.VISIBLE);
            mTextView_RightTitle.setText(title);
            mTextView_RightTitle.setTextColor(getResources().getColor(titleColor));
            if(onClickListener!=null){
                mTextView_RightTitle.setOnClickListener(onClickListener);
            }
        }
    }
    protected void setBackIcon(@DrawableRes int backIcon,boolean isShow){
        if(isHaveTitle()){
            if(isShow){
                mImageView_Back.setVisibility(View.VISIBLE);
            }else {
                mImageView_Back.setVisibility(View.GONE);
            }
            mImageView_Back.setImageResource(backIcon);
        }
    }
    protected void setTitleBg(@ColorRes int titleBg){
        if(isHaveTitle()){
           mRelativeLayout_Container.setBackgroundResource(titleBg);
        }
    }
    protected void setRightIcon(@DrawableRes int rightIcon, @Nullable View.OnClickListener onClickListener){
        if(isHaveTitle()){
            mImageView_RightIcon.setVisibility(View.VISIBLE);
            mTextView_RightTitle.setVisibility(View.GONE);
            mImageView_RightIcon.setImageResource(rightIcon);
            if(onClickListener!=null){
                mImageView_RightIcon.setOnClickListener(onClickListener);
            }
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
        return new Intent(this,clazz);
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
        startActivity(new Intent(this,clazz));
    }
}
