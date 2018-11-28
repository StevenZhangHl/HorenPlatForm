package com.horen.partner.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.util.NumberUtil;
import com.horen.partner.R;
import com.horen.partner.bean.WalletBillBean;

import java.util.List;

import javax.crypto.spec.RC2ParameterSpec;

/**
 * Author:Steven
 * Time:2018/9/3 15:48
 * Description:This isWalletBillAdapter
 */
public class WalletBillAdapter extends BaseQuickAdapter<WalletBillBean.ListBean, BaseViewHolder> {
    public WalletBillAdapter(int layoutResId, @Nullable List<WalletBillBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WalletBillBean.ListBean item) {
        helper.setText(R.id.tv_cash_date, item.getDate());
        TextView tv_cash_value = helper.getView(R.id.tv_cash_value);
        String typeTitle = "";
        String moneyType = "";
        if ("A".equals(item.getType())) {
            typeTitle = "提现申请-申请中";
        }
        if ("B".equals(item.getType())) {
            moneyType = "+";
            typeTitle = item.getType_title();
            tv_cash_value.setTextColor(mContext.getResources().getColor(R.color.base_text_color_light));
        }
        if ("C".equals(item.getType())) {
            typeTitle = "提现申请-处理完成";
        }
        tv_cash_value.setText(moneyType + NumberUtil.formitNumber(item.getAmount()));
        helper.setText(R.id.tv_cash_state, typeTitle);
    }
}
