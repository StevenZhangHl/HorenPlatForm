package com.horen.service.ui.fragment.adapter;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.util.ImageLoader;
import com.horen.service.R;
import com.horen.service.bean.ServiceListBean;
import com.horen.service.enumeration.service.ServiceType;
import com.horen.service.listener.ModifyCleanListener;
import com.horen.service.listener.UpdateListener;
import com.horen.service.ui.activity.service.RepairDetailActivity;
import com.horen.service.widget.ModifyCleanDialog;

/**
 * @author :ChenYangYi
 * @date :2018/08/17/13:46
 * @description : 服务中心，Adapter
 * @github :https://github.com/chenyy0708
 */
public class ServiceAdapter extends BaseQuickAdapter<ServiceListBean.ServiceBean, BaseViewHolder> {
    /**
     * 服务中心类型
     */
    private String type;

    /**
     * 通知主界面更新
     */
    private UpdateListener onUpdateListener;

    public ServiceAdapter(int layoutResId, String type) {
        super(layoutResId);
        this.type = type;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ServiceListBean.ServiceBean item) {
        // 产品图片
        ImageLoader.load(mContext, item.getProduct_photo(), (ImageView) helper.getView(R.id.iv_pic));
        // 颜色
        helper.setTextColor(R.id.tv_status, type.equals(ServiceType.CLEAN.getStatus()) ? ContextCompat.getColor(mContext, R.color.service_color_39C) : ContextCompat.getColor(mContext, R.color.service_color_F15));
        // 名字
        helper.setText(R.id.tv_name, item.getProduct_name());
        // 类型
        helper.setText(R.id.tv_type, item.getProduct_type());
        // 未清洗数量
        helper.setText(R.id.tv_number, String.format(type.equals(ServiceType.CLEAN.getStatus()) ?
                        mContext.getString(R.string.service_not_cleaned) : mContext.getString(R.string.service_damage_number),
                item.getService_qty()));
        // 状态
        helper.setText(R.id.tv_status, type.equals(ServiceType.CLEAN.getStatus()) ? mContext.getString(R.string.service_clean) : mContext.getString(R.string.service_repair_new));
        helper.setOnClickListener(R.id.ll_content, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 待清洗
                if (type.equals(ServiceType.CLEAN.getStatus())) {
                    new ModifyCleanDialog(mContext, item)
                            .setOnModifyClean(new ModifyCleanListener() {
                                @Override
                                public void onModifyCleanCount(int clean_qty) {
                                    if (clean_qty != 0) {
                                        item.setService_qty(clean_qty);
                                        // 更新单条数据
                                        notifyItemChanged(helper.getLayoutPosition());
                                    } else {
                                        remove(helper.getLayoutPosition());
                                    }
                                    // 通知首页更新数据
                                    if (onUpdateListener != null) {
                                        onUpdateListener.onUpdateListener();
                                    }
                                }
                            }).show();
                } else {
                    RepairDetailActivity.startActivity(mContext, item);
                }
            }
        });
    }

    public void setOnUpdateListener(UpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
    }
}
