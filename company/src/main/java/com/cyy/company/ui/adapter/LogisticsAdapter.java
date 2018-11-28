package com.cyy.company.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyy.company.R;
import com.cyy.company.bean.OrderLogs;
import com.cyy.company.utils.DateUtils;
import com.horen.base.util.DisplayUtil;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/18/10:25
 * @description : 物流跟踪Adapter
 * @github :https://github.com/chenyy0708
 */
public class LogisticsAdapter extends BaseQuickAdapter<OrderLogs.OrderIdBean, BaseViewHolder> {

    public LogisticsAdapter(@Nullable List<OrderLogs.OrderIdBean> data) {
        super(R.layout.item_logistics, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderLogs.OrderIdBean item) {
        View viewLine = helper.getView(R.id.view_line);
        RecyclerView mRecyclerView = helper.getView(R.id.recycler_view);
        // 时间颜色
        helper.setTextColor(R.id.tv_time_mouth_day, ContextCompat.getColor(mContext, R.color.color_999))
                .setText(R.id.tv_time_mouth_day, DateUtils.formatNewMonthDay(item.getCreate_date()))
                .setText(R.id.tv_minutes, DateUtils.formatHourMinute(item.getCreate_date()));
        helper.setTextColor(R.id.tv_minutes, ContextCompat.getColor(mContext, R.color.color_999));
        // 物流状态颜色
        helper.setTextColor(R.id.tv_status, ContextCompat.getColor(mContext, R.color.color_999));
        // 显示时间
        helper.setVisible(R.id.ll_time, true);
        // 显示 线
        helper.setVisible(R.id.view_line, true);
        // 默认隐藏物流描述
        helper.setGone(R.id.tv_desc, false);
        helper.setGone(R.id.view_line_top, true);
        if (item.getMovement().equals("0")) { // 提交订单
            helper.setText(R.id.tv_status, "订单已提交");
            // 显示物流描述
            helper.setGone(R.id.tv_desc, true);
            helper.setText(R.id.tv_desc, "您提交了订单，请等待平台审核");
            // 物流状态图标
            helper.setImageResource(R.id.iv_logistics_status, R.drawable.icon_logistics_top);
            // 隐藏 线
            helper.setGone(R.id.view_line, false);
        } else if (item.getMovement().equals("2")) { // 确认订单
            helper.setText(R.id.tv_status, "订单已受理");
            // 显示物流描述
            helper.setGone(R.id.tv_desc, true);
            helper.setText(R.id.tv_desc, "平台审核通过");
            helper.setImageResource(R.id.iv_logistics_status, R.drawable.icon_logistics_top);
            setLineHeight(viewLine, 57);
        } else if (item.getMovement().equals("4")) { // 订单执行中
            helper.setText(R.id.tv_status, "订单执行中");
            // 隐藏时间
            helper.setVisible(R.id.ll_time, false);
            helper.setImageResource(R.id.iv_logistics_status, R.drawable.icon_logistics_point);
            setLineHeight(viewLine, 42);
        } else if (item.getMovement().equals("16")) { // 部分签收
            helper.setText(R.id.tv_status, "部分签收");
            helper.setImageResource(R.id.iv_logistics_status, R.drawable.icon_logistics_top);
            // 部分签收物品
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setAdapter(new LogSigningAdapter(item.getPartList()));
            int height = 6 + item.getPartList().size() * 30 + 40;
            // 动态计算线高度
            setLineHeight(viewLine, height);
        } else if (item.getMovement().equals("32")) { // 完成
            helper.setText(R.id.tv_status, "订单完成");
            helper.setTextColor(R.id.tv_status, ContextCompat.getColor(mContext, R.color.base_text_color_light));
            helper.setTextColor(R.id.tv_time_mouth_day, ContextCompat.getColor(mContext, R.color.base_text_color_light));
            helper.setTextColor(R.id.tv_minutes, ContextCompat.getColor(mContext, R.color.base_text_color_light));
            helper.setImageResource(R.id.iv_logistics_status, R.drawable.icon_logistics_complete);
            // 动态计算线高度
            setLineHeight(viewLine, 56);
        }
        if (helper.getLayoutPosition() == 0) {
            helper.setGone(R.id.view_line_top, false);
        }
    }

    /**
     * 设置线高度
     */
    private void setLineHeight(View line, int height) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) line.getLayoutParams();
        lp.height = DisplayUtil.dip2px(height);
        line.setLayoutParams(lp);
    }
}
