package com.horen.service.ui.activity.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.service.R;
import com.horen.service.bean.StorageBean;
import com.horen.service.enumeration.business.OrderType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/10/10:30
 * @description :出库订单交割列表Adapter
 * @github :https://github.com/chenyy0708
 */
public class OutOrderDeliveryAdapter extends BaseQuickAdapter<StorageBean, BaseViewHolder> {

    /**
     * 订单状态
     */
    private String order_type;

    public OutOrderDeliveryAdapter(int layoutResId, String order_type) {
        super(layoutResId);
        this.order_type = order_type;
    }

    @Override
    protected void convert(final BaseViewHolder helper, StorageBean item) {
        // 出入库单号
        helper.setText(R.id.tv_order_number, String.format(
                order_type.equals(OrderType.OUTBOUND.getStatus()) ? mContext.getString(R.string.service_number_delivery)
                        : mContext.getString(R.string.service_storage_number_delivery), String.valueOf(item.getStorage_id())));
        // 时间
        helper.setText(R.id.tv_order_time, String.format(mContext.getString(R.string.service_delivery_time), item.getCreate_time()));
        // 子级订单列表RecycleView
        RecyclerView rvOrderChild = helper.getView(R.id.rv_order_child);
        rvOrderChild.setNestedScrollingEnabled(false);
        rvOrderChild.setLayoutManager(new LinearLayoutManager(mContext));
        OutOrderChildAdapter childAdapter = new OutOrderChildAdapter(R.layout.service_item_order_list);
        rvOrderChild.setAdapter(childAdapter);
        // 交割记录
        childAdapter.setNewData(item.getProList());
        // 交割图片集合
        RecyclerView rvPhoto = helper.getView(R.id.rv_photo);
        rvPhoto.setNestedScrollingEnabled(false);
        rvPhoto.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        PhotoPreviewAdapter previewAdapter = new PhotoPreviewAdapter(R.layout.service_item_photo_preview, PhotoPreviewAdapter.BUSINESS);
        rvPhoto.setAdapter(previewAdapter);
        // 图片集合
        List<String> imageUrls = new ArrayList<>();
        if (!TextUtils.isEmpty(item.getImg1())) {
            imageUrls.add(item.getImg1());
        }
        if (!TextUtils.isEmpty(item.getImg2())) {
            imageUrls.add(item.getImg2());
        }
        // 展示图片
        previewAdapter.setNewData(imageUrls);
        // 展开收缩
        helper.setOnClickListener(R.id.ll_order, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (helper.getView(R.id.ll_expend).getVisibility() == View.VISIBLE) {
                    helper.setGone(R.id.ll_expend, false);
                    helper.setImageResource(R.id.iv_right_arrow, R.drawable.service_ic_down);
                } else {
                    helper.setGone(R.id.ll_expend, true);
                    helper.setImageResource(R.id.iv_right_arrow, R.drawable.service_ic_up);
                }
            }
        });
    }
}
