package com.cyy.company.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyy.company.R;
import com.cyy.company.bean.HomeOkuraDetail;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/25/16:26
 * @description :大仓产品信息
 * @github :https://github.com/chenyy0708
 */
public class HomeOkuraChildAdapter extends BaseQuickAdapter<HomeOkuraDetail.PdListBean, BaseViewHolder> {
    public HomeOkuraChildAdapter(@Nullable List<HomeOkuraDetail.PdListBean> data) {
        super(R.layout.item_home_map_list_child, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeOkuraDetail.PdListBean item) {
        helper.setText(R.id.tv_type, item.getProduct_type())
                .setText(R.id.tv_number, String.valueOf(item.getQty()));
    }
}
