package com.horen.service.ui.activity.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.ui.BigImagePagerActivity;
import com.horen.base.util.DisplayUtil;
import com.horen.base.util.ImageLoader;
import com.horen.service.R;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/10/14:00
 * @description : 图片预览Adapter
 * @github :https://github.com/chenyy0708
 */
public class PhotoPreviewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    /**
     * 业务中心，图片需要设置margin
     */
    public static final int BUSINESS = 1001;
    private int type = -1;

    public PhotoPreviewAdapter(int layoutResId, @Nullable List<String> data, int type) {
        super(layoutResId, data);
        this.type = type;
    }

    public PhotoPreviewAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    public PhotoPreviewAdapter(int layoutResId, int type) {
        super(layoutResId);
        this.type = type;
    }

    @Override
    protected void convert(final BaseViewHolder helper, String item) {
        // 动态计算每一张图片的宽度
        ImageView ivPhoto = helper.getView(R.id.iv_photo);
        // 屏幕宽减去左右15dp边距，减去3个分割5dp线宽度 / 4 = 单个图片宽高
        ViewGroup.LayoutParams layoutParams = ivPhoto.getLayoutParams();
        int width = (DisplayUtil.getScreenWidth(mContext) - (DisplayUtil.dip2px(15) * 2) - (DisplayUtil.dip2px(12) * 3)) / 4;
        layoutParams.width = width;
        layoutParams.height = width;
        ivPhoto.setLayoutParams(layoutParams);
        // 业务中心需要margin
        if (type == BUSINESS) {
            ViewGroup.MarginLayoutParams vmp = (ViewGroup.MarginLayoutParams) ivPhoto.getLayoutParams();
            vmp.setMargins(DisplayUtil.dip2px(10), DisplayUtil.dip2px(10), 0, DisplayUtil.dip2px(10));
        }
        ImageLoader.loadCenter(mContext, item, (ImageView) helper.getView(R.id.iv_photo));
        helper.setOnClickListener(R.id.iv_photo, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BigImagePagerActivity.startImagePagerActivity((Activity) mContext, mData, helper.getLayoutPosition());
            }
        });
    }
}
