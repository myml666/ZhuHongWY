package com.zhwy.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhwy.app.R;
import com.zhwy.app.activity.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.activity
 * @ClassName: NoticeActivity
 * @Description: java类作用描述 ：通知
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class NoticeActivity extends BaseActivity {
    @BindView(R.id.activity_notice_rv)
    RecyclerView activityNoticeRv;
    private BaseQuickAdapter<AVObject,BaseViewHolder> baseQuickAdapter;
    @Override
    protected int LayoutRes() {
        return R.layout.activity_notice;
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
       setTitle("公告",R.color.colorWhite);
       activityNoticeRv.setLayoutManager(new LinearLayoutManager(this));
       initNotice();
    }

    private void initNotice() {
        AVQuery<AVObject> query = new AVQuery<>("Notice");
//        query.whereContains("title","");
        // 如果这样写，第二个条件将覆盖第一个条件，查询只会返回 priority = 1 的结果
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e==null){
                    initAdapter(list);
                }else {
                    ToastUtils.showShort(e.getMessage());
                }
            }
        });
    }

    private void initAdapter(List<AVObject> list) {
        if(baseQuickAdapter == null){
            baseQuickAdapter = new BaseQuickAdapter<AVObject, BaseViewHolder>(R.layout.item_notice,list) {
                @Override
                protected void convert(BaseViewHolder helper, AVObject item) {
                    helper.setText(R.id.item_notice_tv_title,item.getString("title"));
                    helper.setText(R.id.item_notice_tv_time,item.getString("createdAt"));
                }
            };
            baseQuickAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            baseQuickAdapter.isFirstOnly(false);
            activityNoticeRv.setAdapter(baseQuickAdapter);
        }else {
            baseQuickAdapter.addData(list);
            baseQuickAdapter.notifyDataSetChanged();
        }
    }
}
