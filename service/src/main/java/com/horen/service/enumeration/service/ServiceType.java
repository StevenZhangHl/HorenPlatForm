package com.horen.service.enumeration.service;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/11:23
 * @description :服务中心  	服务状态值 0:维修 1:清洗，=====已完成不用传
 * 维修状态值 0:未清洗|未维修，4:已清洗已维修
 * @github :https://github.com/chenyy0708
 */

import com.horen.base.app.BaseApp;
import com.horen.service.R;

public enum ServiceType {
    /**
     * 待清洗
     */
    CLEAN(BaseApp.getAppContext().getString(R.string.service_cleaned), "1"),
    /**
     * 待维修
     */
    REPAIR(BaseApp.getAppContext().getString(R.string.service_repair), "0");
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
    ServiceType(String text, String position) {
        this.text = text;
        this.status = position;
    }

    /**
     * 根据类型的名称，返回类型的枚举实例。
     *
     * @param text 类型名称
     */
    public static ServiceType fromTabText(String text) {
        for (ServiceType orderStatus : ServiceType.values()) {
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
        for (ServiceType tabText : ServiceType.values()) {
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