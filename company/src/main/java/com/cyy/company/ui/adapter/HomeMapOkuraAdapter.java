package com.cyy.company.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyy.company.R;
import com.cyy.company.bean.MapOkuraBean;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/25/15:02
 * @description :百网千驿 大仓产品信息
 * @github :https://github.com/chenyy0708
 */
public class HomeMapOkuraAdapter extends BaseQuickAdapter<MapOkuraBean, BaseViewHolder> {
    public HomeMapOkuraAdapter(@Nullable List<MapOkuraBean> data) {
        super(R.layout.item_home_map_pd, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MapOkuraBean item) {
        helper.setText(R.id.tv_type, item.getType());
        RecyclerView recyclerView = helper.getView(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new HomeOkuraChildAdapter(item.getmData()));
        if (helper.getLayoutPosition() == mData.size() - 1) { // 最后一条数据不需要虚线
            helper.setGone(R.id.dash_view, false);
        } else {
            helper.setGone(R.id.dash_view, true);
        }
    }
}
