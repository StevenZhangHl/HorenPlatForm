package com.horen.partner.adapter;

import android.support.annotation.Nullable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.util.DisplayUtil;
import com.horen.partner.R;
import com.horen.partner.bean.BarChartListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Steven
 * Time:2018/9/5 12:14
 * Description:This isBarChartAdapter
 */
public class BarChartAdapter extends BaseQuickAdapter<BarChartListBean, BaseViewHolder> {
    private int height;
    private int currentPosition = -1;
    private int waringPostionMax = 0;

    public BarChartAdapter(int layoutResId, @Nullable List<BarChartListBean> data, int barHeight) {
        super(layoutResId, data);
        this.height = barHeight;
    }

    public void setCurrentPosition(int position) {
        this.currentPosition = position;
        notifyDataSetChanged();
    }

    public void setWaringPostionMax(int postionMax) {
        this.waringPostionMax = postionMax;
    }

    @Override
    protected void convert(BaseViewHolder helper, BarChartListBean item) {
        TextView tv_marker = helper.getView(R.id.tv_y_value);
        TextView tv_bar_translate = helper.getView(R.id.tv_bar_translate);
        TextView tv_bar_white = helper.getView(R.id.tv_bar_white);
        int barHeight = DisplayUtil.dip2px(100);
        int h = item.getyValue() * ((height / 4) * 3) / 120;
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) tv_bar_white.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        linearParams.height = barHeight - h;
        tv_bar_white.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        RelativeLayout.LayoutParams linearParams1 = (RelativeLayout.LayoutParams) tv_bar_translate.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        linearParams1.height = h;
        tv_bar_translate.setLayoutParams(linearParams1); //使设置好的布局参数应用到控件
        if (item.getyValue() <= 15) {
            tv_marker.setTextColor(mContext.getResources().getColor(R.color.bar_color));
            tv_marker.setText(item.getyValue() + "天");
        }
        if (helper.getLayoutPosition() > waringPostionMax) {
            if (currentPosition == helper.getLayoutPosition()) {
                tv_marker.setTextColor(mContext.getResources().getColor(R.color.color_333));
                tv_marker.setText(item.getyValue() + "天");
            } else {
                tv_marker.setText("");
            }
        }
    }
}
