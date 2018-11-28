package com.cyy.company.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyy.company.R;
import com.cyy.company.bean.MsgLogisticsBean;
import com.cyy.company.enums.OrderType;
import com.horen.base.util.ImageLoader;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/18/17:39
 * @description :物流跟踪
 * @github :https://github.com/chenyy0708
 */
public class MsgLogisticsAdapter extends BaseQuickAdapter<MsgLogisticsBean.PdListBean.ListBean, BaseViewHolder> {

    private DeleteListener deleteListener;

    public MsgLogisticsAdapter(@Nullable List<MsgLogisticsBean.PdListBean.ListBean> data, DeleteListener deleteListener) {
        super(R.layout.item_msg_logistics, data);
        this.deleteListener = deleteListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, MsgLogisticsBean.PdListBean.ListBean item) {
        final SwipeMenuLayout menuLayout = helper.getView(R.id.menu_layout);
        ImageLoader.load(mContext, item.getProduct_photo(), (ImageView) helper.getView(R.id.iv_photo));
        // 订单类型
        helper.setText(R.id.tv_order_type,
                item.getOrder_type().equals(OrderType.ONE.getPosition()) ? "[订箱订单]" : "[还箱订单]")
                .setText(R.id.tv_order_status, item.getLog())
                .setText(R.id.tv_order_date, item.getCreate_date())
                .setText(R.id.tv_order_number, "订单号: " + item.getOrder_id())
                .setGone(R.id.sb_red_round, item.getStatus().equals("1"))// 未读已读  (1：未读，2：已读)
                .setText(R.id.tv_all_box, "已签收/总箱数: " + item.getBox_receiveqty() + "/" + item.getBox_orderqty()) // 箱子已签收/总箱数
                .setText(R.id.tv_all_supplies, "已签收/总耗材数: " + item.getMaterial_receiveqty() + "/" + item.getMaterial_orderqty());// 耗材已签收/总耗材数
        SuperButton superButton = helper.getView(R.id.sb_message_date);
        superButton.setText(item.getCreate_date_time());
        // 删除
        helper.setOnClickListener(R.id.iv_delete, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuLayout.smoothClose();
                if (deleteListener != null)
                    deleteListener.onDeleteListener(helper.getLayoutPosition());
            }
        });
        // 已读
        helper.setOnClickListener(R.id.item_content, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteListener != null)
                    deleteListener.onItemClickListener(helper.getLayoutPosition());
            }
        });
    }

    /**
     * 删除信息
     */
    public interface DeleteListener {
        void onDeleteListener(int position);

        void onItemClickListener(int position);
    }
}
