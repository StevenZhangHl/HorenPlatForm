package com.horen.service.enumeration.business;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/11:23
 * @description :订单状态  3已审核，4已完成
 * @github :https://github.com/chenyy0708
 */

import com.horen.base.app.BaseApp;
import com.horen.service.R;

public enum OrderStatus {
    /**
     * 待处理
     */
    PENDING(BaseApp.getAppContext().getString(R.string.service_pending), "3"),
    /**
     * 已完成
     */
    COMPLETE(BaseApp.getAppContext().getString(R.string.service_completed), "4");

    /**
     * 字段名
     */
    private String text;
    /**
     * 状态值
     */
    private String status;

    /**
     * 构造方法
     */
    OrderStatus(String text, String position) {
        this.text = text;
        this.status = position;
    }

    /**
     * 根据类型的名称，返回类型的枚举实例。
     *
     * @param text 类型名称
     */
    public static OrderStatus fromTabText(String text) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.getText().equals(text)) {
                return orderStatus;
            }
        }
        return null;
    }

    /**
     * 根据类型的名称，返回类型的枚举实例。
     *
     * @param position 类型名称
     */
    public static String fromTabPosition(String position) {
        for (OrderStatus tabText : OrderStatus.values()) {
            if (tabText.getStatus().equals(position)) {
                return tabText.getText();
            }
        }
        return null;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}