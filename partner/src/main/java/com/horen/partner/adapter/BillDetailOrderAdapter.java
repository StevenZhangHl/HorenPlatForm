package com.horen.partner.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.util.NumberUtil;
import com.horen.partner.R;
import com.horen.partner.bean.BillDetailBean;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/30 8:46
 * Description:This isBillDetailOrderAdapter
 */
public class BillDetailOrderAdapter extends BaseQuickAdapter<BillDetailBean, BaseViewHolder> {
    public BillDetailOrderAdapter(int layoutResId, @Nullable List<BillDetailBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillDetailBean item) {
        helper.setText(R.id.tv_order_number, "订单号：" + item.getOrder_id());
        helper.setText(R.id.tv_order_money, "¥ " + NumberUtil.formitNumberTwoPoint(item.getOrders_amount()));
        helper.setText(R.id.tv_income_date, item.getPay_date());
        RecyclerView recyclerView = helper.getView(R.id.recyclerview_bill_order_product);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(mContext.getResources().getDrawable(R.drawable.list_divider));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        BillDetailOrderProductAdapter productAdapter = new BillDetailOrderProductAdapter(R.layout.partner_item_bill_detail_order_product, item.getProducts());
        recyclerView.setAdapter(productAdapter);
    }
}
