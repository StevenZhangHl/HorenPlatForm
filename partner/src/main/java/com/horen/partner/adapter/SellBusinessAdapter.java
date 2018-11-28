package com.horen.partner.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.partner.R;
import com.horen.partner.bean.OrderBean;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/22 15:43
 * Description:This isSellBusinessAdapter
 */
public class SellBusinessAdapter extends BaseQuickAdapter<OrderBean.Order, BaseViewHolder> {
    private RecyclerView.RecycledViewPool viewPool;

    public SellBusinessAdapter(int layoutResId, @Nullable List<OrderBean.Order> data) {
        super(layoutResId, data);
        viewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean.Order item) {
        helper.setText(R.id.tv_order_number, "下单单号：" + item.getOrder_id());
        helper.setText(R.id.tv_order_date, item.getFinish_date());
        if ("11".equals(item.getOrder_type())) {
            helper.getView(R.id.tv_top).setBackgroundColor(mContext.getResources().getColor(R.color.base_text_color_light));
        }
        RecyclerView recyclerView = helper.getView(R.id.recyclerview_order_list);
        recyclerView.setRecycledViewPool(viewPool);
        OrderProductAdapter orderProductAdapter = new OrderProductAdapter(R.layout.partner_item_business_order_product, item.getProducts());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(orderProductAdapter);
    }
}
