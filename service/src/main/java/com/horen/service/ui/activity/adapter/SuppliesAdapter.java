package com.horen.service.ui.activity.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.service.R;
import com.horen.service.bean.RepairDetailBean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/23/16:23
 * @description :耗材列表
 * @github :https://github.com/chenyy0708
 */
public class SuppliesAdapter extends BaseQuickAdapter<RepairDetailBean.ServiceListBean.ConsumablesListBean, BaseViewHolder> {

    public SuppliesAdapter(int layoutResId, @Nullable List<RepairDetailBean.ServiceListBean.ConsumablesListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RepairDetailBean.ServiceListBean.ConsumablesListBean item) {
        if (helper.getLayoutPosition() % 2 == 0) {
            helper.setBackgroundColor(R.id.ll_content, ContextCompat.getColor(mContext, R.color.color_f5));
        } else {
            helper.setBackgroundColor(R.id.ll_content, ContextCompat.getColor(mContext, R.color.white));
        }
        // 序号
        helper.setText(R.id.tv_serial, String.valueOf(helper.getLayoutPosition() + 1));
        // 耗材名称
        helper.setText(R.id.tv_name, item.getProduct_name());
        // 产品类型
        helper.setText(R.id.tv_model, item.getProduct_type());
        // 使用数量
        helper.setText(R.id.tv_consumable_number, item.getQty());
        // 库存数量
        helper.setText(R.id.tv_inventory, String.valueOf(item.getWarehouse_qty()));
    }
}
