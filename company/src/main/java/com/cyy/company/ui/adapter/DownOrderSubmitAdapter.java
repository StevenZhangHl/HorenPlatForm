package com.cyy.company.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyy.company.R;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/11/07/16:08
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class DownOrderSubmitAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public DownOrderSubmitAdapter(@Nullable List<String> data) {
        super(R.layout.item_down_order_id, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_order_rent_name, "订单号" + (helper.getLayoutPosition() + 1))
                .setText(R.id.tv_order_number, item);
    }
}
