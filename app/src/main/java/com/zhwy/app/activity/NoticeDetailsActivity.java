package com.zhwy.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.zhwy.app.R;
import com.zhwy.app.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.activity
 * @ClassName: NoticeDetailsActivity
 * @Description: java类作用描述 ：通知详情
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class NoticeDetailsActivity extends BaseActivity {
    @BindView(R.id.activity_noticedetails_tv_title)
    TextView activityNoticedetailsTvTitle;
    @BindView(R.id.activity_noticedetails_tv_content)
    TextView activityNoticedetailsTvContent;

    @Override
    protected int LayoutRes() {
        return R.layout.activity_noticedetails;
    }

    @Override
    protected boolean isHaveTitle() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initDatas();
    }

    private void initDatas() {
        setTitle("通知详情",R.color.colorWhite);
        initDetails();
    }

    /**
     * 填充详情数据
     */
    private void initDetails() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        activityNoticedetailsTvContent.setText(content);
        activityNoticedetailsTvTitle.setText(title);
    }
}
