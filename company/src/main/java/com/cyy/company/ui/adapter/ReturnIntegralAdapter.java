package com.cyy.company.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyy.company.R;
import com.cyy.company.bean.ReturnIntegra;
import com.horen.base.util.DividerTopBottomDecoration_1;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/11/19/10:06
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class ReturnIntegralAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ReturnIntegralAdapter(@Nullable List<String> data) {
        super(R.layout.item_return_integral, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        RecyclerView mRecyclerView = helper.getView(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerTopBottomDecoration_1(mContext));
//       mRecyclerView.setAdapter(new IntegralAdapter(TestAdapter.mData));
    }

    /**
     * 积分详情
     */
    public static class IntegralAdapter extends BaseQuickAdapter<ReturnIntegra.PdListBean, BaseViewHolder> {
        public IntegralAdapter(@Nullable List<ReturnIntegra.PdListBean> data) {
            super(R.layout.item_return_integral_detail, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ReturnIntegra.PdListBean item) {
            helper.setText(R.id.tv_time, item.getCreate_date());
            if (item.getPoint() > 0) {
                helper.setText(R.id.tv_status, "增加还箱点");
                helper.setText(R.id.tv_integral, "+" + item.getPoint());
            } else {
                helper.setText(R.id.tv_status, "使用还箱点");
                helper.setText(R.id.tv_integral, String.valueOf(item.getPoint()));
            }
        }
    }
}
