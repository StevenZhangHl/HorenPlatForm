package com.horen.service.ui.activity.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.service.R;
import com.horen.service.bean.RepairDetailBean;

/**
 * @author :ChenYangYi
 * @date :2018/08/20/10:06
 * @description :清洗历史Adapter
 * @github :https://github.com/chenyy0708
 */
public class CleanCompleteAdapter extends BaseQuickAdapter<RepairDetailBean.ServiceListBean, BaseViewHolder> {

    private RecyclerView rvPhoto;

    public CleanCompleteAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, RepairDetailBean.ServiceListBean item) {
        if (helper.getLayoutPosition() % 2 == 0) {
            helper.setBackgroundColor(R.id.ll_content, ContextCompat.getColor(mContext, R.color.color_f5));
        } else {
            helper.setBackgroundColor(R.id.ll_content, ContextCompat.getColor(mContext, R.color.white));
        }
        // 序号
        helper.setText(R.id.tv_serial, String.valueOf(helper.getLayoutPosition() + 1));
        // 日期
        helper.setText(R.id.tv_date, item.getCreate_time());
        // 清洗数量
        helper.setText(R.id.tv_number, String.valueOf(item.getService_qty()));
    }
}
