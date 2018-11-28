package com.horen.service.ui.activity.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.service.R;
import com.horen.service.bean.OutInDetailBean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/27/13:12
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class OutofStorageDetailAdapter extends BaseQuickAdapter<OutInDetailBean.OutInStorageListBean, BaseViewHolder> {
    public OutofStorageDetailAdapter(@Nullable List<OutInDetailBean.OutInStorageListBean> data) {
        super(R.layout.service_item_storage_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OutInDetailBean.OutInStorageListBean item) {
        if (helper.getLayoutPosition() % 2 == 0) {
            helper.setBackgroundColor(R.id.ll_content, ContextCompat.getColor(mContext, R.color.color_f5));
        } else {
            helper.setBackgroundColor(R.id.ll_content, ContextCompat.getColor(mContext, R.color.white));
        }
        // 序号
        helper.setText(R.id.tv_serial, String.valueOf(helper.getLayoutPosition() + 1));
        // 日期
        helper.setText(R.id.tv_date, item.getCreateTime());
        // 出入库类型 11出库12入库
        helper.setText(R.id.tv_type, item.getSto_type().equals("11") ? "出库" : "入库");
        // 产品数量
        helper.setText(R.id.tv_count, String.valueOf(item.getStorage_qty()));
    }
}
