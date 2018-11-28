package com.horen.service.ui.activity.adapter;

import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.service.R;
import com.horen.service.bean.StorageBean;

/**
 * @author :ChenYangYi
 * @date :2018/08/10/10:30
 * @description :调拨已完成订单物品信息
 * @github :https://github.com/chenyy0708
 */
public class SchedulingCompleteAdapter extends BaseQuickAdapter<StorageBean.ProListBean, BaseViewHolder> {

    public SchedulingCompleteAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, StorageBean.ProListBean item) {
        if (helper.getLayoutPosition() % 2 == 0) {
            helper.setBackgroundColor(R.id.ll_content, ContextCompat.getColor(mContext, R.color.color_f5));
        } else {
            helper.setBackgroundColor(R.id.ll_content, ContextCompat.getColor(mContext, R.color.white));
        }
        // 序号
        helper.setText(R.id.tv_serial, String.valueOf(helper.getLayoutPosition() + 1));
        // 产品名称
        helper.setText(R.id.tv_name, item.getProduct_name());
        // 产品类型
        helper.setText(R.id.tv_product_number, item.getProduct_type());
        // 数量
        helper.setText(R.id.tv_quantity, String.valueOf(item.getStorage_qty()));
    }
}
