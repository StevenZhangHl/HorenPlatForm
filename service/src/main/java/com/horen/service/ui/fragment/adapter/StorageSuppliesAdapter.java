package com.horen.service.ui.fragment.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.service.R;
import com.horen.service.bean.StorageCenterBean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/27/13:12
 * @description :耗材
 * @github :https://github.com/chenyy0708
 */
public class StorageSuppliesAdapter extends BaseQuickAdapter<StorageCenterBean.ListBean, BaseViewHolder> {
    public StorageSuppliesAdapter(@Nullable List<StorageCenterBean.ListBean> data) {
        super(R.layout.service_item_supplies, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StorageCenterBean.ListBean item) {
        if (helper.getLayoutPosition() % 2 == 0) {
            helper.setBackgroundColor(R.id.ll_content, ContextCompat.getColor(mContext, R.color.color_f5));
        } else {
            helper.setBackgroundColor(R.id.ll_content, ContextCompat.getColor(mContext, R.color.white));
        }
        // 名称
        helper.setText(R.id.tv_name,item.getProduct_name());
        // 型号
        helper.setText(R.id.tv_type,item.getProduct_type());
        // 计量单位
        helper.setText(R.id.tv_unit,item.getProduct_unitname());
        // 库存量
        helper.setText(R.id.tv_inventory,String.valueOf(item.getWarehouse_qty()));
    }
}
