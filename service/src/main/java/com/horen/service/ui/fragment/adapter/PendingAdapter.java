package com.horen.service.ui.fragment.adapter;

import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.service.R;
import com.horen.service.bean.OrderTransList;
import com.horen.service.enumeration.business.OrderType;

/**
 * @author :ChenYangYi
 * @date :2018/08/13/08:53
 * @description :待处理Adapter
 * @github :https://github.com/chenyy0708
 */
public class PendingAdapter extends BaseQuickAdapter<OrderTransList.PageInfoBean.ListBean, BaseViewHolder> {

    public PendingAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderTransList.PageInfoBean.ListBean item) {
        TextView tvTime = helper.getView(R.id.tv_want_goods_time);
        // 订单号
        helper.setText(R.id.tv_order, String.format(mContext.getString(R.string.service_distribution_order), item.getOrderallot_id()));
//        helper.setText(R.id.tv_status, OrderType.fromTabPosition(item.getOrder_type()));
        // 下单时间
        helper.setText(R.id.tv_order_time, item.getAudit_time());
        // 距离要货剩余时间 ， 只有出库单有
        if (item.getOrder_type().equals(OrderType.OUTBOUND.getStatus())) {
            helper.setImageResource(R.id.iv_order_status, R.drawable.service_ic_out_order);
            if (Integer.valueOf(item.getTime_difference()) >= 0) {
                helper.setTextColor(R.id.tv_want_goods_time, ContextCompat.getColor(mContext, R.color.color_333));
                tvTime.setBackgroundResource(R.drawable.service_shape_333);
                helper.setText(R.id.tv_want_goods_time, item.getTime_difference() + mContext.getString(R.string.service_day));
            } else { //  剩余时间小于0，显示超期时间，标红
                helper.setTextColor(R.id.tv_want_goods_time, ContextCompat.getColor(mContext, R.color.service_color_F15));
                tvTime.setBackgroundResource(R.drawable.service_shape_f15);
                helper.setText(R.id.tv_want_goods_time, "超期" + Math.abs(Integer.valueOf(item.getTime_difference())) + mContext.getString(R.string.service_day));
            }
            helper.setVisible(R.id.ll_want_goods_time, true);
        } else if (item.getOrder_type().equals(OrderType.STORAGE.getStatus())) {
            // 入库
            helper.setImageResource(R.id.iv_order_status, R.drawable.service_ic_out_storage);
            helper.setGone(R.id.ll_want_goods_time, false);
        } else if (item.getOrder_type().equals(OrderType.SCHEDULING.getStatus())) {
            // 调度
            helper.setImageResource(R.id.iv_order_status, R.drawable.service_ic_out_supplies);
            helper.setGone(R.id.ll_want_goods_time, false);
        }
    }
}
