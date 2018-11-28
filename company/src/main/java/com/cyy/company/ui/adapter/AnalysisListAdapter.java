package com.cyy.company.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyy.company.R;
import com.cyy.company.bean.AnalysisTotal;
import com.horen.base.widget.HRTabView;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/29/14:29
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class AnalysisListAdapter extends BaseQuickAdapter<AnalysisTotal.PdListBean, BaseViewHolder> {

    public AnalysisListAdapter(@Nullable List<AnalysisTotal.PdListBean> data) {
        super(R.layout.item_map_org_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnalysisTotal.PdListBean item) {
        helper.setText(R.id.tv_type, item.getProduct_type());
        HRTabView tabView = helper.getView(R.id.tab_view);
        tabView.setTabText(String.valueOf(item.getTotal_qty()),
                String.valueOf(item.getEmpty_qty()), String.valueOf(item.getFull_qty()), String.valueOf(item.getEn_route_qty()));
    }

}
