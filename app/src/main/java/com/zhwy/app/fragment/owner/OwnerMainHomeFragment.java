package com.zhwy.app.fragment.owner;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhwy.app.R;
import com.zhwy.app.activity.NoticeActivity;
import com.zhwy.app.activity.PayActivity;
import com.zhwy.app.activity.RepaireAddActivity;
import com.zhwy.app.activity.SecurityActivity;
import com.zhwy.app.adapter.MainHomeMenuAdapter;
import com.zhwy.app.beans.BannerBean;
import com.zhwy.app.beans.MainHomeItemBean;
import com.zhwy.app.fragment.base.BaseFragment;
import com.zhwy.app.fragment.property.PropertyMainHomeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;

import static android.widget.ImageView.ScaleType.FIT_XY;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.fragment.property
 * @ClassName: PropertyMainHomeFragment
 * @Description: java类作用描述 ：物业主界面HomeFragment
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class OwnerMainHomeFragment extends BaseFragment implements BGABanner.Adapter<ImageView, String> {
    @BindView(R.id.fragment_propertymainhome_banner)
    BGABanner fragmentPropertymainhomeBanner;
    @BindView(R.id.fragment_propertymainhome_gv)
    GridView fragmentPropertymainhomeGv;
    private ArrayList<MainHomeItemBean> mHomeItemBeans;
    private String[] mItemTitles = {"通知公告", "报修/投诉","安保","在线客服","缴费"};
    private int[] mItemIcons = {R.drawable.ic_tz, R.drawable.ic_wx,R.drawable.ic_ab,R.drawable.ic_qq,R.drawable.ic_jf};
    private MainHomeMenuAdapter mMainHomeMenuAdapter;

    @Override
    protected int LayoutRes() {
        return R.layout.fragment_propertymainhome;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initDatas();
    }


    private void initDatas() {
        initBanner();
        initItems();
    }

    /**
     * 增加菜单Item
     */
    private void initItems() {
        if (mHomeItemBeans == null) {
            mHomeItemBeans = new ArrayList<>();
        }
        mHomeItemBeans.clear();
        for (int x = 0; x < mItemTitles.length; x++) {
            mHomeItemBeans.add(new MainHomeItemBean(mItemTitles[x], mItemIcons[x]));
        }
        if (mMainHomeMenuAdapter == null) {
            mMainHomeMenuAdapter = new MainHomeMenuAdapter(mHomeItemBeans, getContext());
            mMainHomeMenuAdapter.setOnItemClickListener(new MainHomeMenuAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    switch (position){
                        case 0:
                            //通知公告
                            gotoActivity(NoticeActivity.class);
                            break;
                        case 1:
                            //报修维修
                            gotoActivity(RepaireAddActivity.class);
                            break;
                        case 2:
                            //安保
                            gotoActivity(SecurityActivity.class);
                            break;
                        case 3:
                            //在线客服（业主才有）
                            if (isQQInstall(getContext())) {
                                final String qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=1955891339";
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
                            } else {
                                ToastUtils.showShort("请安装QQ客户端");
                            }
                            break;
                        case 4:
                            //缴费
                            gotoActivity(PayActivity.class);
                            break;
                    }
                }
            });
            fragmentPropertymainhomeGv.setAdapter(mMainHomeMenuAdapter);
        } else {
            mMainHomeMenuAdapter.setmDatas(mHomeItemBeans);
            mMainHomeMenuAdapter.notifyDataSetChanged();
        }

    }

    /**
     * 检测是否安装QQ
     * @param context
     * @return
     */
    public static boolean isQQInstall(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                //通过遍历应用所有包名进行判断
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 增加顶部lunbot
     */
    private void initBanner() {
        AVQuery<AVObject> avQuery = new AVQuery<>("Banner");
        avQuery.getInBackground("5c85f99aac502e0066273803", new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null) {
                    String images = avObject.getString("images");
                    BannerBean bannerBean = new Gson().fromJson(images, BannerBean.class);
                    if (bannerBean != null) {
                        fragmentPropertymainhomeBanner.setData(bannerBean.getImages(), null);
                        fragmentPropertymainhomeBanner.setAdapter(OwnerMainHomeFragment.this);
                    }
                }
            }
        });
    }

    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
        itemView.setScaleType(FIT_XY);
        Glide.with(itemView.getContext())
                .load(model)
//                .placeholder(R.drawable.ic_bannerdefault)
//                .error(R.drawable.ic_bannerdefault)
                .into(itemView);
    }
}
