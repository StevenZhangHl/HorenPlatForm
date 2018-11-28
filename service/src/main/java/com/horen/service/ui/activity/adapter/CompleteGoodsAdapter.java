package com.horen.service.ui.activity.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.util.ImageLoader;
import com.horen.service.R;
import com.horen.service.bean.OrderAllotInfoBean;
import com.horen.service.enumeration.business.OrderType;

/**
 * @author :ChenYangYi
 * @date :2018/08/10/10:30
 * @description :已完成订单物品信息  出入库通用
 * @github :https://github.com/chenyy0708
 */
public class CompleteGoodsAdapter extends BaseQuickAdapter<OrderAllotInfoBean.ProListBean, BaseViewHolder> {

    /**
     * 订单状态
     */
    private String order_type;

    /**
     * @param layoutResId 布局Id
     * @param order_type  订单状态
     */
    public CompleteGoodsAdapter(int layoutResId, String order_type) {
        super(layoutResId);
        this.order_type = order_type;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderAllotInfoBean.ProListBean item) {
        if (helper.getLayoutPosition() == mData.size() - 1) {
            helper.setGone(R.id.divider, false);
        }
        // 产品图片
        ImageLoader.load(mContext, item.getProduct_photo(), (ImageView) helper.getView(R.id.iv_pic));
        // 产品名称
        helper.setText(R.id.tv_goods_name, item.getProduct_name());
        // 产品类型
        helper.setText(R.id.tv_goods_type, item.getProduct_type());
        // 出入库量
        helper.setText(R.id.tv_outbound_count, String.format(
                order_type.equals(OrderType.OUTBOUND.getStatus()) ? mContext.getString(R.string.service_service_outbound_quantity_tip)
                        : mContext.getString(R.string.service_service_storage_quantity_tip), String.valueOf(item.getPerform_qty())));
    }
}
