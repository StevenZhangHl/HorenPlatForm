package com.cyy.company.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyy.company.R;
import com.cyy.company.bean.OrderDetailBean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/11/21/17:00
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class OrderOverPriceAdapter extends BaseQuickAdapter<OrderDetailBean.PageInfoBean.OverListBean, BaseViewHolder> {
    public OrderOverPriceAdapter(@Nullable List<OrderDetailBean.PageInfoBean.OverListBean> data) {
        super(R.layout.item_overdue_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailBean.PageInfoBean.OverListBean item) {
        helper.setText(R.id.tv_overdue_product, item.getProduct_name())
                .setText(R.id.tv_overdue_qty, item.getBox_orderqty() + "个")
                .setText(R.id.tv_overdue_days, item.getExpired_days() + "天")
        ;
    }
}
