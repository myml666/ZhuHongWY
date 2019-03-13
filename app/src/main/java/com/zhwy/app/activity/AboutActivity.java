package com.zhwy.app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.avos.avoscloud.AVUser;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zhwy.app.R;
import com.zhwy.app.activity.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.activity
 * @ClassName: LoginActivity
 * @Description: java类作用描述 ：关于
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class AboutActivity extends BaseActivity {

    @Override
    protected int LayoutRes() {
        return R.layout.activity_about;
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
        setBackIcon(R.drawable.ic_back_white, false);
        setTitle("关于我们", R.color.colorWhite);
    }

    @OnClick({R.id.activity_about_layout_author, R.id.activity_about_layout_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_about_layout_author:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("关于作者");
                builder.setMessage("软件作者：朱红");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                break;
            case R.id.activity_about_layout_update:
                ToastUtils.showShort("已是最新版本");
                break;
        }
    }
}
