package com.cyy.company.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyy.company.R;
import com.cyy.company.bean.AnalysisLineChart;
import com.horen.base.util.DisplayUtil;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/29/14:29
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class AssetsTagAdapter extends BaseQuickAdapter<AnalysisLineChart.PdListBean, BaseViewHolder> {

    /**
     * 当前选中tag
     */
    private int currentSelectPosition = 0;

    public AssetsTagAdapter(@Nullable List<AnalysisLineChart.PdListBean> data) {
        super(R.layout.item_eye_assets_rent, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnalysisLineChart.PdListBean item) {
        SuperButton button = helper.getView(R.id.sbt_tag);
        button.setText(item.getType());
        button.setTextSize(12);
        button.setPadding(DisplayUtil.dip2px(15), 0, DisplayUtil.dip2px(15), 0);
        button.setShapeCornersRadius(4);
        if (helper.getLayoutPosition() == currentSelectPosition) { // 选中
            item.setSelect(true);
            button.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            button.setShapeSolidColor(ContextCompat.getColor(mContext, R.color.base_text_color_light))
                    .setShapeStrokeWidth(0)
                    .setUseShape();
        } else { // 未选中
            item.setSelect(false);
            button.setTextColor(ContextCompat.getColor(mContext, R.color.color_999));
            button.setShapeSolidColor(ContextCompat.getColor(mContext, R.color.white))
                    .setShapeStrokeWidth(1)
                    .setShapeStrokeColor(ContextCompat.getColor(mContext, R.color.DCDCDC))
                    .setUseShape();
        }
    }

    public void notifyData(int currentSelectPosition, List<AnalysisLineChart.PdListBean> mData) {
        this.currentSelectPosition = currentSelectPosition;
        setNewData(mData);
    }

    public void setSelectPosition(int position) {
        this.currentSelectPosition = position;
        notifyDataSetChanged();
    }
}
