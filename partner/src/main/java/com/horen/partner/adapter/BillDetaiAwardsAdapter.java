package com.horen.partner.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.util.NumberUtil;
import com.horen.partner.R;
import com.horen.partner.bean.BillDetailBean;
import com.horen.partner.bean.BillListBean;

import java.util.List;

/**
 * Author:Steven
 * Time:2018/8/30 8:56
 * Description:This isBillDetaiAwardsAdapter
 */
public class BillDetaiAwardsAdapter extends BaseQuickAdapter<BillListBean.AwardsBean, BaseViewHolder> {
    public BillDetaiAwardsAdapter(int layoutResId, @Nullable List<BillListBean.AwardsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillListBean.AwardsBean item) {
        helper.setText(R.id.tv_money, NumberUtil.formitNumberTwoPoint(item.getAmount()));
        helper.setText(R.id.tv_company_name,item.getCompany_name());
    }
}
