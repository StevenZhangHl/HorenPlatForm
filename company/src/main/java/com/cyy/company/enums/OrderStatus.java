package com.cyy.company.enums;

/**
 * 订单状态
 */
public enum OrderStatus {

    ALL("全部", "all"),
    ONE("待处理", "0"),
    TWO("待执行", "2"),
    THREE("执行中", "4"),
    FOUR("已取消", "8"),
    FIVE("完成", "32"),
    SIX("异常完成", "65"),
    SEVEN("已取消", "66"), // 已作废 当已取消处理
    EIGHT("执行中", "16");// 部分签收 当执行中处理

    private String text;//全部
    private String position;//0

    // 构造方法
    OrderStatus(String text, String position) {
        this.text = text;
        this.position = position;
    }

    /**
     * 根据类型的名称，返回类型的枚举实例。
     *
     * @param text 类型名称
     */
    public static OrderStatus fromTabText(String text) {
        for (OrderStatus tabText : OrderStatus.values()) {
            if (tabText.getText().equals(text)) {
                return tabText;
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
            if (tabText.getPosition().equals(position)) {
                return tabText.getText();
            }
        }
        return null;
    }

    /**
     * 根据类型的名称，返回类型的枚举实例。
     *
     * @param text 类型名称
     */
    public static String fromText(String text) {
        for (OrderStatus tabText : OrderStatus.values()) {
            if (tabText.getText().equals(text)) {
                return tabText.getPosition();
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "OrderStatusRent{" +
                "text='" + text + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
