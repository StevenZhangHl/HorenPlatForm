package com.cyy.company.enums;

/**
 * 网点地址
 * 1：上游，2：下游
 */

public enum OrgType {

    ONE("上游", "1"),
    TWO("下游", "2");

    private String text;//全部
    private String position;//0

    // 构造方法
    OrgType(String text, String position) {
        this.text = text;
        this.position = position;
    }

    /**
     * 根据类型的名称，返回类型的枚举实例。
     *
     * @param text 类型名称
     */
    public static OrgType fromTabText(String text) {
        for (OrgType tabText : OrgType.values()) {
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
        for (OrgType tabText : OrgType.values()) {
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
