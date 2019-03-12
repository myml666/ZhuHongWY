package com.zhwy.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
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
 * @Description: java类作用描述 ：发布通知
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class NoticeAddActivity extends BaseActivity {

    @BindView(R.id.activity_addnotice_et_title)
    EditText activityAddnoticeEtTitle;
    @BindView(R.id.activity_addnotice_et_content)
    EditText activityAddnoticeEtContent;

    @Override
    protected int LayoutRes() {
        return R.layout.activity_addnotice;
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
        setTitle("发布通知", R.color.colorWhite);
    }

    @OnClick(R.id.activity_addnotice_tv_submit)
    public void onViewClicked() {
        String title = GetTextUtils.getText(activityAddnoticeEtTitle);
        String content = GetTextUtils.getText(activityAddnoticeEtContent);
        if(TextUtils.isEmpty(title)){
            ToastUtils.showShort("请输入标题");
            return;
        }
        if(TextUtils.isEmpty(content)){
            ToastUtils.showShort("请输入详情");
            return;
        }
        AVObject notice = new AVObject("Notice");// 构建对象
        notice.put("title", title);
        notice.put("content", content);
        notice.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e==null){
                    ToastUtils.showShort("发布成功");
                    finish();
                }else {
                    ToastUtils.showShort("发布失败请重试");
                }
            }
        });
    }
}
