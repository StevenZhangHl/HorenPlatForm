package com.horen.service.ui.activity.adapter;

import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.util.ImageLoader;
import com.horen.service.R;
import com.horen.service.bean.ServiceListBean;
import com.horen.service.enumeration.service.ServiceType;

/**
 * @author :ChenYangYi
 * @date :2018/08/21/14:32
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class ServiceCompleteAdapter extends BaseQuickAdapter<ServiceListBean.ServiceBean, BaseViewHolder> {
    public ServiceCompleteAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceListBean.ServiceBean item) {
        // 产品图片
        ImageLoader.load(mContext,item.getProduct_photo(), (ImageView) helper.getView(R.id.iv_pic));
        // 名字
        helper.setText(R.id.tv_name, item.getProduct_name());
        // 类型
        helper.setText(R.id.tv_type, item.getProduct_type());
        if (item.getService_type().equals(ServiceType.CLEAN.getStatus())) {
            helper.setText(R.id.tv_status, R.string.service_clean);
            helper.setTextColor(R.id.tv_status, ContextCompat.getColor(mContext, R.color.service_color_39C));
            // 清洗数量
            helper.setText(R.id.tv_number, String.format(mContext.getString(R.string.service_clean_total), String.valueOf(item.getService_qty())));
        } else {
            helper.setText(R.id.tv_status, R.string.service_repair_new);
            helper.setTextColor(R.id.tv_status, ContextCompat.getColor(mContext, R.color.service_color_F15));
            // 维修数量
            helper.setText(R.id.tv_number, String.format(mContext.getString(R.string.service_repair_total), String.valueOf(item.getService_qty())));
        }
    }
}
