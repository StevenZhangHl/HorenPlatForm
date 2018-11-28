package com.horen.partner.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.util.AppUtil;
import com.horen.base.util.DisplayUtil;
import com.horen.partner.R;
import com.horen.partner.ui.widget.LeanTextView;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/9/5 14:18
 * Description:This isBarChartXAdapter
 */
public class BarChartXAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private int currentPosition = -1;

    public BarChartXAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    public void setCurrentPosition(int position) {
        this.currentPosition = position;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        LeanTextView tv_x = helper.getView(R.id.tv_bar_x);
        tv_x.setmDegrees(DisplayUtil.dip2px(103));
        tv_x.setText(item);
        if ("Xiaomi".equals(AppUtil.getDeviceBrand())) {
            if ("Mi Note 3".equals(AppUtil.getSystemModel())) {
                tv_x.setmDegrees(DisplayUtil.dip2px(113));
            }
        }
        if (currentPosition == helper.getLayoutPosition()) {
            tv_x.setTextColor(mContext.getResources().getColor(R.color.color_333));
        } else {
            tv_x.setTextColor(mContext.getResources().getColor(R.color.color_666));
        }
    }
}
