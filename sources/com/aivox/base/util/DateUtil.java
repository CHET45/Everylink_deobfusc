package com.aivox.base.util;

import android.content.Context;
import android.util.Log;
import com.aivox.base.C0874R;
import com.blankj.utilcode.constant.TimeConstants;
import com.github.houbb.heaven.constant.PunctuationConst;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public class DateUtil {
    public static String DATE_IN_DETAIL = "E, MMM dd yyyy HH:mm";
    public static String DATE_IN_LIST = "E, MMM dd yyyy";
    public static String DATE_IN_LIST_WITHOUT_YEAR = "E, MMM dd";
    public static String DATE_IN_TIME_DETAIL = "MMM dd, yyyy.";
    public static String DATE_IN_TIME_DETAIL_WITHOUT_YEAR = "MMM dd";
    public static String DATE_IN_TIME_PACK = "MMM dd yyyy HH:mm";
    public static String HH_MM = "HH:mm";
    public static String HH_MM_MM_DD = "HH:mm/MMdd";
    public static String HH_MM_SS = "HH:mm:ss";
    public static String HH_MM_SS_SSS = "HH:mm:ss SSS";
    public static String MMDD = "MMdd";
    public static String MM_DD = "MM-dd";
    public static String MM_DD2 = "MM/dd";
    public static String MM_DD_HH_MM = "MM/dd HH:mm";
    public static String MM_DD_HH_MM_SS = "MM-dd HH:mm:ss";
    public static String MM_SS = "mm:ss";
    public static String YYYYMM = "yyyy-MM";
    public static String YYYYMMDD = "yyyyMMdd";
    public static String YYYYMMDD2 = "yyyy/MM/dd";
    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static String YYYYMMDD_expired = "yyyy/MM/dd 到期";
    public static String YYYY_MMDD_HHMM_SS = "yyyy_MMdd_HHmm_ss";
    public static String YYYY_MM_DD = "yyyy-MM-dd";
    public static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static String YYYY_MM_DD_T_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";
    public static String YYYY_MM_DD_dot = "yyyy.MM.dd";

    public static long timeConvert(String str) {
        String[] strArrSplit = str.split(":");
        return (Integer.parseInt(strArrSplit[0]) * TimeConstants.HOUR) + (Integer.parseInt(strArrSplit[1]) * 60000) + (Integer.parseInt(strArrSplit[2]) * 1000);
    }

    public static String getCurDate(String str) {
        return new SimpleDateFormat(str).format(new Date());
    }

    public static String numberToTime(int i) {
        String str = new DecimalFormat("00").format(i / 3600);
        String str2 = new DecimalFormat("00").format((i % 3600) / 60);
        String str3 = new DecimalFormat("00").format(i % 60);
        return i >= 3600 ? str + ":" + str2 + ":" + str3 : str2 + ":" + str3;
    }

    public static String numberToTimeInDetail(int i) {
        return new DecimalFormat("00").format(i / 3600) + ":" + new DecimalFormat("00").format((i % 3600) / 60) + ":" + new DecimalFormat("00").format(i % 60);
    }

    public static String numberToTime2(int i, Context context) {
        String str = new DecimalFormat("0").format(i / 3600);
        String str2 = new DecimalFormat("0").format((i % 3600) / 60);
        String str3 = new DecimalFormat("0").format(i % 60);
        if (str.equals("0")) {
            if (str2.equals("0")) {
                if (str3.equals("0")) {
                    return "0" + context.getResources().getString(C0874R.string.me_unit_hour);
                }
                return str3 + context.getResources().getString(C0874R.string.me_second);
            }
            if (str3.equals("0")) {
                return str2 + context.getResources().getString(C0874R.string.me_unit_min);
            }
            return str2 + context.getResources().getString(C0874R.string.me_unit_min) + str3 + context.getResources().getString(C0874R.string.me_second);
        }
        if (str2.equals("0")) {
            if (str3.equals("0")) {
                return str + context.getResources().getString(C0874R.string.me_unit_hour);
            }
            return str + context.getResources().getString(C0874R.string.me_unit_hour) + str3 + context.getResources().getString(C0874R.string.me_second);
        }
        if (str3.equals("0")) {
            return str + context.getResources().getString(C0874R.string.me_unit_hour) + str2 + context.getResources().getString(C0874R.string.me_unit_min);
        }
        return str + context.getResources().getString(C0874R.string.me_unit_hour) + str2 + context.getResources().getString(C0874R.string.me_unit_min) + str3 + context.getResources().getString(C0874R.string.me_second);
    }

    public static String minToTime(long j) {
        long j2 = j / 1000;
        return new DecimalFormat("00").format(j2 / 3600) + ":" + new DecimalFormat("00").format((j2 % 3600) / 60) + ":" + new DecimalFormat("00").format(j2 % 60) + " " + new DecimalFormat("000").format(j % 1000);
    }

    public static String minToTimeWithoutMm(long j) {
        long j2 = j / 1000;
        return new DecimalFormat("00").format(j2 / 3600) + ":" + new DecimalFormat("00").format((j2 % 3600) / 60) + ":" + new DecimalFormat("00").format(j2 % 60);
    }

    public static String getDateFromTimestamp(long j, String str) {
        return new SimpleDateFormat(str).format(new Date(j));
    }

    public static long getTimestampFromDate(String str, String str2) {
        if (BaseStringUtil.isEmpty(str)) {
            return 0L;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str2);
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            BaseAppUtils.printErrorMsg(e);
        }
        return date.getTime();
    }

    public static long UTC2Timestamp(String str, String str2) {
        return getTimestampFromDate(utc2Local(str, str2), str2);
    }

    public static String getCurrentTimeZone() {
        return TimeZone.getDefault().getDisplayName(false, 0);
    }

    public static String getCurrentTimeZone2() {
        return TimeZone.getDefault().getDisplayName(false, 1);
    }

    public static StringBuffer getNowDate() {
        StringBuffer stringBuffer = new StringBuffer();
        Calendar calendar = Calendar.getInstance();
        stringBuffer.append(calendar.get(1)).append(".");
        if (calendar.get(2) + 1 < 10) {
            stringBuffer.append("0");
        }
        stringBuffer.append(calendar.get(2) + 1).append(".");
        if (calendar.get(5) < 10) {
            stringBuffer.append("0");
        }
        stringBuffer.append(calendar.get(5));
        return stringBuffer;
    }

    public static StringBuffer getNowMonth() {
        StringBuffer stringBuffer = new StringBuffer();
        Calendar calendar = Calendar.getInstance();
        stringBuffer.append(calendar.get(1));
        if (calendar.get(2) + 1 < 10) {
            stringBuffer.append("0");
        }
        stringBuffer.append(calendar.get(2) + 1);
        if (calendar.get(5) < 10) {
            stringBuffer.append("0");
        }
        return stringBuffer;
    }

    public static StringBuffer getNowYear() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(Calendar.getInstance().get(1));
        return stringBuffer;
    }

    public static int getHourOfDay() {
        return Calendar.getInstance().get(11);
    }

    public static StringBuffer getNowTime() {
        StringBuffer stringBuffer = new StringBuffer();
        Calendar calendar = Calendar.getInstance();
        stringBuffer.append(calendar.get(1) + "-");
        if (calendar.get(2) + 1 < 10) {
            stringBuffer.append("0");
        }
        stringBuffer.append((calendar.get(2) + 1) + "-");
        if (calendar.get(5) < 10) {
            stringBuffer.append("0");
        }
        stringBuffer.append(calendar.get(5) + " ");
        if (calendar.get(11) < 10) {
            stringBuffer.append("0");
        }
        stringBuffer.append(calendar.get(11) + ":");
        stringBuffer.append(calendar.get(12));
        return stringBuffer;
    }

    public static StringBuffer getNowWeekDay() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(Calendar.getInstance().get(7) - 1);
        return stringBuffer;
    }

    public static int getTrueWeek(int i) {
        if (Calendar.getInstance().getFirstDayOfWeek() != 1) {
            return i;
        }
        int i2 = i - 1;
        if (i2 == 0) {
            return 7;
        }
        return i2;
    }

    public static String dateToWeek(String str) {
        String[] stringArray = BaseAppUtils.getContext().getResources().getStringArray(C0874R.array.week_day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Calendar.getInstance().setTime(simpleDateFormat.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return stringArray[getTrueWeek(r2.get(7)) - 1];
    }

    public static int dateToWeek(int i, int i2, int i3) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(i, i2 - 1, i3);
        int trueWeek = getTrueWeek(calendar.get(7)) - 1;
        LogUtil.m338i(String.format("Y:%s_M:%s_D:%s_trueWeekIndex:%s", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(trueWeek)));
        return trueWeek;
    }

    public static boolean compare_date(String str, String str2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM);
        try {
            return simpleDateFormat.parse(str).getTime() < simpleDateFormat.parse(str2).getTime();
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return false;
        }
    }

    public static boolean compare_date(String str, String str2, String str3) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str3);
        try {
            return simpleDateFormat.parse(str).getTime() < simpleDateFormat.parse(str2).getTime();
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return false;
        }
    }

    public static boolean isTimePassed(String str) {
        try {
            return UTC2Timestamp(str, YYYY_MM_DD_T_HH_MM_SS) < System.currentTimeMillis();
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return false;
        }
    }

    public static long compareDate(String str, String str2, String str3) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str3);
        try {
            return (simpleDateFormat.parse(str).getTime() - simpleDateFormat.parse(str2).getTime()) / 86400000;
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            Log.e("tag", "e:" + e.getLocalizedMessage());
            return 0L;
        }
    }

    public static boolean compare_date2(String str, String str2) {
        return !str.equals(str2);
    }

    public static String longTimeToDay(Long l) {
        Integer num = 1000;
        Integer numValueOf = Integer.valueOf(num.intValue() * 60);
        Integer numValueOf2 = Integer.valueOf(numValueOf.intValue() * 60);
        Integer numValueOf3 = Integer.valueOf(numValueOf2.intValue() * 24);
        Long lValueOf = Long.valueOf(l.longValue() / ((long) numValueOf3.intValue()));
        Long lValueOf2 = Long.valueOf((l.longValue() - (lValueOf.longValue() * ((long) numValueOf3.intValue()))) / ((long) numValueOf2.intValue()));
        Long lValueOf3 = Long.valueOf(((l.longValue() - (lValueOf.longValue() * ((long) numValueOf3.intValue()))) - (lValueOf2.longValue() * ((long) numValueOf2.intValue()))) / ((long) numValueOf.intValue()));
        Long lValueOf4 = Long.valueOf((((l.longValue() - (lValueOf.longValue() * ((long) numValueOf3.intValue()))) - (lValueOf2.longValue() * ((long) numValueOf2.intValue()))) - (lValueOf3.longValue() * ((long) numValueOf.intValue()))) / ((long) num.intValue()));
        Long.valueOf((((l.longValue() - (lValueOf.longValue() * ((long) numValueOf3.intValue()))) - (lValueOf2.longValue() * ((long) numValueOf2.intValue()))) - (lValueOf3.longValue() * ((long) numValueOf.intValue()))) - (lValueOf4.longValue() * ((long) num.intValue())));
        StringBuffer stringBuffer = new StringBuffer();
        if (lValueOf.longValue() > 0) {
            stringBuffer.append(lValueOf + "天");
        }
        if (lValueOf2.longValue() > 0) {
            stringBuffer.append(lValueOf2 + "小时");
        }
        if (lValueOf3.longValue() > 0) {
            stringBuffer.append((lValueOf3.longValue() > 9 ? new StringBuilder() : new StringBuilder("0")).append(lValueOf3).append(":").toString());
        }
        if (lValueOf4.longValue() > 0) {
            stringBuffer.append((lValueOf4.longValue() > 9 ? new StringBuilder("") : new StringBuilder("0")).append(lValueOf4).toString());
        }
        return stringBuffer.toString();
    }

    public static String[] getLeftTimeFormatedStrings(long j) {
        String strValueOf;
        String strValueOf2;
        String strValueOf3;
        String strValueOf4;
        String strValueOf5 = "00";
        if (j <= 0) {
            strValueOf = "000";
            strValueOf2 = "00";
            strValueOf3 = strValueOf2;
            strValueOf4 = strValueOf3;
        } else {
            long j2 = j % 1000;
            if (j2 > 100) {
                strValueOf = String.valueOf(j2);
            } else if (j2 >= 10 && j2 < 100) {
                strValueOf = String.valueOf("0" + j2);
            } else {
                strValueOf = String.valueOf("00" + j2);
            }
            long j3 = j / 1000;
            long j4 = j3 % 60;
            if (j4 < 10) {
                strValueOf2 = String.valueOf("0" + j4);
            } else {
                strValueOf2 = String.valueOf(j4);
            }
            long j5 = j3 / 60;
            long j6 = j5 % 60;
            if (j6 < 10) {
                strValueOf3 = String.valueOf("0" + j6);
            } else {
                strValueOf3 = String.valueOf(j6);
            }
            long j7 = j5 / 60;
            long j8 = j7 % 24;
            if (j8 < 10) {
                strValueOf4 = String.valueOf("0" + j8);
            } else {
                strValueOf4 = String.valueOf(j8);
            }
            long j9 = j7 / 24;
            if (j9 < 10) {
                strValueOf5 = String.valueOf("0" + j9);
            } else {
                strValueOf5 = String.valueOf(j9);
            }
        }
        return new String[]{strValueOf5, strValueOf4, strValueOf3, strValueOf2, strValueOf};
    }

    public static long getDateTimeNow() {
        try {
            return Calendar.getInstance().getTimeInMillis();
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return 0L;
        }
    }

    public static long getDateTimeNowSecond() {
        return getDateTimeNow() / 1000;
    }

    public static String getTimeFromMills(long j, String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        String str2 = simpleDateFormat.format(calendar.getTime());
        LogUtil.m338i("getTimeFromMills:" + j + "/" + str2);
        return str2;
    }

    public static long getMillsFromDate(String str) {
        long jLongValue = Long.valueOf(str.substring(6, str.lastIndexOf(")"))).longValue();
        LogUtil.m338i("getMillsFromSportsDate:" + jLongValue);
        return jLongValue;
    }

    public static long getDateTimeFromString(String str) {
        long time = 0;
        if (str == null) {
            return 0L;
        }
        try {
            time = new SimpleDateFormat(YYYY_MM_DD).parse(str.trim()).getTime();
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
        }
        LogUtil.m338i("getDateTimeFromString:" + time);
        return time;
    }

    public static String getSimpleTimeString(long j) {
        Date date = new Date(j);
        Date date2 = new Date();
        date2.setHours(0);
        date2.setMinutes(0);
        date2.setSeconds(0);
        if (date.before(date2)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MM_DD);
            simpleDateFormat.setTimeZone(TimeZone.getDefault());
            return simpleDateFormat.format(date);
        }
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(HH_MM);
        simpleDateFormat2.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat2.format(date);
    }

    public static String getSimpleTimeString2(long j) {
        Date date = new Date(j);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HH_MM_MM_DD);
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(date);
    }

    public static String getSimpleTimeString3(long j) {
        Date date = new Date(j);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MM_DD_HH_MM_SS);
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(date);
    }

    public static String getSimpleTimeString4(long j) {
        Date date = new Date(j);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM);
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(date);
    }

    public static String getSimpleTimeStringHMS(long j) {
        Date date = new Date(j);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HH_MM_SS);
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(date);
    }

    public static String getUtcTimeString(long j) {
        return new SimpleDateFormat(YYYY_MM_DD_HH_MM).format(new Date(j));
    }

    public static String getUnixTimeString(long j) {
        return new SimpleDateFormat(YYYY_MM_DD_HH_MM).format(new Date(j * 1000));
    }

    public static String getUnixTimeStringMonthToDay(long j) {
        return new SimpleDateFormat(YYYY_MM_DD).format(new Date(j * 1000));
    }

    public static String getContractTimeString(long j) {
        return new SimpleDateFormat(MMDD).format(new Date(j * 1000));
    }

    public static String format(long j) {
        if (j < 60000) {
            return "00：" + ((j % 60000) / 1000);
        }
        return (j < 60000 || j >= 3600000) ? (j / 3600000) + ":" + ((j % 3600000) / 60000) + ":" + ((j % 60000) / 1000) : ((j % 3600000) / 60000) + ":" + ((j % 60000) / 1000);
    }

    public static String getTimeString(Date date) {
        Calendar.getInstance().setTime(date);
        String strValueOf = String.valueOf(1);
        String str = "" + ("0000".substring(0, 4 - strValueOf.length()) + strValueOf);
        String strValueOf2 = String.valueOf(2);
        String str2 = str + ("0000".substring(0, 2 - strValueOf2.length()) + strValueOf2);
        String strValueOf3 = String.valueOf(5);
        String str3 = str2 + ("0000".substring(0, 2 - strValueOf3.length()) + strValueOf3);
        String strValueOf4 = String.valueOf(11);
        String str4 = str3 + ("0000".substring(0, 2 - strValueOf4.length()) + strValueOf4);
        String strValueOf5 = String.valueOf(12);
        String str5 = str4 + ("0000".substring(0, 2 - strValueOf5.length()) + strValueOf5);
        String strValueOf6 = String.valueOf(13);
        return str5 + ("0000".substring(0, 2 - strValueOf6.length()) + strValueOf6);
    }

    public static String getTimeInterval(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (1 == calendar.get(7)) {
            calendar.add(5, -1);
        }
        calendar.setFirstDayOfWeek(2);
        calendar.add(5, calendar.getFirstDayOfWeek() - calendar.get(7));
        String str = simpleDateFormat.format(calendar.getTime());
        calendar.add(5, 6);
        return str + PunctuationConst.COMMA + simpleDateFormat.format(calendar.getTime());
    }

    public String getLastTimeInterval() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int i = calendar.get(7) - 1;
        calendar.add(5, (-6) - i);
        calendar2.add(5, 0 - i);
        return simpleDateFormat.format(calendar.getTime()) + PunctuationConst.COMMA + simpleDateFormat.format(calendar2.getTime());
    }

    public static List<Date> findDates(Date date, Date date2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar.getInstance().setTime(date2);
        while (date2.after(calendar.getTime())) {
            calendar.add(5, 1);
            arrayList.add(calendar.getTime());
        }
        return arrayList;
    }

    public static List<String> getDateForThisWeek() {
        Date date;
        String[] strArrSplit = getTimeInterval(new Date()).split(PunctuationConst.COMMA);
        String str = strArrSplit[0];
        String str2 = strArrSplit[1];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = null;
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e = e;
            date = null;
        }
        try {
            date2 = simpleDateFormat.parse(str2);
        } catch (ParseException e2) {
            e = e2;
            BaseAppUtils.printErrorMsg(e);
        }
        List<Date> listFindDates = findDates(date, date2);
        ArrayList arrayList = new ArrayList();
        for (Date date3 : listFindDates) {
            arrayList.add(simpleDateFormat.format(date3));
            LogUtil.m338i("日期：" + simpleDateFormat.format(date3));
        }
        return arrayList;
    }

    public static String local2GMT(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("gmt"));
        return simpleDateFormat.format(new Date());
    }

    public static String local2UTC(String str, String str2) {
        Date date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str2);
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(YYYY_MM_DD_T_HH_MM_SS);
        simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return simpleDateFormat2.format(Long.valueOf(date.getTime()));
    }

    public static String local2UTC(long j, String str) {
        Date date;
        Date date2 = new Date(j);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        try {
            date = simpleDateFormat.parse(simpleDateFormat.format(date2));
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(YYYY_MM_DD_T_HH_MM_SS);
        simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return simpleDateFormat2.format(Long.valueOf(date.getTime()));
    }

    public static String utc2Local(String str, String str2) {
        if (BaseStringUtil.isEmpty(str)) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD_T_HH_MM_SS);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        try {
            Date date = simpleDateFormat.parse(str);
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(str2);
            simpleDateFormat2.setTimeZone(TimeZone.getDefault());
            return simpleDateFormat2.format(Long.valueOf(date.getTime()));
        } catch (ParseException e) {
            LogUtil.m336e("e:" + e);
            return "";
        }
    }

    public static String getDateForList(String str, boolean z) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD_T_HH_MM_SS);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(z ? DATE_IN_LIST_WITHOUT_YEAR : DATE_IN_LIST);
            simpleDateFormat2.setTimeZone(TimeZone.getDefault());
            return simpleDateFormat2.format(simpleDateFormat.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getDateForHomeList(String str, boolean z) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD_T_HH_MM_SS);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(z ? YYYY_MM_DD_HH_MM : YYYYMMDD2);
            simpleDateFormat2.setTimeZone(TimeZone.getDefault());
            return simpleDateFormat2.format(simpleDateFormat.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getDateForDetail(String str) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD_T_HH_MM_SS);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(DATE_IN_DETAIL);
            simpleDateFormat2.setTimeZone(TimeZone.getDefault());
            return simpleDateFormat2.format(simpleDateFormat.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getDateForTimeDetail(String str, boolean z) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD_T_HH_MM_SS);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(z ? DATE_IN_TIME_DETAIL_WITHOUT_YEAR : DATE_IN_TIME_DETAIL);
            simpleDateFormat2.setTimeZone(TimeZone.getDefault());
            return simpleDateFormat2.format(simpleDateFormat.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getDisplayDateStr(String str, Context context) {
        String[] strArr = {context.getString(C0874R.string.birthday_month_january), context.getString(C0874R.string.birthday_month_february), context.getString(C0874R.string.birthday_month_march), context.getString(C0874R.string.birthday_month_april), context.getString(C0874R.string.birthday_month_may), context.getString(C0874R.string.birthday_month_june), context.getString(C0874R.string.birthday_month_july), context.getString(C0874R.string.birthday_month_august), context.getString(C0874R.string.birthday_month_september), context.getString(C0874R.string.birthday_month_october), context.getString(C0874R.string.birthday_month_november), context.getString(C0874R.string.birthday_month_december)};
        if (str.split("-").length != 3) {
            return "";
        }
        int i = Integer.parseInt(str.split("-")[0]);
        int i2 = Integer.parseInt(str.split("-")[1]);
        int i3 = Integer.parseInt(str.split("-")[2]);
        return i > 12 ? strArr[i2 - 1] + " " + i3 + ", " + i : strArr[i - 1] + " " + i2 + ", " + i3;
    }

    public static boolean isTodayBirthday(String str) {
        if (str == null || str.split("-").length != 3) {
            return false;
        }
        int i = Integer.parseInt(str.split("-")[0]);
        int i2 = Integer.parseInt(str.split("-")[1]);
        int i3 = Integer.parseInt(str.split("-")[2]);
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        if (i > 12) {
            calendar2.set(i, i2 - 1, i3);
        } else {
            calendar2.set(i3, i - 1, i2);
        }
        return calendar.get(5) == calendar2.get(5) && calendar.get(2) == calendar2.get(2) && calendar.get(1) != calendar2.get(1);
    }
}
