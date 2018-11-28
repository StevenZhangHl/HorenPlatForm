package com.horen.partner.util;

/**
 * Author:Steven
 * Time:2018/9/4 12:05
 * Description:This isCalculateUtil
 */
public class CalculateUtil {
    public static int getBillChartMaxY(int maxValue){
        if (maxValue == 0) {
            maxValue = 6000;
        }
        if (maxValue <= 6000) {
            maxValue = 6000;
        }
        if (maxValue > 6000 && maxValue <= 12000) {
            maxValue = 12000;
        }
        if (maxValue > 12000 && maxValue <= 30000) {
            maxValue = 30000;
        }
        return maxValue;
    }
}
