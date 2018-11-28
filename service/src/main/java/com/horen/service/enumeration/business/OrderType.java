package com.horen.service.enumeration.business;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/11:23
 * @description :订单类型  11：出库 12：入库 99 ：调拨
 * @github :https://github.com/chenyy0708
 */

import com.horen.base.app.BaseApp;
import com.horen.service.R;

public enum OrderType {
    /**
     * 出库
     */
    OUTBOUND(BaseApp.getAppContext().getString(R.string.service_outbound_order), "11"),
    /**
     * 入库
     */
    STORAGE(BaseApp.getAppContext().getString(R.string.service_storage_order), "12"),
    /**
     * 调拨
     */
    SCHEDULING(BaseApp.getAppContext().getString(R.string.service_scheduling_order), "99");

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
    OrderType(String text, String position) {
        this.text = text;
        this.status = position;
    }

    /**
     * 根据类型的名称，返回类型的枚举实例。
     *
     * @param text 类型名称
     */
    public static OrderType fromTabText(String text) {
        for (OrderType orderStatus : OrderType.values()) {
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
        for (OrderType tabText : OrderType.values()) {
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