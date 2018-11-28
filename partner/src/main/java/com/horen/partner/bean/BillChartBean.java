package com.horen.partner.bean;

import com.horen.base.util.MatcherUtils;
import com.horen.base.util.NumberUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Author:Steven
 * Time:2018/8/29 8:46
 * Description:This isBillBean
 */
public class BillChartBean {

    /**
     * months : 测试内容yph4
     * amount : 76212
     */

    private String months;
    private double amount;

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public double getAmount() {
        return Double.parseDouble(NumberUtil.formitNumberTwoPoint(amount));
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
