package com.horen.service.enumeration.service;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/11:23
 * @description :仓储中心  	1:出入库统计 ，2:产品库存 ，3:耗材库存
 * @github :https://github.com/chenyy0708
 */

public enum StorageType {
    /**
     * 出入库统计
     */
    OUTIN("出入库统计", "1"),
    /**
     * 产品库存
     */
    PRODUCT("产品库存", "2"),
    /**
     * 耗材库存
     */
    SUPPLIES("耗材库存", "3");
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
    StorageType(String text, String position) {
        this.text = text;
        this.status = position;
    }

    /**
     * 根据类型的名称，返回类型的枚举实例。
     *
     * @param text 类型名称
     */
    public static StorageType fromTabText(String text) {
        for (StorageType orderStatus : StorageType.values()) {
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
        for (StorageType tabText : StorageType.values()) {
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