package com.horen.service.ui.activity.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.util.NumberUtil;
import com.horen.service.R;
import com.horen.service.bean.BillTransportDetail;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/09/04/10:10
 * @description :账单中心 运输费详情时间列表Adapter
 * @github :https://github.com/chenyy0708
 */
public class BilTransportlDateAdapter extends BaseQuickAdapter<BillTransportDetail.BillLogListBean, BaseViewHolder> {
    public BilTransportlDateAdapter(@Nullable List<BillTransportDetail.BillLogListBean> data) {
        super(R.layout.service_item_bill_detail_date, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillTransportDetail.BillLogListBean item) {
        // 时间
        helper.setText(R.id.tv_time, item.getCreate_time());
        // 金额
        helper.setText(R.id.tv_money, "¥" + NumberUtil.formitNumberTwoPoint(Double.valueOf(item.getConfirm_amt())));
    }
}
