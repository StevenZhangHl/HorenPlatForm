package com.horen.partner.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.app.BaseApp;
import com.horen.partner.R;
import com.horen.partner.bean.OrderBean;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/20 9:30
 * Description:This isOrderProductAdapter
 */
public class OrderProductAdapter extends BaseQuickAdapter<OrderBean.Order.ProductsBean, BaseViewHolder> {
    public OrderProductAdapter(int layoutResId, @Nullable List<OrderBean.Order.ProductsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean.Order.ProductsBean item) {
        helper.setText(R.id.tv_num, (helper.getLayoutPosition() + 1) + "");
        helper.setText(R.id.tv_product_name, item.getProduct_name());
        helper.setText(R.id.tv_product_type, item.getProduct_type());
        helper.setText(R.id.tv_product_count, item.getReceive_qty() + "");
        if (helper.getLayoutPosition() % 2 == 0) {
            helper.itemView.setBackgroundColor(BaseApp.getAppResources().getColor(R.color.color_f5));
        } else {
            helper.itemView.setBackgroundColor(BaseApp.getAppResources().getColor(R.color.white));
        }
    }
}
