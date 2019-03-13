package com.zhwy.app.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.bumptech.glide.Glide;
import com.zhwy.app.R;
import com.zhwy.app.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.activity
 * @Description: java类作用描述 ：缴费详情
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class PayDetailsActivity extends BaseActivity {

    @BindView(R.id.activity_paydetails_img_category)
    ImageView activityPaydetailsImgCategory;
    @BindView(R.id.activity_pay_et_phone)
    TextView activityPayEtPhone;
    @BindView(R.id.activity_pay_tv_number)
    TextView activityPayTvNumber;
    @BindView(R.id.activity_pay_tv_category)
    TextView activityPayTvCategory;
    @BindView(R.id.activity_pay_tv_money)
    TextView activityPayTvMoney;

    @Override
    protected int LayoutRes() {
        return R.layout.activity_paydetails;
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
        setTitle("缴费详情", R.color.colorWhite);
        AVObject pay = getIntent().getParcelableExtra("pay");
        if(pay!=null){
            activityPayEtPhone.setText(pay.getString("phone"));
            switch (pay.getInt("category")){
                case 0:
                    //物业费
                    Glide.with(this).load(R.drawable.ic_paywy).into(activityPaydetailsImgCategory);
                    activityPayTvCategory.setText("物业费");
                    break;
                case 1:
                    //水费
                    Glide.with(this).load(R.drawable.ic_paywater).into(activityPaydetailsImgCategory);
                    activityPayTvCategory.setText("水费");
                    break;
                case 2:
                    //电费
                    Glide.with(this).load(R.drawable.ic_paydian).into(activityPaydetailsImgCategory);
                    activityPayTvCategory.setText("电费");
                    break;
            }
            activityPayTvNumber.setText(pay.getString("number"));
            activityPayTvMoney.setText(pay.getString("money")+"元");
        }
    }

}
