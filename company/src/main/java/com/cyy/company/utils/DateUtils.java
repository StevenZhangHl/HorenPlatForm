package com.cyy.company.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author :ChenYangYi
 * @date :2018/10/16/11:12
 * @description :
 * @github :https://github.com/chenyy0708
 */
public class DateUtils {
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
     * 时间格式化 年月日 -- 月日
     */
    public static String formatMonthDay(String time) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("MM月dd日");
        String format = null;
        try {
            format = format2.format(format1.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format;
    }

    /**
     * 时间格式化 年月日 -- 月日
     */
    public static String formatNewMonthDay(String time) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("MM-dd");
        String format = null;
        try {
            format = format2.format(format1.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format;
    }

    /**
     * 时间格式化 年月日 -- 时分
     */
    public static String formatHourMinute(String time) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
        String format = null;
        try {
            format = format2.format(format1.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format;
    }

    /**
     * 时间格式化 年月日 -- 月日
     */
    public static String formatYearMonthDay(String time) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        String format = null;
        try {
            format = format2.format(format1.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format;
    }

    /**
     * 时间格式化 年月日 -- 月日
     */
    public static String getCurentData() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String format = null;
        try {
            format = format1.format(new Date(System.currentTimeMillis()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format;
    }
}
