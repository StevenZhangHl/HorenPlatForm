package com.horen.service.ui.fragment.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.util.ImageLoader;
import com.horen.service.R;
import com.horen.service.bean.StorageCenterBean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/27/10:48
 * @description :产品库存Adapter
 * @github :https://github.com/chenyy0708
 */
public class ProductAdapter extends BaseQuickAdapter<StorageCenterBean.ListBean, BaseViewHolder> {
    public ProductAdapter(@Nullable List<StorageCenterBean.ListBean> data) {
        super(R.layout.service_item_product_storage, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StorageCenterBean.ListBean item) {
        // 图片
        ImageLoader.load(mContext, item.getProduct_photo(), (ImageView) helper.getView(R.id.iv_pic));
        // 型号，类型
        helper.setText(R.id.tv_name, item.getProduct_name());
        helper.setText(R.id.tv_type, item.getProduct_type());
        // 库存量
        helper.setText(R.id.tv_all, String.valueOf(item.getWarehouse_qty()));
        // 可用量
        helper.setText(R.id.tv_available, String.format(mContext.getString(R.string.service_available_number), String.valueOf(item.getAvailable_qty())));
        // 未清洗
        helper.setText(R.id.tv_cleaned, String.format(mContext.getString(R.string.service_cleaned_tip), String.valueOf(item.getClean_qty())));
        // 可用量
        helper.setText(R.id.tv_repair, String.format(mContext.getString(R.string.service_repair_tip), String.valueOf(item.getRepair_qty())));
    }
}
