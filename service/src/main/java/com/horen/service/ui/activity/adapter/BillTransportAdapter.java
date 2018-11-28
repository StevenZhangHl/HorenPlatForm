package com.horen.service.ui.activity.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.service.R;
import com.horen.service.bean.BillTransportBean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/09/13/15:31
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class BillTransportAdapter extends BaseQuickAdapter<BillTransportBean.PageInfoBean.ListBean, BaseViewHolder> {
    public BillTransportAdapter(@Nullable List<BillTransportBean.PageInfoBean.ListBean> data) {
        super(R.layout.service_item_bill_transport, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillTransportBean.PageInfoBean.ListBean item) {
        // 单号
        helper.setText(R.id.tv_order_number, item.getOrderallot_id());
        // 时间
        helper.setText(R.id.tv_time, item.getCreate_time());
    }
}
