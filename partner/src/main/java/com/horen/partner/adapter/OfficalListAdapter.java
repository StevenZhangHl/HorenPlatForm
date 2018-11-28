package com.horen.partner.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.partner.R;
import com.horen.partner.bean.PotentialBean;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/14 9:41
 * Description:This isOfficalListAdapter
 */
public class OfficalListAdapter extends BaseQuickAdapter<PotentialBean.ListBean, BaseViewHolder> {
    public OfficalListAdapter(int layoutResId, @Nullable List<PotentialBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PotentialBean.ListBean item) {
        helper.setText(R.id.tv_company_name, item.getCustomer_name());
        helper.addOnClickListener(R.id.iv_detail);
    }
}
