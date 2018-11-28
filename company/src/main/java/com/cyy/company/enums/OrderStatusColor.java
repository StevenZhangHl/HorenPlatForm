package com.cyy.company.enums;

import android.graphics.Color;

/**
 * 订单类型对应颜色
 */

public enum OrderStatusColor {

    //待处理         	#EF8D06
    //执行中				#EFC006
    //完成				#6FBA2C
    //已取消			    #999999
    //异常完成			#6FBA2C
    //待执行	            #EF8D06

    ALL(OrderStatus.ONE.getPosition(), Color.parseColor("#EF8D06")),
    TWO(OrderStatus.TWO.getPosition(), Color.parseColor("#EF8D06")),
    EIGHT(OrderStatus.EIGHT.getPosition(), Color.parseColor("#EFC006")),
    THREE(OrderStatus.THREE.getPosition(), Color.parseColor("#EFC006")),
    FOUR(OrderStatus.FOUR.getPosition(), Color.parseColor("#999999")),
    SEVEN(OrderStatus.SEVEN.getPosition(), Color.parseColor("#999999")),
    FIVE(OrderStatus.FIVE.getPosition(), Color.parseColor("#6FBA2C")),
    SIX(OrderStatus.SIX.getPosition(), Color.parseColor("#6FBA2C"));

    private String text;//签收完成1
    private int color;//#5F8BFA

    // 构造方法
    OrderStatusColor(String text, int color) {
        this.text = text;
        this.color = color;
    }

    /**
     * 根据类型的名称，返回类型的枚举实例。
     *
     * @param text 类型名称
     */
    public static int fromColor(String text) {
        for (OrderStatusColor statusColor : OrderStatusColor.values()) {
            if (statusColor.getText().equals(text)) {
                return statusColor.getColor();
            }
        }
        return Color.parseColor("#5F8BFA");
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
