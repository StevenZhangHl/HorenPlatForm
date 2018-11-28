package com.horen.partner.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.horen.base.util.DisplayUtil;
import com.horen.base.util.ImageLoader;
import com.horen.partner.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/7 12:50
 * Description:This isShowPhotoAdapter
 */
public class ShowPhotoAdapter extends BaseAdapter {
    private Context context;
    private List<String> beanList = new ArrayList<>();

    public ShowPhotoAdapter(Context context, @Nullable List<String> data) {
        this.beanList = data;
        this.context = context;
    }

    public void addAllData(List<String> data) {
        beanList.addAll(data);
        notifyDataSetChanged();
    }

    public void setNewData(List<String> newData) {
        beanList = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        int count = beanList == null ? 1 : beanList.size() + 1;
        if (count > 6) {
            return beanList.size();
        } else {
            return count;
        }
    }

    public List<String> getBeanList() {
        return beanList;
    }

    @Override
    public Object getItem(int i) {
        return beanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.partner_item_show_photo, null);
        ImageView imageView = view.findViewById(R.id.iv_image);
        ImageView iv_delate = view.findViewById(R.id.iv_delete);
        int width =(DisplayUtil.getScreenWidth(context) - (DisplayUtil.dip2px(60))) / 4;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = width ;
        imageView.setLayoutParams(layoutParams);
        if (i == beanList.size()) {
            iv_delate.setVisibility(View.GONE);
            imageView.setImageResource(R.mipmap.icon_add_photo);
        } else {
            iv_delate.setVisibility(View.VISIBLE);
            ImageLoader.load(context, beanList.get(i), imageView);
        }
        iv_delate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beanList.remove(i);
                notifyDataSetChanged();
            }
        });
        return view;
    }
}
