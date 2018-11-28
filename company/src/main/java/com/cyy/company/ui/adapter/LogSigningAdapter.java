package com.cyy.company.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyy.company.R;
import com.cyy.company.bean.OrderLogs;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/11/20/13:08
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class LogSigningAdapter extends BaseQuickAdapter<OrderLogs.PartListBean, BaseViewHolder> {
    public LogSigningAdapter(@Nullable List<OrderLogs.PartListBean> data) {
        super(R.layout.item_logistics_sign_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderLogs.PartListBean item) {
        helper.setText(R.id.tv_sign, "签收明细: " + item.getProduct_type() + "_" + item.getStorage_qty() + "个");
    }
}
