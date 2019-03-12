package com.zhwy.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhwy.app.R;
import com.zhwy.app.beans.MainHomeItemBean;

import java.util.List;

/**
 * @ProjectName: Hehuidai
 * @Package: com.hehuidai.application.adapter
 * @ClassName: FindFragmentHeaderAdapter
 * @Description: java类作用描述 ：主界面Home菜单适配器
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class MainHomeMenuAdapter extends BaseAdapter {
    private List<MainHomeItemBean> mDatas;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MainHomeMenuAdapter(List<MainHomeItemBean> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    public List<MainHomeItemBean> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<MainHomeItemBean> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext,R.layout.item_mainhome, null);
            holder = new ViewHolder();
            holder.texttitle = (TextView) convertView.findViewById(R.id.item_mainhome_tv_title);
            holder.imageView=convertView.findViewById(R.id.item_mainhome_img_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MainHomeItemBean mainHomeItemBean = mDatas.get(i);
        if (mainHomeItemBean != null) {
            holder.texttitle.setText(mainHomeItemBean.getTitle());
            Glide.with(mContext)
                            .load(mainHomeItemBean.getIcon())
                            .into(holder.imageView);
        }
        if(onItemClickListener!=null){
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(i);
                }
            });
        }
        return convertView;
    }
    static class ViewHolder {
        ImageView imageView;
        TextView texttitle;
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
