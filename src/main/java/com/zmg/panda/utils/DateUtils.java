package com.zmg.panda.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Panda
 */
public class DateUtils {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getNow() {
        Calendar calendar = Calendar.getInstance();
        return SIMPLE_DATE_FORMAT.format(calendar.getTime());
    }

    /**
     * 获取最近一个月的起始时间
     *
     * @return
     */
    public static String getLastMonthTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        return SIMPLE_DATE_FORMAT.format(calendar.getTime());
    }

    /**
     * 获取最近一年的起始时间
     *
     * @return
     */
    public static String getLastYearTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        return SIMPLE_DATE_FORMAT.format(calendar.getTime());
    }

    /**
     * 获取上一个月的最后一天
     *
     * @return
     */
    public static String getLastMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return SIMPLE_DATE_FORMAT.format(calendar.getTime());
    }
}
