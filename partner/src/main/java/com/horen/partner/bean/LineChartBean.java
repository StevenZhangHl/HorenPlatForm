package com.horen.partner.bean;

import com.horen.chart.linechart.ILineChartData;

/**
 * Author:Steven
 * Time:2018/8/20 13:43
 * Description:This isLineChartBean
 */
public class LineChartBean implements ILineChartData{
    private String name;
    private double valueData;

    public LineChartBean(double valueData, String name) {
        this.name = name;
        this.valueData = valueData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * 值
     */
    @Override
    public float getValue() {
        return (float) valueData;
    }

    /**
     * 对应名字
     */
    @Override
    public String getLabelName() {
        return name;
    }

    public void setValue(double value) {
        this.valueData = value;
    }
}
