package com.zhwy.app.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhwy.app.R;
import com.zhwy.app.fragment.base.BaseFragment;
import com.zhwy.app.widget.MultipleStatusView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.fragment.property
 * @ClassName: PropertyMainHomeFragment
 * @Description: java类作用描述 ：圈子
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class PYQFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.fragment_pyq_rv)
    RecyclerView fragmentPyqRv;
    @BindView(R.id.content_view)
    SwipeRefreshLayout contentView;
    @BindView(R.id.multipleStatusView)
    MultipleStatusView multipleStatusView;
    private BaseQuickAdapter<AVObject,BaseViewHolder> baseQuickAdapter;
    @Override
    protected int LayoutRes() {
        return R.layout.fragment_pyq;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initDatas();
    }
    private void initDatas() {
        multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleStatusView.showLoading();
                initPyq();
            }
        });
        multipleStatusView.showLoading();
        contentView.setOnRefreshListener(this);
        fragmentPyqRv.setLayoutManager(new LinearLayoutManager(getContext()));
        initPyq();
    }

    /**
     * 加载朋友圈数据
     */
    private void initPyq() {
        AVQuery<AVObject> query = new AVQuery<>("Pyq");
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
        if(baseQuickAdapter==null){
            baseQuickAdapter = new BaseQuickAdapter<AVObject, BaseViewHolder>(R.layout.item_pyq,list) {
                @Override
                protected void convert(BaseViewHolder helper, AVObject item) {
                    CircleImageView itemPyqImgUsericon = helper.getView(R.id.item_pyq_img_usericon);
                    ImageView itemPyqImgContent =  helper.getView(R.id.item_pyq_img_content);
                    helper.setText(R.id.item_pyq_tv_username,item.getString("userName"));//用户名
                    helper.setText(R.id.item_pyq_tv_content,item.getString("content"));//内容
//                    helper.setText(R.id.item_pyq_tv_time, TimeUtils.date2String(item.getDate("createdAt")));
                    Glide.with(getContext()).load(item.getString("userIcon")).into(itemPyqImgUsericon);
                    AVFile img = item.getAVFile("img");
                    if(img!=null){
                        LogUtils.eTag("测试",img.getUrl());
                        Glide.with(getContext()).load(img.getUrl()).into(itemPyqImgContent);
                    }
                }
            };
            fragmentPyqRv.setAdapter(baseQuickAdapter);
        }else {
            baseQuickAdapter.setNewData(list);
            baseQuickAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onRefresh() {
        initPyq();
    }
}
