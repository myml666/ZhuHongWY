package com.zhwy.app.fragment.owner;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhwy.app.R;
import com.zhwy.app.activity.AboutActivity;
import com.zhwy.app.activity.MainActivity;
import com.zhwy.app.activity.PayListActivity;
import com.zhwy.app.activity.RepairActivity;
import com.zhwy.app.activity.RepaireAddActivity;
import com.zhwy.app.activity.SecurityAddActivity;
import com.zhwy.app.adapter.MainHomeMenuAdapter;
import com.zhwy.app.beans.MainHomeItemBean;
import com.zhwy.app.fragment.base.BaseFragment;
import com.zhwy.app.fragment.callback.ChoicePhotoCallback;
import com.zhwy.app.utils.ValuesUtils;
import com.zhwy.app.widget.MyDividerGridView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.fragment
 * @ClassName: MainMineFragment
 * @Description: java类作用描述 ：主页我的页面（业主）
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class OwnerMainMineFragment extends BaseFragment {
    @BindView(R.id.fragment_ownermainmine_icon)
    CircleImageView fragmentOwnermainmineIcon;
    @BindView(R.id.fragment_ownermainmine_tv_username)
    TextView fragmentOwnermainmineTvUsername;
    @BindView(R.id.fragment_ownermainmine_gv)
    MyDividerGridView fragmentOwnermainmineGv;
    private ArrayList<MainHomeItemBean> mHomeItemBeans;
    private String[] mItemTitles = {"我的缴费", "我的维修/投诉", "关于我们","退出登录"};
    private int[] mItemIcons = {R.drawable.ic_jf, R.drawable.ic_wx, R.drawable.ic_about,R.drawable.ic_logout};
    private MainHomeMenuAdapter mMainHomeMenuAdapter;
    private RxPermissions rxPermissions;

    @Override
    protected int LayoutRes() {
        return R.layout.fragment_mainmine;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initDatas();
    }

    private void initDatas() {
        FragmentActivity activity = getActivity();
        if(activity instanceof MainActivity){
            MainActivity mainActivity = (MainActivity) activity;
            mainActivity.setChoicePhotoCallback(new ChoicePhotoCallback() {
                @Override
                public void onPhotoChoice(final File file) {
                    try {
                        AVFile f = AVFile.withAbsoluteLocalPath("temp.png", file.getAbsolutePath());
                        AVUser currentUser = AVUser.getCurrentUser();
                        currentUser.put("photo",f);
                        currentUser.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if(e==null){
                                    ToastUtils.showShort("头像修改成功");
                                    Glide.with(getContext()).load(file).into(fragmentOwnermainmineIcon);
                                }else {
                                    ToastUtils.showShort("头像修改失败，请重试");
                                }
                            }
                        });
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(this);
        }
        initUserInfo();
        initMenu();
    }

    /**
     * 加载菜单
     */
    private void initMenu() {
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
                    Intent startActivityIntent;
                    switch (position) {
                        case 0:
                            //我的缴费
                            startActivityIntent = getStartActivityIntent(PayListActivity.class);
                            startActivityIntent.putExtra("userId", AVUser.getCurrentUser().getObjectId());
                            startActivity(startActivityIntent);
                            break;
                        case 1:
                            //我的维修/投诉
                            startActivityIntent = getStartActivityIntent(RepairActivity.class);
                            startActivityIntent.putExtra("userId", AVUser.getCurrentUser().getObjectId());
                            startActivity(startActivityIntent);
                            break;
                        case 2:
                            //关于我们
                            gotoActivity(AboutActivity.class);
                            break;
                        case 3:
                            //退出登录
                            logout();
                            break;
                    }
                }
            });
            fragmentOwnermainmineGv.setAdapter(mMainHomeMenuAdapter);
        } else {
            mMainHomeMenuAdapter.setmDatas(mHomeItemBeans);
            mMainHomeMenuAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 退出登录
     */
    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("提示");
        builder.setMessage("是否确定退出登录？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AVUser.logOut();
                dialog.dismiss();
                AppUtils.exitApp();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 获取用户信息
     */
    private void initUserInfo() {
        AVUser currentUser = AVUser.getCurrentUser();
        AVFile photo = currentUser.getAVFile("photo");
        if (photo != null) {
            Glide.with(this).load(photo.getUrl()).into(fragmentOwnermainmineIcon);
        }
        String username = currentUser.getUsername();
        fragmentOwnermainmineTvUsername.setText(username.substring(2,username.length()));
    }

    @OnClick(R.id.fragment_ownermainmine_icon)
    public void onViewClicked() {
        //选择头像
        choicePhoto();
    }
    /**
     * 选择头像
     */
    private void choicePhoto() {
        rxPermissions.requestEachCombined(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            Matisse.from(getActivity())
                                    .choose(MimeType.ofImage())
                                    .countable(true)
                                    .captureStrategy(new CaptureStrategy(true, "com.zhwy.app.fileprovider")) //是否拍照功能，并设置拍照后图片的保存路径
                                    .maxSelectable(1)
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                    .thumbnailScale(0.8f)
                                    .forResult(ValuesUtils.REQUEST_CODE_CHOOSE);
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            ToastUtils.showShort("您已拒绝权限申请");
                        } else {
                            ToastUtils.showShort("您已拒绝权限申请，请前往设置>应用管理>权限管理打开权限");
                        }
                    }
                });
    }
}
