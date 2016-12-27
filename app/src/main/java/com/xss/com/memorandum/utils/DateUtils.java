package com.xss.com.memorandum.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class DateUtils {

    /**
     * 获取当前时间戳
     * @return
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * 秒转，天  时 分
     *
     * @param second
     * @return
     */
    public static String cal2(long second) {
        int h = 0;//时
        int m = 0;//分
        StringBuffer sb = new StringBuffer();
        int day = 3600 * 24;//60*60
        int temp = (int) second % day;
        if (second > day)//大于一天
        {
            sb.append(second / day);
            sb.append("天");
        }
        if (temp != 0) {
            if (temp > 3600) {
                h = temp / 3600;//小时
                sb.append(h);
                sb.append("时");
                if (temp != h * 3600) {
                    m = (temp - 3600 * h) / 60;//分
                }
            } else if (temp > 60) {
                m = temp / 60;//分
            }
            sb.append(m);
            sb.append("分");
        }
        return sb.toString();
    }

    /**
     * 获取 年
     *
     * @param time 时间戳
     * @return
     */
    public static int getYear(long time) {
        return Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date(time)));
    }

    /**
     * 获取 月份
     *
     * @param time 时间戳
     * @return
     */
    public static int getMonth(long time) {
        return Integer.parseInt(new SimpleDateFormat("MM").format(new Date(time)));
    }

    /**
     * 获取 YYYY年  格式的日期
     *
     * @param time 时间戳
     * @return
     */
    public static int getDay(long time) {
        return Integer.parseInt(new SimpleDateFormat("dd").format(new Date(time)));
    }


    /**
     * 获取 YYYY年MM月  格式的日期
     *
     * @param time 时间戳
     * @return
     */
    public static String getDateAll(String time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(time)));
    }

    /**
     * 获取 YYYY年MM月  格式的日期
     *
     * @param time 时间戳
     * @return
     */
    public static String getYearMonth1(long time) {
        return new SimpleDateFormat("yyyyMM").format(new Date(time));
    }

    /**
     * 获取 YYYY年MM月  格式的日期
     *
     * @param time 时间戳
     * @return
     */
    public static String getYearMonth(String time) {
        return new SimpleDateFormat("yyyy年MM月").format(new Date(Long.parseLong(time)));
    }

    /**
     * 获取 YYYY年MM月dd日  格式的日期
     *
     * @param time 时间戳
     * @return
     */
    public static String getYearMonthDay(String time) {
        return new SimpleDateFormat("yyyy年MM月dd日").format(new Date(Long.parseLong(time)));
    }

    /**
     * 获取 YYYY年MM月  格式的日期
     *
     * @param time 时间戳
     * @return
     */
    public static String getYearMonth2(long time) {
        return new SimpleDateFormat("yyyy年MM月").format(new Date(time));
    }


    /**
     * 获取  MM-dd  格式的日期
     *
     * @param time 时间戳
     * @return
     */
    public static String getMonthDay(long time) {
        return new SimpleDateFormat("MM-dd").format(new Date(time));
    }

    /**
     * 获取  HH:mm  格式的日期
     *
     * @param time 时间戳
     * @return
     */
    public static String getHourMinute(long time) {
        return new SimpleDateFormat("HH:mm").format(new Date(time));
    }

    /**
     * @param second
     * @return
     */
    public static String getYearMdhm(long second) {
        Date date = new Date(second);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        return sdf.format(date);
    }

    public static String date2String(String second) {
        Long time = Long.parseLong(second);
        Date date = new Date(time);
//		System.out.println(TimeZone.getDefault().getDisplayName());
        //System.out.println("Date:" + date);
        //return date.toLocaleString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * @param second
     * @return
     */
    public static String getYearMdh(long second) {
        Date date = new Date(second);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        return sdf.format(date);
    }

    /**
     * @param second
     * @return
     */
    public static String getYearMdh1(long second) {
        Date date = new Date(second);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String getMonthEnglish(int month) {
        switch (month) {
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
        }
        return "";
    }

    public static int getMonth(String date) {
        String[] arr = date.split("-");
        int month = 0;
        if (arr[1].startsWith("0")) {
            month = Integer.parseInt(arr[1].substring(1, arr[1].length()));
        } else {
            month = Integer.parseInt(arr[1]);
        }
        return month;
    }

    public static String getDay(String date) {
        return date.split("-")[2];
    }

    public static String getYear(String date) {
        return date.split("-")[0];
    }
}
