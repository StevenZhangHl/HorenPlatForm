package com.horen.partner.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.util.NumberUtil;
import com.horen.partner.R;
import com.horen.partner.bean.BillDetailBean;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/30 10:16
 * Description:This isBillDetailOrderProductAdapter
 */
public class BillDetailOrderProductAdapter extends BaseQuickAdapter<BillDetailBean.ProdcutsBean, BaseViewHolder> {
    public BillDetailOrderProductAdapter(int layoutResId, @Nullable List<BillDetailBean.ProdcutsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillDetailBean.ProdcutsBean item) {
        helper.setText(R.id.tv_product_name, item.getProduct_name());
        helper.setText(R.id.tv_product_count, item.getOrder_amount() + "");
        if (item.getProduct_flag() == 1) {
            helper.setText(R.id.tv_income_type, "租赁");
            helper.getView(R.id.rl_prodcut_type).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_type, item.getProduct_type());
        } else {
            helper.setText(R.id.tv_income_type, "销售");
            helper.getView(R.id.rl_prodcut_type).setVisibility(View.GONE);
        }
    }
}
