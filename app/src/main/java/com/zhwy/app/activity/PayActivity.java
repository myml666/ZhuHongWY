package com.zhwy.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.ToastUtils;
import com.zhwy.app.R;
import com.zhwy.app.activity.base.BaseActivity;
import com.zhwy.app.utils.GetTextUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.activity
 * @ClassName: PayActivity
 * @Description: java类作用描述 ：缴费
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class PayActivity extends BaseActivity {
    @BindView(R.id.activity_pay_et_phone)
    EditText activityPayEtPhone;
    @BindView(R.id.activity_pay_et_number)
    EditText activityPayEtNumber;
    @BindView(R.id.activity_pay_tv_category)
    TextView activityPayTvCategory;
    @BindView(R.id.activity_pay_et_money)
    EditText activityPayEtMoney;
    private OptionsPickerView pvOptions;
    private ArrayList<String> mPayCategory;//缴费类别
    private int mCategory = 0;
    @Override
    protected int LayoutRes() {
        return R.layout.activity_pay;
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
        setTitle("缴费", R.color.colorWhite);
        if(mPayCategory == null){
            mPayCategory = new ArrayList<>();
        }
        mPayCategory.add("物业费");
        mPayCategory.add("水费");
        mPayCategory.add("电费");
        if(pvOptions == null){
            pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    mCategory = options1;//设置缴费类别
                    activityPayTvCategory.setText(mPayCategory.get(options1));
                }
            }).build();
            pvOptions.setPicker(mPayCategory);
        }
    }

    @OnClick({R.id.activity_pay_tv_category, R.id.activity_pay_tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_pay_tv_category:
                //选择缴费类别
                if(pvOptions!=null){
                    pvOptions.show();
                }
                break;
            case R.id.activity_pay_tv_submit:
                //缴费
                pay();
                break;
        }
    }

    /**
     * 缴费
     */
    private void pay() {
        String money = GetTextUtils.getText(activityPayEtMoney);
        String number = GetTextUtils.getText(activityPayEtNumber);
        String phone = GetTextUtils.getText(activityPayEtPhone);
        String category = GetTextUtils.getText(activityPayTvCategory);
        if(TextUtils.isEmpty(phone)){
            ToastUtils.showShort("请输入联系手机号");
            return;
        }
        if(TextUtils.isEmpty(number)){
            ToastUtils.showShort("请输入住户楼号");
            return;
        }
        if(category.length()>4){
            ToastUtils.showShort("请选择缴费类别");
            return;
        }
        if(TextUtils.isEmpty(money)){
            ToastUtils.showShort("请输入缴费金额");
            return;
        }
        String objectId = AVUser.getCurrentUser().getObjectId();
        AVObject pay = new AVObject("Pay");// 构建对象
        pay.put("category", mCategory);
        pay.put("number", number);
        pay.put("phone", phone);
        pay.put("money", money);
        pay.put("userId", objectId);
        pay.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e==null){
                    ToastUtils.showShort("缴费成功");
                    finish();
                }else {
                    ToastUtils.showShort("缴费失败请重试");
                }
            }
        });
    }
}
