package com.horen.service.ui.activity.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.util.CollectionUtils;
import com.horen.service.R;
import com.horen.service.bean.RepairDetailBean;
import com.horen.service.ui.activity.service.RepairAddActivity;

/**
 * @author :ChenYangYi
 * @date :2018/08/20/10:06
 * @description :维修列表Adapter
 * @github :https://github.com/chenyy0708
 */
public class RepairListAdapter extends BaseQuickAdapter<RepairDetailBean.ServiceListBean, BaseViewHolder> {

    private RecyclerView rvPhoto;

    public RepairListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final RepairDetailBean.ServiceListBean item) {
        // 报修列表
        helper.setText(R.id.tv_serial, mContext.getString(R.string.service_repair_list) + (helper.getLayoutPosition() + 1));
        // 箱号
        helper.setText(R.id.tv_box_number, item.getCtnr_sn());
        // 损坏位置,拼接损坏位置
        StringBuilder builder = new StringBuilder();
        // 用分号隔开
        for (int i = 0; i < item.getPositionList().size(); i++) {
            builder.append(item.getPositionList().get(i).getPosition());
            // 集合的长度大于1并且不是最后一条 拼接;
            if (item.getPositionList().size() > 1 && item.getPositionList().size() - 1 != i) {
                builder.append("、");
            }
        }
        helper.setText(R.id.tv_damage_location, builder.toString());
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
                RepairAddActivity.startActivity(mContext, item);
            }
        });
    }
}
