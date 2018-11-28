package com.horen.partner.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.util.NumberUtil;
import com.horen.partner.R;
import com.horen.partner.bean.BillListBean;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/29 8:45
 * Description:This isBillAdapter
 */
public class BillAdapter extends BaseQuickAdapter<BillListBean.OrdersBean, BaseViewHolder> {
    public BillAdapter(int layoutResId, @Nullable List<BillListBean.OrdersBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillListBean.OrdersBean item) {
        helper.setText(R.id.tv_customer_name, item.getCompany_name());
        helper.setText(R.id.tv_money, NumberUtil.formitNumberTwoPoint(item.getAmount()));
    }
}
