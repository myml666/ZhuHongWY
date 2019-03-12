package com.zhwy.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.blankj.utilcode.util.ToastUtils;
import com.zhwy.app.R;
import com.zhwy.app.activity.base.BaseActivity;
import com.zhwy.app.utils.GetTextUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.activity
 * @ClassName: NoticeDetailsActivity
 * @Description: java类作用描述 ：添加报修/投诉
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class RepaireAddActivity extends BaseActivity {

    @BindView(R.id.activity_repairdetails_et_title)
    EditText activityRepairdetailsEtTitle;
    @BindView(R.id.activity_repairdetails_et_number)
    EditText activityRepairdetailsEtNumber;
    @BindView(R.id.activity_repairdetails_et_name)
    EditText activityRepairdetailsEtName;
    @BindView(R.id.activity_repairdetails_et_phone)
    EditText activityRepairdetailsEtPhone;
    @BindView(R.id.activity_repairdetails_et_content)
    EditText activityRepairdetailsEtContent;

    @Override
    protected int LayoutRes() {
        return R.layout.activity_addrepair;
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
        setTitle("提交报修/投诉", R.color.colorWhite);
    }

    @OnClick(R.id.activity_addnotice_tv_submit)
    public void onViewClicked() {
        String title = GetTextUtils.getText(activityRepairdetailsEtTitle);
        String content = GetTextUtils.getText(activityRepairdetailsEtContent);
        String name = GetTextUtils.getText(activityRepairdetailsEtName);
        String number = GetTextUtils.getText(activityRepairdetailsEtNumber);
        String phone = GetTextUtils.getText(activityRepairdetailsEtPhone);
        if (TextUtils.isEmpty(title)) {
            ToastUtils.showShort("请输入标题");
            return;
        }
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showShort("请输入详情");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort("请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(number)) {
            ToastUtils.showShort("请输入户号");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort("请输入手机号");
            return;
        }
        String objectId = AVUser.getCurrentUser().getObjectId();
        AVObject repair = new AVObject("Repair");// 构建对象
        repair.put("title", title);
        repair.put("content", content);
        repair.put("number", number);
        repair.put("name", name);
        repair.put("phone", phone);
        repair.put("userId", objectId);
        repair.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    ToastUtils.showShort("发布成功");
                    finish();
                } else {
                    ToastUtils.showShort("发布失败请重试");
                }
            }
        });
    }
}
