package com.zhwy.app.fragment.owner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhwy.app.R;
import com.zhwy.app.beans.BannerBean;
import com.zhwy.app.fragment.base.BaseFragment;

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
