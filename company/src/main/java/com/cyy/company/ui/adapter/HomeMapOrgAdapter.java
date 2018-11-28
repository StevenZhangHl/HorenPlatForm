package com.cyy.company.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyy.company.R;
import com.cyy.company.bean.HomeOrgDetail;
import com.horen.base.widget.HRTabView;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/25/15:02
 * @description :百网千驿 网点产品信息
 * @github :https://github.com/chenyy0708
 */
public class HomeMapOrgAdapter extends BaseQuickAdapter<HomeOrgDetail.PdListBean, BaseViewHolder> {
    public HomeMapOrgAdapter(@Nullable List<HomeOrgDetail.PdListBean> data) {
        super(R.layout.item_map_org_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeOrgDetail.PdListBean item) {
        helper.setText(R.id.tv_type, item.getProduct_type());
        HRTabView tabView = helper.getView(R.id.tab_view);
        tabView.setTabText(String.valueOf(item.getTotal_qty()),
                String.valueOf(item.getEmpty_qty()), String.valueOf(item.getFull_qty()), String.valueOf(item.getEn_route_qty()));
    }
}
