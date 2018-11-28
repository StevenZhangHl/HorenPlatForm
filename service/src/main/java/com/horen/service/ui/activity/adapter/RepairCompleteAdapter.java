package com.horen.service.ui.activity.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.util.CollectionUtils;
import com.horen.service.R;
import com.horen.service.bean.RepairDetailBean;
import com.horen.service.utils.RepairUtils;

/**
 * @author :ChenYangYi
 * @date :2018/08/20/10:06
 * @description :维修历史Adapter
 * @github :https://github.com/chenyy0708
 */
public class RepairCompleteAdapter extends BaseQuickAdapter<RepairDetailBean.ServiceListBean, BaseViewHolder> {

    private RecyclerView rvPhoto;

    public RepairCompleteAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, RepairDetailBean.ServiceListBean item) {
        // 服务单号
        helper.setText(R.id.tv_service_order, item.getService_id());
        // 箱号
        helper.setText(R.id.tv_box_number, item.getCtnr_sn());
        // 维修日期
        helper.setText(R.id.tv_repair_date, item.getUpdate_time());
        // 损坏位置
        RepairUtils.setLocation((LinearLayout) helper.getView(R.id.ll_normal_location), (TextView) helper.getView(R.id.tv_normal_location),
                (LinearLayout) helper.getView(R.id.ll_refuse_location), (TextView) helper.getView(R.id.tv_refuse_location), item.getPositionList());
        // 图片集合
        rvPhoto = helper.getView(R.id.rv_photo);
        rvPhoto.setNestedScrollingEnabled(false);
        rvPhoto.setLayoutManager(new GridLayoutManager(mContext, 4));
        // 图片集合不为空
        if (!CollectionUtils.isNullOrEmpty(item.getPositionList()))
            rvPhoto.setAdapter(new PhotoPreviewAdapter(R.layout.service_item_photo_preview, item.getPositionList().get(0).getImgList()));
    }
}
