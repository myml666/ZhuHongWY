package com.zhwy.app.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
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
 * @Description: java类作用描述 ：安保
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class SecurityActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.activity_security_rv)
    RecyclerView activitySecurityRv;
    @BindView(R.id.content_view)
    SwipeRefreshLayout contentView;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    private BaseQuickAdapter<AVObject, BaseViewHolder> baseQuickAdapter;
    @Override
    protected int LayoutRes() {
        return R.layout.activity_security;
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
        setTitle("安保", R.color.colorWhite);
        String identity = ValuesUtils.getIdentity();//获取用户身份
        if(identity.equals(ValuesUtils.IDENTITY_WY)){
            //物业身份才能发布通知
            setRightTitle("添加", R.color.colorWhite, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoActivity(SecurityAddActivity.class);
                }
            });
        }
        activitySecurityRv.setLayoutManager(new LinearLayoutManager(this));
        multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleStatusView.showLoading();
                initSecurity();
            }
        });
        multipleStatusView.showLoading();
        contentView.setOnRefreshListener(this);//添加刷新事件
    }

    @Override
    protected void onResume() {
        super.onResume();
        initSecurity();
    }

    /**
     * 加载安保人员数据
     */
    private void initSecurity() {
        AVQuery<AVObject> query = new AVQuery<>("Security");
        query.orderByDescending("createdAt");//根据时间查询
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
            baseQuickAdapter = new BaseQuickAdapter<AVObject, BaseViewHolder>(R.layout.item_security, list) {
                @Override
                protected void convert(BaseViewHolder helper, AVObject item) {
                    ImageView itemSecurityImgPhoto = helper.getView(R.id.item_security_img_photo);
                    TextView textView = helper.getView(R.id.item_security_tv_state);
                    RelativeLayout itemSecurityLayoutChangestate = helper.getView(R.id.item_security_layout_changestate);
                    if(ValuesUtils.getIdentity().equals(ValuesUtils.IDENTITY_WY)){
                        itemSecurityLayoutChangestate.setVisibility(View.VISIBLE);
                        helper.addOnClickListener(R.id.item_security_tv_zb);
                        helper.addOnClickListener(R.id.item_security_tv_xb);
                    }
                    helper.setText(R.id.item_security_tv_name,"姓名："+item.getString("name"));
                    if(item.getBoolean("state")){
                        textView.setTextColor(Color.parseColor("#36F885"));
                        textView.setText("值班");
                    }else {
                        textView.setTextColor(Color.parseColor("#F9321D"));
                        helper.setText(R.id.item_security_tv_state,"休班");
                    }
                    Glide.with(SecurityActivity.this)
                            .load(item.getAVFile("photo").getUrl())
                            .placeholder(R.drawable.ic_defaultusericon)
                            .error(R.drawable.ic_defaultusericon).into(itemSecurityImgPhoto);
                }
            };
            baseQuickAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            baseQuickAdapter.isFirstOnly(false);
            baseQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    AVObject avObject = baseQuickAdapter.getData().get(position);
                    switch (view.getId()){
                        case R.id.item_security_tv_zb:
                            //修改为值班
                            avObject.put("state",true);
                            break;
                        case R.id.item_security_tv_xb:
                            //修改为休班
                            avObject.put("state",false);
                            break;
                    }
                    avObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if(e==null){
                                ToastUtils.showShort("状态修改成功");
                                initSecurity();
                            }else {
                                ToastUtils.showShort("状态修改失败，请重试");
                            }
                        }
                    });
                }
            });
            activitySecurityRv.setAdapter(baseQuickAdapter);
        } else {
            baseQuickAdapter.setNewData(list);
            baseQuickAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        initSecurity();
    }
}
