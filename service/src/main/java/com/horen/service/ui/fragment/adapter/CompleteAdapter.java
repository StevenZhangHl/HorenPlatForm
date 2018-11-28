package com.horen.service.ui.fragment.adapter;

import android.support.v4.content.ContextCompat;

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
public class CompleteAdapter extends BaseQuickAdapter<OrderTransList.PageInfoBean.ListBean, BaseViewHolder> {
    public CompleteAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderTransList.PageInfoBean.ListBean item) {
        // 订单号
        helper.setText(R.id.tv_order, String.format(mContext.getString(R.string.service_distribution_order), item.getOrderallot_id()));
        // 订单类型
//        helper.setText(R.id.tv_status, OrderType.fromTabPosition(item.getOrder_type()));
        // 下单时间
        helper.setText(R.id.tv_order_time, item.getAudit_time());
        helper.setGone(R.id.ll_want_goods_time, false);
        // 距离要货剩余时间 ， 只有出库单有
        if (item.getOrder_type().equals(OrderType.OUTBOUND.getStatus())) {
            // 出库
            helper.setImageResource(R.id.iv_order_status,R.drawable.service_ic_out_order);
            helper.setTextColor(R.id.tv_status, ContextCompat.getColor(mContext, R.color.service_color_6FB));
        } else if (item.getOrder_type().equals(OrderType.STORAGE.getStatus())) {
            // 入库
            helper.setImageResource(R.id.iv_order_status,R.drawable.service_ic_out_storage);
            helper.setTextColor(R.id.tv_status, ContextCompat.getColor(mContext, R.color.service_color_39C));
        } else if (item.getOrder_type().equals(OrderType.SCHEDULING.getStatus())) {
            // 调度
            helper.setImageResource(R.id.iv_order_status,R.drawable.service_ic_out_supplies);
            helper.setTextColor(R.id.tv_status, ContextCompat.getColor(mContext, R.color.service_color_EF8));
        }
    }
}
