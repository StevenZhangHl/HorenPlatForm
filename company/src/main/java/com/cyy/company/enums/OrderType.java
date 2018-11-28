package com.cyy.company.enums;

/**
 * 订单类型
 */

public enum OrderType {

    //	订单类型：11:租赁(租箱) 12：收箱(还箱)

    ONE("租箱", "11"),
    TWO("还箱", "12"),
    THREE("续租", "13");

    private String text;//全部
    private String position;//0

    // 构造方法
    OrderType(String text, String position) {
        this.text = text;
        this.position = position;
    }

    /**
     * 根据类型的名称，返回类型的枚举实例。
     *
     * @param text 类型名称
     */
    public static OrderType fromTabText(String text) {
        for (OrderType tabText : OrderType.values()) {
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
        for (OrderType tabText : OrderType.values()) {
            if (tabText.getPosition().equals(position)) {
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
