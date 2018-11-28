package com.horen.partner.bean;

/**
 * Author:Steven
 * Time:2018/9/5 12:16
 * Description:This isBarChartListBean
 */
public class BarChartListBean {
    private int yValue;

    public int getyValue() {
        return yValue;
    }

    public void setyValue(int yValue) {
        this.yValue = yValue;
    }

    public BarChartListBean(int yValue) {
        this.yValue = yValue;
    }
}
