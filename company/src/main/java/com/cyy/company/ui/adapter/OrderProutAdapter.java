package com.cyy.company.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyy.company.R;
import com.cyy.company.bean.OrderProducts;
import com.horen.base.util.ImageLoader;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/22/16:49
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class OrderProutAdapter extends BaseQuickAdapter<OrderProducts.PdListBean, BaseViewHolder> {
    public OrderProutAdapter(@Nullable List<OrderProducts.PdListBean> data) {
        super(R.layout.item_select_rtp, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderProducts.PdListBean item) {
        ImageLoader.load(mContext, item.getProduct_photo(), (ImageView) helper.getView(R.id.iv_res_image));
        helper.setText(R.id.tv_res_name, item.getProduct_name());
        // 是否已选
        helper.setGone(R.id.tv_select, item.isSelect());
    }
}
