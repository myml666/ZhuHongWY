package com.zhwy.app.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.zhwy.app.R;
import com.zhwy.app.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.activity
 * @ClassName: NoticeDetailsActivity
 * @Description: java类作用描述 ：报修/投诉详情
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class RepairDetailsActivity extends BaseActivity {


    @BindView(R.id.activity_repairdetails_tv_title)
    TextView activityRepairdetailsTvTitle;
    @BindView(R.id.activity_repairdetails_tv_number)
    TextView activityRepairdetailsTvNumber;
    @BindView(R.id.activity_repairdetails_tv_name)
    TextView activityRepairdetailsTvName;
    @BindView(R.id.activity_repairdetails_tv_phone)
    TextView activityRepairdetailsTvPhone;
    @BindView(R.id.activity_repairdetails_tv_content)
    TextView activityRepairdetailsTvContent;

    @Override
    protected int LayoutRes() {
        return R.layout.activity_repairdetails;
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
        setTitle("报修/投诉详情", R.color.colorWhite);
        initDetails();
    }

    /**
     * 填充详情数据
     */
    private void initDetails() {
        AVObject repaireDetails = getIntent().getParcelableExtra("repaire");
        activityRepairdetailsTvTitle.setText(repaireDetails.getString("title"));
        activityRepairdetailsTvContent.setText(repaireDetails.getString("content"));
        activityRepairdetailsTvName.setText(repaireDetails.getString("name"));
        activityRepairdetailsTvNumber.setText(repaireDetails.getString("number"));
        activityRepairdetailsTvPhone.setText(repaireDetails.getString("phone"));
    }
}
