package com.horen.service.bean;

import com.horen.chart.barchart.IBarData;

/**
 * @author :ChenYangYi
 * @date :2018/08/03/13:02
 * @description :产品
 * @github :https://github.com/chenyy0708
 */
public class ProductData implements IBarData {
    private String name;
    private int valueData;

    public ProductData(int valueData, String name) {
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

    public void setValue(int value) {
        this.valueData = value;
    }
}
