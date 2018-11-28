package com.horen.service.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author :ChenYangYi
 * @date :2018/08/17/13:37
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class StringUtils {
    /**
     * 切割字符串前4位，后续...拼接
     *
     * @param str 字符串
     */
    public static String substring(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        if (str.length() > 4) {
            builder.append(str.substring(0, 4));
            builder.append("...");
        } else {
            builder.append(str);
        }
        return builder.toString();
    }

    /**
     * 金额格式化
     *
     * @param devimal 金额
     * @return 格式化账号
     */
    public static String devimalFormat(float devimal) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
        return decimalFormat.format(devimal);
    }

    /**
     * 时间格式化 年月 -- 月
     */
    public static String formatTime(String time) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat format2 = new SimpleDateFormat("M");
        String format = null;
        try {
            format = format2.format(format1.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format;
    }

    /**
     * 时间格式化 年月日 -- 月
     */
    public static String getDateMonthYear(Date time) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String format = null;
        try {
            format = format1.format(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format;
    }

    /**
     * 获取一个月前的日期
     */
    public static String getMonthAgo() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        String monthAgo = simpleDateFormat.format(calendar.getTime());
        return monthAgo;
    }


    /**
     * 账单中心
     */
    public static String checkBillType(String type) {
        String lableName = "";
        switch (type) {
            case "2": // 产品出库费
                lableName = "产品出库费";
                break;
            case "3": // 耗材出库费
                lableName = "耗材出库费";
                break;
            case "4": // 维修费用
                lableName = "维修费用";
                break;
            case "5": // 运输费用
                lableName = "运输费用";
                break;
            default:
                break;
        }
        return lableName;
    }
}
