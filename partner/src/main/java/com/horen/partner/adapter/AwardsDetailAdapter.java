package com.horen.partner.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.partner.R;
import com.horen.partner.bean.AwardsDetailBean;
import com.horen.partner.bean.BillDetailBean;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/9/25 17:30
 * Description:This isAwardsDetailAdapter
 */
public class AwardsDetailAdapter extends BaseQuickAdapter<AwardsDetailBean, BaseViewHolder> {
    public AwardsDetailAdapter(int layoutResId, @Nullable List<AwardsDetailBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AwardsDetailBean item) {
        helper.setText(R.id.tv_type, item.getProduct_type());
        helper.setText(R.id.tv_product_name, "产品名称：" + item.getProduct_name());
        helper.setText(R.id.tv_product_count, item.getTotal_nums() + "");
    }
}
