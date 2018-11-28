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
 * @description :出入庫Adapter
 * @github :https://github.com/chenyy0708
 */
public class OutofStorageAdapter extends BaseQuickAdapter<StorageCenterBean.ListBean, BaseViewHolder> {
    public OutofStorageAdapter(@Nullable List<StorageCenterBean.ListBean> data) {
        super(R.layout.service_item_out_of_storage, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StorageCenterBean.ListBean item) {
        // 图片
        ImageLoader.load(mContext,item.getProduct_photo(), (ImageView) helper.getView(R.id.iv_pic));
        // 型号，类型
        helper.setText(R.id.tv_name, item.getProduct_name());
        helper.setText(R.id.tv_type, item.getProduct_type());
        // 数量
        helper.setText(R.id.tv_out, String.format(mContext.getString(R.string.service_service_outbound_quantity_tip), String.valueOf(item.getStorage_qty())));
        helper.setText(R.id.tv_in, String.format(mContext.getString(R.string.service_service_storage_quantity_tip), String.valueOf(item.getEnterQty())));
    }
}
