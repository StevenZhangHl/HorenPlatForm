package com.horen.service.enumeration.bill;

/**
 * @author :ChenYangYi
 * @date :2018/08/14/11:23
 * @description :账单中心  	账单状态 1未完成，3已完成
 * @github :https://github.com/chenyy0708
 */

public enum BillType {
    /**
     * 待清洗
     */
    NO_COMPLETE("应收账", "1"),
    /**
     * 待维修
     */
    COMPLETE("已完成", "3");
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
    BillType(String text, String position) {
        this.text = text;
        this.status = position;
    }

    /**
     * 根据类型的名称，返回类型的枚举实例。
     *
     * @param text 类型名称
     */
    public static BillType fromTabText(String text) {
        for (BillType orderStatus : BillType.values()) {
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
        for (BillType tabText : BillType.values()) {
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