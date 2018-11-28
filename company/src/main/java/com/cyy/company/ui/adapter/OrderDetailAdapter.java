package com.cyy.company.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyy.company.R;
import com.cyy.company.bean.OrderDetailBean;
import com.horen.base.util.ImageLoader;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/17/10:26
 * @description :订单详情物品
 * @github :https://github.com/chenyy0708
 */
public class OrderDetailAdapter extends BaseQuickAdapter<OrderDetailBean.PageInfoBean.ListBean, BaseViewHolder> {
    public OrderDetailAdapter(@Nullable List<OrderDetailBean.PageInfoBean.ListBean> data) {
        super(R.layout.item_order_detail_product, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailBean.PageInfoBean.ListBean item) {
        // 产品图片
        ImageLoader.load(mContext, item.getProduct_photo(), (ImageView) helper.getView(R.id.iv_photo));
        // 名称和型号
        helper.setText(R.id.tv_name, item.getProduct_name());
        helper.setText(R.id.tv_type, item.getProduct_type());
        // 数量
        helper.setText(R.id.tv_all_number, "数量: " + item.getOrder_qty());
        // 签收数量
        helper.setText(R.id.tv_sign_number, "已签收数量: " + item.getReceive_qty());
    }
}
