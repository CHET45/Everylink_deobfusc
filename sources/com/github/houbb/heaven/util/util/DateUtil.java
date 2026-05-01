package com.github.houbb.heaven.util.util;

import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes3.dex */
public final class DateUtil {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DATE_TIME_SEC_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_ZH_FORMAT = "yyyy年MM月dd日";
    public static final String PURE_DATE_FORMAT = "yyyyMMdd";
    public static final String PURE_TIME_FORMAT = "HHmmss";
    public static final String TIMESTAMP_FORMAT_14 = "yyyyMMddHHmmss";
    public static final String TIMESTAMP_FORMAT_15 = "yyMMddHHmmssSSS";
    public static final String TIMESTAMP_FORMAT_17 = "yyyyMMddHHmmssSSS";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String TIME_ZH_FORMAT = "HH时mm分ss秒";

    private DateUtil() {
    }

    public static String getDateFormat(Date date, String str) {
        if (ObjectUtil.isNull(date)) {
            return null;
        }
        return new SimpleDateFormat(str).format(date);
    }

    public static String formatDate(Date date, String str) {
        return getDateFormat(date, str);
    }

    public static Date parseDate(String str, String str2) {
        return getFormatDate(str, str2);
    }

    public static String getDateFormat17(Date date) {
        return getDateFormat(date, TIMESTAMP_FORMAT_17);
    }

    public static String getDateFormat14(Date date) {
        return getDateFormat(date, "yyyyMMddHHmmss");
    }

    public static Date getFormatDate(String str, String str2) {
        if (StringUtil.isEmptyTrim(str)) {
            return null;
        }
        try {
            return new SimpleDateFormat(str2).parse(str);
        } catch (ParseException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static Date getFormatDate17(String str) {
        return getFormatDate(str, TIMESTAMP_FORMAT_17);
    }

    public static Date getFormatDate14(String str) {
        return getFormatDate(str, "yyyyMMddHHmmss");
    }

    public static String getCurrentDateStr() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String getCurrentDatePureStr() {
        return new SimpleDateFormat(PURE_DATE_FORMAT).format(new Date());
    }

    public static String getYesterdayPureStr() {
        return new SimpleDateFormat(PURE_DATE_FORMAT).format(addDay(new Date(), -1));
    }

    public static String getCurrentTimeStampStr() {
        return new SimpleDateFormat(TIMESTAMP_FORMAT_17).format(new Date());
    }

    public static String getCurrentTime17() {
        return new SimpleDateFormat(TIMESTAMP_FORMAT_17).format(new Date());
    }

    public static String getCurrentTime14() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public static String getCurrentTimeStampStr15() {
        return new SimpleDateFormat(TIMESTAMP_FORMAT_15).format(new Date());
    }

    public static String getCurrentTimeMills() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String getCurrentDateTimeStr() {
        return new SimpleDateFormat(DATE_TIME_FORMAT).format(new Date());
    }

    public static long convertMsToNs(long j) {
        TimeUnit timeUnit = TimeUnit.NANOSECONDS;
        if (j <= 0) {
            j = 0;
        }
        return timeUnit.convert(j, TimeUnit.MILLISECONDS);
    }

    public static Date now() {
        return new Date();
    }

    public static long costTimeInMills(Date date, Date date2) {
        return date2.getTime() - date.getTime();
    }

    public static void sleep(long j) {
        sleep(TimeUnit.MILLISECONDS, j);
    }

    public static void sleep(TimeUnit timeUnit, long j) {
        if (j <= 0) {
            return;
        }
        try {
            timeUnit.sleep(j);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CommonRuntimeException(e);
        }
    }

    public static Date fromSql(java.sql.Date date) {
        if (date == null) {
            return null;
        }
        return new Date(date.getTime());
    }

    public static java.sql.Date toSqlDate(Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }

    public static Time toSqlTime(Date date) {
        if (date == null) {
            return null;
        }
        return new Time(date.getTime());
    }

    public static Timestamp toSqlTimestamp(Date date) {
        if (date == null) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    public static Date addYear(Date date, int i) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        gregorianCalendar.add(1, i);
        return gregorianCalendar.getTime();
    }

    public static Date addMonth(Date date, int i) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        gregorianCalendar.add(2, i);
        return gregorianCalendar.getTime();
    }

    public static Date addDay(Date date, int i) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        gregorianCalendar.add(5, i);
        return gregorianCalendar.getTime();
    }

    public static Date addHour(Date date, int i) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        gregorianCalendar.add(11, i);
        return gregorianCalendar.getTime();
    }

    public static Date addMinute(Date date, int i) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        gregorianCalendar.add(12, i);
        return gregorianCalendar.getTime();
    }

    public static Date addSecond(Date date, int i) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        gregorianCalendar.add(13, i);
        return gregorianCalendar.getTime();
    }

    public static Date getCurrentDate() {
        return new Date();
    }

    public static Integer getDateHours(Date date) {
        if (date == null) {
            return null;
        }
        return Integer.valueOf(date.getHours());
    }

    public static int getCurrentDateHours() {
        return getDateHours(getCurrentDate()).intValue();
    }

    public static boolean isAm() {
        int currentDateHours = getCurrentDateHours();
        return currentDateHours >= 0 && currentDateHours <= 12;
    }

    public static boolean isPm() {
        return !isAm();
    }

    public static String slimDate(String str) {
        String dateFormat = getDateFormat(getFormatDate(str, PURE_DATE_FORMAT), "MM.dd");
        return dateFormat.startsWith("0") ? dateFormat.substring(1) : dateFormat;
    }

    public static List<String> getDateRangeList(String str, String str2) {
        List<Date> dateRangeList = getDateRangeList(getFormatDate(str, PURE_DATE_FORMAT), getFormatDate(str2, PURE_DATE_FORMAT));
        ArrayList arrayList = new ArrayList(dateRangeList.size());
        Iterator<Date> it = dateRangeList.iterator();
        while (it.hasNext()) {
            arrayList.add(getDateFormat(it.next(), PURE_DATE_FORMAT));
        }
        return arrayList;
    }

    public static List<Date> getDateRangeList(Date date, Date date2) {
        ArgUtil.notNull(date, "startDate");
        ArgUtil.notNull(date2, "endDate");
        ArrayList arrayList = new ArrayList();
        if (date.compareTo(date2) > 0) {
            return arrayList;
        }
        arrayList.add(date);
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            Date dateAddDay = addDay(date, i);
            if (dateAddDay.compareTo(date2) > 0) {
                break;
            }
            arrayList.add(dateAddDay);
        }
        return arrayList;
    }

    public static String getDifferDate(Date date, int i) {
        return getDateFormat(addDay(date, i), PURE_DATE_FORMAT);
    }

    public static String changeFormat(String str, String str2, String str3) {
        return StringUtil.isEmpty(str) ? str : formatDate(parseDate(str, str2), str3);
    }

    public static long getDistanceDays(String str, String str2) {
        return getDistanceDays(parseDate(str, PURE_DATE_FORMAT), parseDate(str2, PURE_DATE_FORMAT));
    }

    public static long getDistanceDays(Date date, Date date2) {
        long time = date.getTime();
        long time2 = date2.getTime();
        return (time < time2 ? time2 - time : time - time2) / 86400000;
    }

    public static Date toDate(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.getTime();
    }

    public static Date toDate(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Date) {
            return (Date) obj;
        }
        if (obj instanceof Calendar) {
            return toDate((Calendar) obj);
        }
        throw new ClassCastException();
    }
}
