package com.cyy.company.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyy.company.R;
import com.cyy.company.bean.BillDetailBean;
import com.horen.base.util.NumberUtil;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/11/19/10:06
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class BillManagerDetailAdapter extends BaseQuickAdapter<BillDetailBean.PdListBean.ListBean, BaseViewHolder> {
    public BillManagerDetailAdapter(@Nullable List<BillDetailBean.PdListBean.ListBean> data) {
        super(R.layout.item_bill_manager_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillDetailBean.PdListBean.ListBean item) {
        helper.setText(R.id.tv_business_number, item.getOrder_id()) // 业务单号
                .setText(R.id.tv_collection_number, item.getAccount_bill_id()) // 收款单号
                .setText(R.id.tv_rent_money, "¥ " + NumberUtil.formitNumberTwoPoint(item.getLine_rentar())) // 租金
                .setText(R.id.tv_supplies_money, "¥ " + NumberUtil.formitNumberTwoPoint(item.getCycle_amt2())) // 耗材费
                .setText(R.id.tv_freight_money, "¥ " + NumberUtil.formitNumberTwoPoint(item.getLine_tmsar())) // 运费
                .setText(R.id.tv_deposit, "¥ " + NumberUtil.formitNumberTwoPoint(item.getLine_foregiftar())) // 押金
                .setText(R.id.tv_preferential_money, "¥ " + NumberUtil.formitNumberTwoPoint(item.getLine_discount())) // 优惠费用
                .setText(R.id.tv_overdue_fee, "¥ " + NumberUtil.formitNumberTwoPoint(item.getBeyond_amt())) // 超期费
                .setText(R.id.tv_repair_fee, "¥ " + NumberUtil.formitNumberTwoPoint(item.getRemaid_amt())) // 维修费
                .setText(R.id.tv_all_fee, "¥ " + NumberUtil.formitNumberTwoPoint(item.getTotal_ar())) // 总金额
                .setText(R.id.tv_received_fee, "¥ " + NumberUtil.formitNumberTwoPoint(item.getConfirm_amt())) // 已收金额
                .setText(R.id.tv_outstanding_free, "¥ " + NumberUtil.formitNumberTwoPoint(item.getTotal_ar() - item.getConfirm_amt())) // 未收金额 = 总金额 - 已收金额
        ;
    }

}
