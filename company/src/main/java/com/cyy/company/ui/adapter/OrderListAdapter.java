package com.cyy.company.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyy.company.R;
import com.cyy.company.bean.OrderListBean;
import com.cyy.company.enums.OrderStatus;
import com.cyy.company.enums.OrderStatusColor;
import com.cyy.company.enums.OrderType;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.ImageLoader;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/16/13:18
 * @description :订单列表
 * @github :https://github.com/chenyy0708
 */
public class OrderListAdapter extends BaseQuickAdapter<OrderListBean.PdListBean.ListBeanX, BaseViewHolder> {

    /**
     * 订单类型
     */
    private String order_type;

    public OrderListAdapter(@Nullable List<OrderListBean.PdListBean.ListBeanX> data, String order_type) {
        super(R.layout.item_order_list, data);
        this.order_type = order_type;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListBean.PdListBean.ListBeanX item) {
        SuperButton sbtEvaluation = helper.getView(R.id.sbt_evaluation);
        if (!CollectionUtils.isNullOrEmpty(item.getList())) {
            // 产品图片
            ImageLoader.load(mContext, item.getList().get(0).getProduct_photo(), (ImageView) helper.getView(R.id.iv_photo));
        }
        // 租还箱类型
        helper.setImageResource(R.id.iv_order_type, OrderType.ONE.getPosition().equals(order_type) ? R.drawable.icon_rent : R.drawable.icon_return);
        // 订单号
        helper.setText(R.id.tv_order_number, item.getOrder_id());
        // 订单类型
        helper.setText(R.id.tv_order_status, OrderStatus.fromTabPosition(item.getOrder_status()));
        helper.setTextColor(R.id.tv_order_status, OrderStatusColor.fromColor(item.getOrder_status()));
        // 箱子已签收/总箱数
        helper.setText(R.id.tv_all_box, item.getBox_receiveqty() + "/" + item.getBox_orderqty());
        // 耗材已签收/总耗材数
        helper.setText(R.id.tv_all_supplies, item.getMaterial_receiveqty() + "/" + item.getMaterial_orderqty());
        // 下单时间
        helper.setText(R.id.tv_order_time, "下单时间: " + item.getOrder_date());
        // 已完成显示评价，异常完成显示  已评价
        if (item.getOrder_status().equals(OrderStatus.FIVE.getPosition()) || item.getOrder_status().equals(OrderStatus.SIX.getPosition())) {
            sbtEvaluation.setVisibility(View.VISIBLE);
            sbtEvaluation.setText(item.isEvalStatus() ? "已评价" : "评价");
        } else {
            sbtEvaluation.setVisibility(View.GONE);
        }
        // 点击事件
        helper.addOnClickListener(R.id.sbt_evaluation);
    }
}
