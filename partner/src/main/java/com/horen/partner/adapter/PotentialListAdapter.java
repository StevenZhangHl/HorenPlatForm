package com.horen.partner.adapter;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daimajia.androidanimations.library.YoYo;
import com.horen.partner.R;
import com.horen.partner.bean.PotentialBean;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/8 13:25
 * Description:This isPotentialListAdapter
 */
public class PotentialListAdapter extends BaseQuickAdapter<PotentialBean.ListBean, BaseViewHolder> {
    private int selectPostion = -1;
    private Handler handler;
    private boolean isRemoveHandler = false;
    private boolean isStart = false;

    public PotentialListAdapter(int layoutResId, @Nullable List<PotentialBean.ListBean> data) {
        super(layoutResId, data);
        handler = new Handler();
    }

    public void removeHandler(boolean isRemoved) {
        this.isRemoveHandler = isRemoved;
        notifyDataSetChanged();
    }

    public void setSelectPostion(int selectPostion) {
        this.selectPostion = selectPostion;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(final BaseViewHolder helper, PotentialBean.ListBean item) {
        helper.setText(R.id.tv_customer_name, item.getCustomer_name());
        helper.setText(R.id.tv_balance_time, item.getDays() + "天");
        TextView tv_time = helper.getView(R.id.tv_balance_time);
        helper.addOnClickListener(R.id.iv_detail);
        if (item.getDays() <= 15) {
            tv_time.setTextColor(mContext.getResources().getColor(R.color.bar_warning_color));
        } else {
            tv_time.setTextColor(mContext.getResources().getColor(R.color.color_333));
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                helper.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
        };
        if (isRemoveHandler) {
            if (handler != null) {
                handler.removeCallbacks(runnable);
            }
        }
        if (helper.getLayoutPosition() == selectPostion) {
            helper.itemView.setBackgroundResource(R.drawable.item_bg_rectangle);
            handler.postDelayed(runnable, 1000);
        } else {
            helper.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

    }
}
