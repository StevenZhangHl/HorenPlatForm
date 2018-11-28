package com.horen.service.ui.activity.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.util.CollectionUtils;
import com.horen.service.R;
import com.horen.service.bean.RepairDetailBean;
import com.horen.service.ui.activity.service.RepairEditActivity;
import com.horen.service.utils.OrderUtils;

/**
 * @author :ChenYangYi
 * @date :2018/08/20/10:06
 * @description :维修详情列表Adapter
 * @github :https://github.com/chenyy0708
 */
public class RepairDetailAdapter extends BaseQuickAdapter<RepairDetailBean.ServiceListBean, BaseViewHolder> {

    private RecyclerView rvPhoto;

    public RepairDetailAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final RepairDetailBean.ServiceListBean item) {
        // 服务单号
        helper.setText(R.id.tv_order_number, item.getService_id());
        // 箱号
        helper.setText(R.id.tv_box_number, item.getCtnr_sn());
        // 责任方 责任人：0：仓储 1：服务商
        helper.setText(R.id.tv_responsible_party, OrderUtils.checkPerson(item.getIs_person()));
        // 报备日期
        helper.setText(R.id.tv_report_date, item.getCreate_time());
        // 图片集合
        rvPhoto = helper.getView(R.id.rv_photo);
        rvPhoto.setNestedScrollingEnabled(false);
        rvPhoto.setLayoutManager(new GridLayoutManager(mContext, 4));
        if (!CollectionUtils.isNullOrEmpty(item.getPositionList()))
            rvPhoto.setAdapter(new PhotoPreviewAdapter(R.layout.service_item_photo_preview, item.getPositionList().get(0).getImgList()));
        // 编辑
        helper.setOnClickListener(R.id.iv_edit, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RepairEditActivity.startActivity(mContext, item);
            }
        });
        // 待平台确认状态  0：提交 1:待审核 2：维修中 3：客户待确认 4：已完成   0、1未确认
        if (OrderUtils.checkServiceStatus(item.getService_status())) {
            helper.setText(R.id.tv_status, "待平台确认");
            helper.setTextColor(R.id.tv_status, ContextCompat.getColor(mContext, R.color.service_color_F15));
            helper.setVisible(R.id.iv_edit, true);
            helper.setGone(R.id.ll_responsible_party, false);
        } else {
            helper.setText(R.id.tv_status, "待维修");
            helper.setTextColor(R.id.tv_status, ContextCompat.getColor(mContext, R.color.service_color_6FB));
            helper.setVisible(R.id.iv_edit, false);
            helper.setGone(R.id.ll_responsible_party, true);
        }
    }
}
