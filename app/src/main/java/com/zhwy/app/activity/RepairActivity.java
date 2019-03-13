package com.zhwy.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhwy.app.R;
import com.zhwy.app.activity.base.BaseActivity;
import com.zhwy.app.utils.ValuesUtils;
import com.zhwy.app.widget.MultipleStatusView;

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

public class RepairActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.activity_repair_rv)
    RecyclerView activityNoticeRv;
    @BindView(R.id.content_view)
    SwipeRefreshLayout contentView;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    private BaseQuickAdapter<AVObject, BaseViewHolder> baseQuickAdapter;
    @Override
    protected int LayoutRes() {
        return R.layout.activity_repair;
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
        setTitle("报修/投诉", R.color.colorWhite);
        activityNoticeRv.setLayoutManager(new LinearLayoutManager(this));
        multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleStatusView.showLoading();
                initNotice();
            }
        });
        multipleStatusView.showLoading();
        contentView.setOnRefreshListener(this);//添加刷新事件
    }

    @Override
    protected void onResume() {
        super.onResume();
        initNotice();
    }

    private void initNotice() {
        String userId = getIntent().getStringExtra("userId");//如果是用户的话根据userID查询
        AVQuery<AVObject> query = new AVQuery<>("Repair");
        query.orderByDescending("createdAt");//根据时间查询
        if(!TextUtils.isEmpty(userId)){
            setTitle("我的报修/投诉", R.color.colorWhite);
            query.whereEqualTo("userId",userId);//根据用户ID查询
        }
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(contentView.isRefreshing()){
                    //停止刷新
                    contentView.setRefreshing(false);
                }
                if (e == null) {
                    if(list!=null&&list.size()>0){
                        multipleStatusView.showContent();
                        initAdapter(list);
                    }else {
                        multipleStatusView.showEmpty();
                    }
                } else {
                    multipleStatusView.showError();
                    ToastUtils.showShort(e.getMessage());
                }
            }
        });
    }

    private void initAdapter(List<AVObject> list) {
        if (baseQuickAdapter == null) {
            baseQuickAdapter = new BaseQuickAdapter<AVObject, BaseViewHolder>(R.layout.item_notice, list) {
                @Override
                protected void convert(BaseViewHolder helper, AVObject item) {
                    helper.setText(R.id.item_notice_tv_title, item.getString("title"));
                    helper.setText(R.id.item_notice_tv_time, TimeUtils.date2String(item.getDate("createdAt")));
                }
            };
            baseQuickAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            baseQuickAdapter.isFirstOnly(false);
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    AVObject avObject = baseQuickAdapter.getData().get(position);
                    Intent startActivityIntent = getStartActivityIntent(RepairDetailsActivity.class);
                    startActivityIntent.putExtra("repaire",avObject);
                    startActivity(startActivityIntent);
                }
            });
            activityNoticeRv.setAdapter(baseQuickAdapter);
        } else {
            baseQuickAdapter.setNewData(list);
            baseQuickAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        initNotice();
    }
}
