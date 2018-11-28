package com.horen.partner.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.partner.R;
import com.horen.partner.bean.PropertyBean;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/22 12:58
 * Description:This isPropertyListAdapter
 */
public class PropertyListAdapter extends BaseQuickAdapter<PropertyBean, BaseViewHolder> {
    public PropertyListAdapter(int layoutResId, @Nullable List<PropertyBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PropertyBean item) {
        helper.setText(R.id.tv_good_name, item.getCtnr_name());
        helper.setText(R.id.tv_good_type, item.getCtnr_type());
        helper.setText(R.id.tv_product_total, item.getTotal_num()+"");
        helper.setText(R.id.tv_empty_count, item.getTotal_empty()+"");
        helper.setText(R.id.tv_sign_count, item.getTotal_loss() + item.getTotal_normal()+"");
    }
}
