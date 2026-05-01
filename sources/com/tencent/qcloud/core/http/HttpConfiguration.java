package com.tencent.qcloud.core.http;

import com.tencent.qcloud.core.logger.COSLogger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes4.dex */
public class HttpConfiguration {
    private static final String RFC822_DATE_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";
    private static final AtomicLong GLOBAL_TIME_OFFSET = new AtomicLong(0);
    private static final TimeZone GMT_TIMEZONE = TimeZone.getTimeZone("GMT");
    private static ThreadLocal<SimpleDateFormat> gmtFormatters = new ThreadLocal<>();

    public static long calculateGlobalTimeOffset(String str, Date date) {
        AtomicLong atomicLong = GLOBAL_TIME_OFFSET;
        long j = atomicLong.get();
        calculateGlobalTimeOffset(str, date, 0);
        return Math.abs(j - atomicLong.get());
    }

    public static void calculateGlobalTimeOffset(String str, Date date, int i) {
        try {
            long time = (getFormatter().parse(str).getTime() - date.getTime()) / 1000;
            if (Math.abs(time) >= i) {
                GLOBAL_TIME_OFFSET.set(time);
                COSLogger.iNetwork(QCloudHttpClient.HTTP_LOG_TAG, "NEW TIME OFFSET is " + time + "s");
            }
        } catch (ParseException unused) {
        }
    }

    public static long getDeviceTimeWithOffset() {
        return System.currentTimeMillis() / 1000;
    }

    public static String getGMTDate(Date date) {
        return getFormatter().format(date);
    }

    private static SimpleDateFormat getFormatter() {
        SimpleDateFormat simpleDateFormat = gmtFormatters.get();
        if (simpleDateFormat != null) {
            return simpleDateFormat;
        }
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(RFC822_DATE_PATTERN, Locale.US);
        simpleDateFormat2.setTimeZone(GMT_TIMEZONE);
        simpleDateFormat2.setLenient(false);
        gmtFormatters.set(simpleDateFormat2);
        return simpleDateFormat2;
    }

    public static Date getGMTDate(String str) {
        try {
            return getFormatter().parse(str);
        } catch (ParseException unused) {
            return null;
        }
    }
}
