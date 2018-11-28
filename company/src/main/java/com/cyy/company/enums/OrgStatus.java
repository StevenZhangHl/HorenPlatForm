package com.cyy.company.enums;

/**
 * 网点类型
 * 1:大仓，2：默认网点，3：下游网点
 */

public enum OrgStatus {

    ONE("大仓", "1"),
    TWO("默认网点", "2"),
    THREE("下游网点", "3");

    private String text;//全部
    private String position;//0

    // 构造方法
    OrgStatus(String text, String position) {
        this.text = text;
        this.position = position;
    }

    /**
     * 根据类型的名称，返回类型的枚举实例。
     *
     * @param text 类型名称
     */
    public static OrgStatus fromTabText(String text) {
        for (OrgStatus tabText : OrgStatus.values()) {
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
        for (OrgStatus tabText : OrgStatus.values()) {
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
