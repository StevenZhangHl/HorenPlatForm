package com.cyy.company.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyy.company.R;
import com.cyy.company.bean.MessageNoticeBean;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/18/17:39
 * @description :通知类消息
 * @github :https://github.com/chenyy0708
 */
public class MsgLogisticsNoticeAdapter extends BaseQuickAdapter<MessageNoticeBean.PdListBean.ListBean, BaseViewHolder> {

    private DeleteListener deleteListener;

    public MsgLogisticsNoticeAdapter(@Nullable List<MessageNoticeBean.PdListBean.ListBean> data, DeleteListener deleteListener) {
        super(R.layout.item_msg_notice, data);
        this.deleteListener = deleteListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, MessageNoticeBean.PdListBean.ListBean item) {
        final SwipeMenuLayout menuLayout = helper.getView(R.id.menu_layout);
        helper.setText(R.id.tv_box_number, item.getMsg_title())
                .setText(R.id.tv_address, item.getMsg_content())
                .setGone(R.id.sb_red_round, item.getSTATUS().equals("1"))// 未读已读  (1：未读，2：已读)
        ;
        // 消息类型
        if (item.getMsg_type().equals("407")) {
            helper.setText(R.id.tv_status, "箱子丢失预警");
        } else if (item.getMsg_type().equals("408")) {
            helper.setText(R.id.tv_status, "租赁到期提醒");
        }
        // 删除
        helper.setOnClickListener(R.id.iv_delete, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuLayout.smoothClose();
                if (deleteListener != null)
                    deleteListener.onDeleteListener(helper.getLayoutPosition());
            }
        });
        SuperButton superButton = helper.getView(R.id.sb_message_date);
        superButton.setText(item.getMsg_date());
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
