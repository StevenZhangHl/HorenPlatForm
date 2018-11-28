package com.horen.partner.bean;

import com.horen.chart.barchart.IBarData;

/**
 * Author:Steven
 * Time:2018/8/7 8:38
 * Description:This isPotentialCustomerBean
 */
public class PotentialCustomerBean implements IBarData {
    private float value;
    private String xname;

    public void setValue(float value) {
        this.value = value;
    }

    public String getXname() {
        return xname;
    }

    public void setXname(String xname) {
        this.xname = xname;
    }

    public PotentialCustomerBean(float value, String xname) {
        this.value = value;
        this.xname = xname;
    }

    @Override
    public float getValue() {
        return value;
    }

    @Override
    public String getLabelName() {
        return xname;
    }
}
