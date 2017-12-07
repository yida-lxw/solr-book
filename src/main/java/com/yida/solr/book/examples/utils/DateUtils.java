package com.yida.solr.book.examples.utils;

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 日期时间工具类
 * @author Lanxiaowei
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class DateUtils {
    /*************************************一分钟、一小时、一天、一个月、一年的固定秒数**************************************/
    private static final long ONE_MINUTE = 60;
    private static final long ONE_HOUR = 3600;
    private static final long ONE_DAY = 86400;
    private static final long ONE_MONTH = 2592000;
    private static final long ONE_YEAR = 31104000;

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static ThreadLocal threadLocal = new ThreadLocal() {
        protected synchronized Object initialValue() {
            return null;
        }
    };

    /**
     *
     * 方法摘要：判断是否闰年
     *
     * @param year
     *            年份
     * @return boolean
     */
    public static boolean isLeapYear(int year) {
        return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
    }

    /**
     * 方法摘要：获取日期格式化对象
     * @param pattern  日期格式
     * @return
     */
    public static SimpleDateFormat getDateFormat(String pattern) {
        if(null == pattern || pattern.equals("")) {
            pattern = DATE_FORMAT;
        }
        SimpleDateFormat df = (SimpleDateFormat) threadLocal.get();
        if (null == df) {
            df = new SimpleDateFormat(pattern, Locale.US);
            threadLocal.set(df);
        }
        df.applyPattern(pattern);
        df.setLenient(false); // 严格转换模式，2012-02-31格式会转换异常
        return df;
    }

    /**
     * 方法摘要：获取日期格式化对象
     * @param pattern  日期格式
     * @return
     */
    public static SimpleDateFormat getDateFormat(String pattern,Locale local) {
        if(null == pattern || pattern.equals("")) {
            pattern = DATE_FORMAT;
        }
        SimpleDateFormat df = (SimpleDateFormat) threadLocal.get();
        if (null == df) {
            if(null == local) {
                df = new SimpleDateFormat(pattern,Locale.US);
            } else {
                df = new SimpleDateFormat(pattern,local);
            }
            threadLocal.set(df);
        }
        df.applyPattern(pattern);
        df.setLenient(false); // 严格转换模式，2012-02-31格式会转换异常
        return df;
    }

    /**
     *
     * 方法摘要：获取日期格式化对象
     *
     * @param
     * @return DateFormat
     */
    public static SimpleDateFormat getDateFormat() {
        return getDateFormat(null);
    }

    /**
     *
     * 方法摘要：日期格式化(yyyy-MM-dd HH:mm:ss)
     *
     * @param date
     *            待格式化的日期
     * @return String
     */
    public static String parseDate(Date date) {
        if (null == date) {
            return "";
        }
        return getDateFormat().format(date);
    }

    /**
     *
     * 方法摘要：按指定的格式格式化日期
     *
     * @param date
     *            待格式化的日期
     * @param pattern
     *            指定的格式
     * @return String
     */
    public static String parseDate(Date date, String pattern) {
        SimpleDateFormat sdf = getDateFormat();
        sdf.applyPattern(pattern);
        return sdf.format(date);
    }

    /**
     *
     * 方法摘要：字符串转换成util.Date对象(重载)
     * 默认格式为yyyy-MM-dd HH:mm:ss
     * @param dateString
     *            待转换的yyyy-MM-dd HH:mm:ss格式日期字符串
     * @return Date
     */
    public static Date stringToDate(String dateString) throws ParseException {
        return stringToDate(dateString,null);
    }

    /**
     * 方法摘要：字符串转换成util.Date对象
     * @param dateString  日期形式字符串
     * @param pattern     转换格式
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String dateString,String pattern) throws ParseException {
        return getDateFormat(pattern).parse(dateString);
    }

    /**
     * 方法摘要：字符串转换成util.Date对象
     * @param dateString  日期形式字符串
     * @param pattern     转换格式
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String dateString,String pattern,Locale local) throws ParseException {
        return getDateFormat(pattern,local).parse(dateString);
    }

    /**
     *
     *方法摘要：返回指定日期N年后的日期(N年前请将years赋值为负数)
     *@param  date   指定日期
     *@param  years 指定年数
     *@return Date
     */
    public static Date addYears(Date date, int years){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years);
        return cal.getTime();
    }

    /**
     *
     *方法摘要：返回指定日期N月后的日期(N月前请将months赋值为负数)
     *@param  date   指定日期
     *@param  months 指定月数
     *@return Date
     */
    public static Date addMonths(Date date,int months){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     *
     * 方法摘要：返回指定日期N天后的日期(N天前请将days赋值为负数)
     *
     * @param date
     *            指定日期
     * @param days
     *            指定天数
     * @return Date
     */
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    /**
     *
     *方法摘要：返回指定日期N小时后的日期(N小时前请将hours赋值为负数)
     *@param  date   指定日期
     *@param  hours  指定小时数
     *@return Date
     */
    public static Date addHours(Date date, int hours){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal.getTime();
    }

    /**
     *
     *方法摘要：返回指定日期N分钟后的日期(N分钟前请将minutes赋值为负数)
     *@param  date     指定日期
     *@param  minutes  指定分钟数
     *@return Date
     */
    public static Date addMinutes(Date date, int minutes){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    /**
     *
     *方法摘要：返回指定日期N秒钟后的日期(N秒钟前请将seconds赋值为负数)
     *@param  date     指定日期
     *@param  seconds  指定秒数
     *@return Date
     */
    public static Date addSeconds(Date date, int seconds){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, seconds);
        return cal.getTime();
    }

    /**
     *
     * 方法摘要：计算两个日期之间相差的天数
     *
     * @param date1
     * @param date2
     * @return int
     */
    public static int dayDiff(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     *
     *方法摘要：返回某年某月的最大天数，如2012年2月实际29天
     *@param  year   年份
     *@param  month  月份
     *@return int
     */
    public static int getMaxDayOfMonth(int year,int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     *
     *方法摘要：返回指定日期的年份
     *@param  date  指定日期
     *@return int
     */
    public static int getYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     *
     *方法摘要：返回指定日期的月份
     *@param  date  指定日期
     *@return int
     */
    public static int getMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    /**
     *
     *方法摘要：返回指定日期当前月的第几天
     *@param  date  指定日期
     *@return int
     */
    public static int getDayOfMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     *
     *方法摘要：返回指定日期星期几
     *@param  date  指定日期
     *@return int  返回星期几，1表示星期日  7表示星期六
     */
    public static int getDayOfWeek(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     *
     *方法摘要：返回指定日期的小时数
     *@param  date  指定日期
     *@return int  返回小时数 0-23
     */
    public static int getHourOfDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     *
     *方法摘要：返回指定日期的分钟数
     *@param  date  指定日期
     *@return int  返回分钟数 0-59
     */
    public static int getMinute(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     *
     *方法摘要：返回指定日期的秒数
     *@param  date  指定日期
     *@return int  返回秒数 0-59
     */
    public static int getSecond(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     *
     *方法摘要：返回指定日期一年中第几天
     *@param  date  指定日期
     *@return int
     */
    public static int getDayOfYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    /**
     *
     *方法摘要：将两者合并成一个Date对象，即Date日期部分+time时间部分
     *@param
     *@return void
     */
    public static Date mergeDateTime(Date date,Date time){
        if(null == date || null == time){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String timeString = parseDate(time, "HH:mm:ss");
        String[] nums = timeString.split(":");
        calendar.set(Calendar.HOUR_OF_DAY, new Integer(nums[0]).intValue());
        calendar.set(Calendar.MINUTE, new Integer(nums[1]).intValue());
        calendar.set(Calendar.SECOND, new Integer(nums[2]).intValue());
        return calendar.getTime();
    }

    /**
     *
     *方法摘要：根据传入的星期数字标识数组拼接星期字符串
     *@param
     *@return String
     */
    public static String getWeekString(Integer[] weeks){
        StringBuffer weekString = new StringBuffer("");
        if(null != weeks && weeks.length != 0){
            String weekTemp = "";
            for(int i = 0;i < weeks.length; i++){
                weekTemp = getWeekString(weeks[i]);
                if(i == weeks.length - 1){
                    weekString.append(weekTemp);
                } else{
                    weekString.append(weekTemp).append("、");
                }
            }
        }
        return weekString.toString();
    }

    /**
     *
     *方法摘要：将星期的数字标识转换成中文字符
     *@param  week   星期标识 1-7
     *@return String
     */
    public static String getWeekString(int week){
        String weekChinese = "";
        switch (week) {
            case Week.WEEK_SUNDAY:      //星期日
                weekChinese = Week.SUNDAY.getChinese();
                break;
            case Week.WEEK_MONDAY:     //星期一
                weekChinese = Week.MONDAY.getChinese();
                break;
            case Week.WEEK_TUESDAY:     //星期二
                weekChinese = Week.TUESDAY.getChinese();
                break;
            case Week.WEEK_WEDNESDAY:     //星期三
                weekChinese = Week.WEDNESDAY.getChinese();
                break;
            case Week.WEEK_THURSDAY:     //星期四
                weekChinese = Week.THURSDAY.getChinese();
                break;
            case Week.WEEK_FRIDAY:     //星期五
                weekChinese = Week.FRIDAY.getChinese();
                break;
            case Week.WEEK_SATURDAY:     //星期六
                weekChinese = Week.SATURDAY.getChinese();
                break;
            default:
                throw new RuntimeException("Not exsists that week.");
        }
        return weekChinese;
    }

    /**
     *
     *方法摘要：根据传入的月份数字标识数组拼接月份字符串
     *@param  months
     *@return String
     */
    public static String getMonthString(Integer[] months){
        StringBuffer monthString = new StringBuffer("");
        if(null != months && months.length != 0){
            String monthTemp = "";
            for(int i = 0;i < months.length; i++){
                monthTemp = getMonthString(months[i]);
                if(i == months.length - 1){
                    monthString.append(monthTemp);
                } else{
                    monthString.append(monthTemp).append("、");
                }
            }
        }
        return monthString.toString();
    }

    /**
     *
     *方法摘要：将月份的数字标识转换成中文字符
     *@param  month   月份标识 1-12
     *@return String
     */
    public static String getMonthString(int month){
        String monthChinese = "";
        switch (month-1) {
            case Month.MONTH_JANUARY:      //一月
                monthChinese = Month.JANUARY.getChinese();
                break;
            case Month.MONTH_FEBRUARY:     //二月
                monthChinese = Month.FEBRUARY.getChinese();
                break;
            case Month.MONTH_MARCH:     //三月
                monthChinese = Month.MARCH.getChinese();
                break;
            case Month.MONTH_APRIL:     //四月
                monthChinese = Month.APRIL.getChinese();
                break;
            case Month.MONTH_MAY:     //五月
                monthChinese = Month.MAY.getChinese();
                break;
            case Month.MONTH_JUNE:     //六月
                monthChinese = Month.JUNE.getChinese();
                break;
            case Month.MONTH_JULY:     //七月
                monthChinese = Month.JULY.getChinese();
                break;
            case Month.MONTH_AUGUST:     //八月
                monthChinese = Month.AUGUST.getChinese();
                break;
            case Month.MONTH_SEPTEMBER:     //九月
                monthChinese = Month.SEPTEMBER.getChinese();
                break;
            case Month.MONTH_OCTOBER:     //十月
                monthChinese = Month.OCTOBER.getChinese();
                break;
            case Month.MONTH_NOVEMBER:     //十一月
                monthChinese = Month.NOVEMBER.getChinese();
                break;
            case Month.MONTH_DECEMBER:     //十二月
                monthChinese = Month.DECEMBER.getChinese();
                break;
            default:
                throw new RuntimeException("Not exsists that month.");
        }
        return monthChinese;
    }

    /**
     * 将日期转换成xx秒前/xx分钟前/xx小时前等等
     * @param date
     * @return
     */
    public static String howLongFromToday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long time = date.getTime() / 1000;
        long now = new Date().getTime() / 1000;
        long ago = now - time;
        if(ago < ONE_MINUTE){
            return ago + "秒前";
        } else if (ago <= ONE_HOUR){
            return ago / ONE_MINUTE + "分钟前";
        } else if (ago <= ONE_DAY){
            return ago / ONE_HOUR + "小时" + (ago % ONE_HOUR / ONE_MINUTE)
                    + "分钟前";
        } else if (ago <= ONE_DAY * 2){
            return "昨天" + calendar.get(Calendar.HOUR_OF_DAY) + "点"
                    + calendar.get(Calendar.MINUTE) + "分";
        } else if (ago <= ONE_DAY * 3){
            return "前天" + calendar.get(Calendar.HOUR_OF_DAY) + "点"
                    + calendar.get(Calendar.MINUTE) + "分";
        } else if (ago <= ONE_MONTH) {
            long day = ago / ONE_DAY;
            return day + "天前" + calendar.get(Calendar.HOUR_OF_DAY) + "点"
                    + calendar.get(Calendar.MINUTE) + "分";
        } else if (ago <= ONE_YEAR) {
            long month = ago / ONE_MONTH;
            long day = ago % ONE_MONTH / ONE_DAY;
            return month + "个月" + day + "天前"
                    + calendar.get(Calendar.HOUR_OF_DAY) + "点"
                    + calendar.get(Calendar.MINUTE) + "分";
        } else {
            long year = ago / ONE_YEAR;
            int month = calendar.get(Calendar.MONTH) + 1;// JANUARY which is 0 so month+1
            return year + "年前" + month + "月" + calendar.get(Calendar.DATE)
                    + "日";
        }
    }

    /**
     * 距离截止日期还有多长时间
     *
     * @param date
     * @return
     */
    public static String fromDeadline(Date date) {
        long deadline = date.getTime() / 1000;
        long now = (new Date().getTime()) / 1000;
        long remain = deadline - now;
        if (remain <= ONE_HOUR) {
            return "只剩下" + remain / ONE_MINUTE + "分钟";
        } else if (remain <= ONE_DAY) {
            return "只剩下" + remain / ONE_HOUR + "小时"
                    + (remain % ONE_HOUR / ONE_MINUTE) + "分钟";
        } else {
            long day = remain / ONE_DAY;
            long hour = remain % ONE_DAY / ONE_HOUR;
            long minute = remain % ONE_DAY % ONE_HOUR / ONE_MINUTE;
            return "只剩下" + day + "天" + hour + "小时" + minute + "分钟";
        }
    }

    /**
     * 日期字符串转换成util.Date对象
     *
     * @param dateString
     * @param origFormate
     * @return
     */
    public static Date parseDate(String dateString, String origFormate) {
        dateString = dateString.replace(", ", ",");
        SimpleDateFormat sdf = new SimpleDateFormat(origFormate, Locale.US);
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取JDK支持的所有时区
     *
     * @return
     */
    public static String[] fecthAllTimeZones() {
        List<String> v = new ArrayList<String>();
        String[] ids = TimeZone.getAvailableIDs();
        for (int i = 0; i < ids.length; i++) {
            v.add(ids[i]);
            System.out.println(ids[i]);
        }
        java.util.Collections.sort(v, String.CASE_INSENSITIVE_ORDER);
        return v.toArray(new String[]{});
    }

    /**
     * 获取系统当前默认时区与UTC的时间差.(单位:毫秒)
     *
     * @return 系统当前默认时区与UTC的时间差.(单位:毫秒)
     */
    private static int getDefaultTimeZoneRawOffset() {
        return TimeZone.getDefault().getRawOffset();
    }

    /**
     * 获取指定时区与UTC的时间差.(单位:毫秒)
     *
     * @param timeZone 时区Id
     * @return 指定时区与UTC的时间差.(单位:毫秒)
     */
    private static int getTimeZoneRawOffset(String timeZone) {
        return TimeZone.getTimeZone(timeZone).getRawOffset();
    }

    /**
     * 获取系统当前默认时区与指定时区的时间差.(单位:毫秒)
     *
     * @param timeZone 时区Id
     * @return 系统当前默认时区与指定时区的时间差.(单位:毫秒)
     */
    private static int getDiffTimeZoneRawOffset(String timeZone) {
        return TimeZone.getDefault().getRawOffset()
                - TimeZone.getTimeZone(timeZone).getRawOffset();
    }

    /**
     * 获取两个时区之间相差的毫秒数
     * @param timeZone1
     * @param timeZone2
     * @return
     */
    private static int getDiffTimeZoneRawOffset(String timeZone1,String timeZone2) {
        return TimeZone.getTimeZone(timeZone1).getRawOffset()
                - TimeZone.getTimeZone(timeZone2).getRawOffset();
    }

    /**
     * 日期时间字符串转换成指定时区对应格式的字符串
     *
     * @param srcDateTime
     * @param dstTimeZoneId
     * @return
     */
    public static String string2TimezoneDefault(String srcDateTime,
                                                String dstTimeZoneId) {
        return string2Timezone("yyyy-MM-dd HH:mm:ss", srcDateTime,
                "yyyy-MM-dd HH:mm:ss", dstTimeZoneId);
    }

    /**
     * 将指定的Date对象转换成目标时区的Date对象
     * @param srcDate
     * @param srcTimeZone
     * @param dstTimeZone
     * @return
     */
    public static Date date2Timezone(Date srcDate,String srcTimeZone, String dstTimeZone) {
        int diffTime = getDiffTimeZoneRawOffset(srcTimeZone,dstTimeZone);
        long nowTime = srcDate.getTime();
        long newNowTime = nowTime - diffTime;
        return new Date(newNowTime);
    }

    /**
     * 将日期时间字符串根据转换为指定时区的日期时间字符串
     *
     * @param srcFormater   待转化的日期时间的格式.
     * @param srcDateTime   待转化的日期时间.
     * @param dstFormater   目标的日期时间的格式.
     * @param dstTimeZoneId 目标的时区编号.
     * @return 转化后的日期时间.
     */
    public static String string2Timezone(String srcFormater,
                                         String srcDateTime, String dstFormater, String dstTimeZoneId) {
        if (srcFormater == null || "".equals(srcFormater)) {
            return null;
        }
        if (srcDateTime == null || "".equals(srcDateTime)) {
            return null;
        }
        if (dstFormater == null || "".equals(dstFormater)) {
            return null;
        }
        if (dstTimeZoneId == null || "".equals(dstTimeZoneId)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(srcFormater);
        try {
            int diffTime = getDiffTimeZoneRawOffset(dstTimeZoneId);
            Date d = sdf.parse(srcDateTime);
            long nowTime = d.getTime();
            long newNowTime = nowTime - diffTime;
            d = new Date(newNowTime);
            return parseDate(d,dstFormater);
        } catch (ParseException e) {
            return null;
        } finally {
            sdf = null;
        }
    }

    /**
     * 日期字符串转换成UTC时区格式的字符串
     * @param srcFormater
     * @param srcDateTime
     * @return
     */
    public static String string2UTC(String srcFormater,
                                    String srcDateTime) {
        return string2Timezone(srcFormater,srcDateTime,"yyyy-MM-dd'T'HH:mm:ss'Z'","UTC");
    }

    public static void main(String[] args) {
        System.out.println(string2UTC("yyyy-MM-dd HH:mm:ss",
                "2016-11-23 21:48:09"));
        System.out.println(parseDate(date2Timezone(new Date(),"Asia/Shanghai","GMT+8")));
    }
}

