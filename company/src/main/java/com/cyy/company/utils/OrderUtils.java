package com.cyy.company.utils;

import android.support.annotation.DrawableRes;

import com.cyy.company.R;
import com.cyy.company.enums.OrderStatus;
import com.horen.base.bean.RadioSelectBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/17/09:57
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class OrderUtils {

    /**
     * 根据订单状态返回不同的背景
     *
     * @param order_status 订单状态
     * @return
     */
    public static @DrawableRes
    int getOrderHeaderBG(String order_status) {
        if (order_status.equals(OrderStatus.ONE.getPosition())) { // 待处理
            return R.drawable.shape_order_orange;
        } else if (order_status.equals(OrderStatus.TWO.getPosition())) { // 待执行
            return R.drawable.shape_order_orange;
        } else if (order_status.equals(OrderStatus.THREE.getPosition())) { // 执行中
            return R.drawable.shape_order_yellow;
        } else if (order_status.equals(OrderStatus.EIGHT.getPosition())) { // 部分签收
            return R.drawable.shape_order_yellow;
        } else if (order_status.equals(OrderStatus.FOUR.getPosition())) { // 已取消
            return R.drawable.shape_order_gray;
        } else if (order_status.equals(OrderStatus.SEVEN.getPosition())) { //  已作废
            return R.drawable.shape_order_gray;
        } else if (order_status.equals(OrderStatus.FIVE.getPosition())) { // 完成
            return R.drawable.shape_order_green;
        } else if (order_status.equals(OrderStatus.SIX.getPosition())) { // 异常完成
            return R.drawable.shape_order_green;
        }
        return R.drawable.shape_order_green;
    }

    /**
     * 根据订单状态返回不同的图片
     *
     * @param order_status 订单状态
     * @return
     */
    public static @DrawableRes
    int getOrderHeaderImg(String order_status) {
        if (order_status.equals(OrderStatus.ONE.getPosition())) { // 待处理
            return R.drawable.icon_dai_chuli;
        } else if (order_status.equals(OrderStatus.TWO.getPosition())) { // 待执行
            return R.drawable.icon_dai_zhixing;
        } else if (order_status.equals(OrderStatus.THREE.getPosition())) { // 执行中
            return R.drawable.icon_zhixing_zhong;
        } else if (order_status.equals(OrderStatus.EIGHT.getPosition())) { // 部分签收
            return R.drawable.icon_zhixing_zhong;
        } else if (order_status.equals(OrderStatus.FOUR.getPosition())) { // 已取消
            return R.drawable.icon_order_cancle;
        } else if (order_status.equals(OrderStatus.SEVEN.getPosition())) { // 已作废
            return R.drawable.icon_order_cancle;
        } else if (order_status.equals(OrderStatus.FIVE.getPosition())) { // 完成
            return R.drawable.icon_order_complete;
        } else if (order_status.equals(OrderStatus.SIX.getPosition())) { // 异常完成
            return R.drawable.icon_exception_complete;
        }
        return R.drawable.shape_order_green;
    }

    public static List<RadioSelectBean> getTransModeBean() {
        List<RadioSelectBean> radioSelectBeans = new ArrayList<>();
        radioSelectBeans.add(new RadioSelectBean("自提", "1"));
        radioSelectBeans.add(new RadioSelectBean("配送", "0"));
        return radioSelectBeans;
    }

    public static List<RadioSelectBean> getReturnTransModeBean() {
        List<RadioSelectBean> radioSelectBeans = new ArrayList<>();
        radioSelectBeans.add(new RadioSelectBean("自送", "1"));
        radioSelectBeans.add(new RadioSelectBean("收箱", "0"));
        return radioSelectBeans;
    }
}
