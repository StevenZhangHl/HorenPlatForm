package com.horen.partner.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.ui.BigImagePagerActivity;
import com.horen.base.util.DisplayUtil;
import com.horen.base.util.ImageLoader;
import com.horen.partner.R;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/14 15:13
 * Description:This isVisiteNotePhotoAdapter
 */
public class VisiteNotePhotoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public VisiteNotePhotoAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, String item) {
        ImageView imageView = helper.getView(R.id.iv_image);
        int width = (DisplayUtil.getScreenWidth(mContext) - (DisplayUtil.dip2px(60))) / 4;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = width;
        imageView.setLayoutParams(layoutParams);
        ImageLoader.load(mContext, item, imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BigImagePagerActivity.startImagePagerActivity((Activity) mContext, mData, helper.getLayoutPosition());
            }
        });
    }
}
