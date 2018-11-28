package com.cyy.company.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyy.company.R;
import com.cyy.company.bean.BillListBean;
import com.cyy.company.utils.DateUtils;
import com.horen.base.util.NumberUtil;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/11/19/10:06
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class BillManagerAdapter extends BaseQuickAdapter<BillListBean.PdListBean.ListBean, BaseViewHolder> {
    public BillManagerAdapter(@Nullable List<BillListBean.PdListBean.ListBean> data) {
        super(R.layout.item_bill_manager, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillListBean.PdListBean.ListBean item) {
        helper.setText(R.id.tv_bill_id, item.getAccount_bill_id()) // 账单号
                .setText(R.id.tv_all_money, NumberUtil.formitNumber(item.getTotal_ar())) // 总金额
                .setText(R.id.tv_write_money, NumberUtil.formitNumber(item.getConfirm_amt())) // 已核销金额
                .setText(R.id.tv_time, item.getBegin_last_month() + " 至 " +
                        item.getEnd_last_month());  // 时间
        if (item.getAccount_type().equals("1")) { // 收款
            helper.setText(R.id.tv_status, "已结清")
                    .setTextColor(R.id.tv_status, ContextCompat.getColor(mContext, R.color.base_text_color_light));
        } else { // 付款
            helper.setText(R.id.tv_status, "未结清")
                    .setTextColor(R.id.tv_status, ContextCompat.getColor(mContext, R.color.color_F15));
        }
    }

}
