package com.aivox.base.util.crash;

import com.aivox.base.util.BaseAppUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public final class DateUtil {
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyyMMddHHmmss";
    public static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd";
    public static final String DEFAULT_FORMAT_TIME = "HH:mm:ss";
    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final ThreadLocal<SimpleDateFormat> defaultDateTimeFormat = new ThreadLocal<SimpleDateFormat>() { // from class: com.aivox.base.util.crash.DateUtil.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss");
        }
    };
    public static final ThreadLocal<SimpleDateFormat> defaultDateFormat = new ThreadLocal<SimpleDateFormat>() { // from class: com.aivox.base.util.crash.DateUtil.2
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    public static final ThreadLocal<SimpleDateFormat> defaultTimeFormat = new ThreadLocal<SimpleDateFormat>() { // from class: com.aivox.base.util.crash.DateUtil.3
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm:ss");
        }
    };

    private DateUtil() {
        throw new RuntimeException("￣ 3￣");
    }

    public static String getDateTimeFromMillis(long j) {
        return getDateTimeFormat(new Date(j));
    }

    public static String getDateFromMillis(long j) {
        return getDateFormat(new Date(j));
    }

    public static String getDateTimeFormat(Date date) {
        return dateSimpleFormat(date, defaultDateTimeFormat.get());
    }

    public static String getDateFormat(int i, int i2, int i3) {
        return getDateFormat(getDate(i, i2, i3));
    }

    public static String getDateFormat(Date date) {
        return dateSimpleFormat(date, defaultDateFormat.get());
    }

    public static String getTimeFormat(Date date) {
        return dateSimpleFormat(date, defaultTimeFormat.get());
    }

    public static String dateFormat(String str, String str2) {
        return dateSimpleFormat(java.sql.Date.valueOf(str), new SimpleDateFormat(str2));
    }

    public static String dateFormat(Date date, String str) {
        return dateSimpleFormat(date, new SimpleDateFormat(str));
    }

    public static String dateSimpleFormat(Date date, SimpleDateFormat simpleDateFormat) {
        if (simpleDateFormat == null) {
            simpleDateFormat = defaultDateTimeFormat.get();
        }
        return date == null ? "" : simpleDateFormat.format(date);
    }

    public static Date getDateByDateTimeFormat(String str) {
        return getDateByFormat(str, defaultDateTimeFormat.get());
    }

    public static Date getDateByDateFormat(String str) {
        return getDateByFormat(str, defaultDateFormat.get());
    }

    public static Date getDateByFormat(String str, String str2) {
        return getDateByFormat(str, new SimpleDateFormat(str2));
    }

    private static Date getDateByFormat(String str, SimpleDateFormat simpleDateFormat) {
        if (simpleDateFormat == null) {
            simpleDateFormat = defaultDateTimeFormat.get();
        }
        try {
            return simpleDateFormat.parse(str);
        } catch (ParseException e) {
            BaseAppUtils.printErrorMsg(e);
            return null;
        }
    }

    public static Date getDate(int i, int i2, int i3) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(i, i2 - 1, i3);
        return calendar.getTime();
    }

    public static long getIntervalDays(String str, String str2) {
        return (java.sql.Date.valueOf(str2).getTime() - java.sql.Date.valueOf(str).getTime()) / 86400000;
    }

    public static int getCurrentYear() {
        return Calendar.getInstance().get(1);
    }

    public static int getCurrentMonth() {
        return Calendar.getInstance().get(2) + 1;
    }

    public static int getDayOfMonth() {
        return Calendar.getInstance().get(5);
    }

    public static String getToday() {
        return getDateFormat(Calendar.getInstance().getTime());
    }

    public static String getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(5, -1);
        return getDateFormat(calendar.getTime());
    }

    public static String getBeforeYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(5, -2);
        return getDateFormat(calendar.getTime());
    }

    public static String getOtherDay(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(5, i);
        return getDateFormat(calendar.getTime());
    }

    public static String getCalcDateFormat(String str, int i) {
        return getDateFormat(getCalcDate(getDateByDateFormat(str), i));
    }

    public static Date getCalcDate(Date date, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, i);
        return calendar.getTime();
    }

    public static Date getCalcTime(Date date, int i, int i2, int i3) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(11, i);
        calendar.add(12, i2);
        calendar.add(13, i3);
        return calendar.getTime();
    }

    public static Date getDate(int i, int i2, int i3, int i4, int i5, int i6) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(i, i2, i3, i4, i5, i6);
        return calendar.getTime();
    }

    public static int[] getYearMonthAndDayFrom(String str) {
        return getYearMonthAndDayFromDate(getDateByDateFormat(str));
    }

    public static int[] getYearMonthAndDayFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return new int[]{calendar.get(1), calendar.get(2), calendar.get(5)};
    }
}
