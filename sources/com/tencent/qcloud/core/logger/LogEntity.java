package com.tencent.qcloud.core.logger;

import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class LogEntity {
    private final LogCategory category;
    private final Map<String, String> extras;
    private final LogLevel level;
    private final String message;
    private final String tag;
    private final Throwable throwable;
    private final long timestamp = System.currentTimeMillis();
    private final String threadName = Thread.currentThread().getName();

    public LogEntity(LogLevel logLevel, LogCategory logCategory, String str, String str2, Map<String, String> map, Throwable th) {
        this.level = logLevel;
        this.category = logCategory;
        this.tag = str;
        this.message = str2;
        this.extras = map;
        this.throwable = th;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public LogLevel getLevel() {
        return this.level;
    }

    public LogCategory getCategory() {
        return this.category;
    }

    public String getTag() {
        return this.tag;
    }

    public String getMessage() {
        return this.message;
    }

    public String getThreadName() {
        return this.threadName;
    }

    public Map<String, String> getExtras() {
        return this.extras;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.level).append("/");
        sb.append(timeUtils(this.timestamp, "yyyy-MM-dd HH:mm:ss"));
        sb.append("[").append(this.threadName).append("][");
        sb.append(this.category).append(" ").append(this.tag).append("][");
        sb.append(this.message).append("][");
        sb.append(this.extras).append("]");
        if (this.throwable != null) {
            sb.append(" * Exception :\n").append(Log.getStackTraceString(this.throwable));
        }
        sb.append("\n");
        return sb.toString();
    }

    public String getLogcatString() {
        if (getExtras() == null || getExtras().isEmpty()) {
            return String.format("[%s][%s] %s", getCategory().name(), getThreadName(), getMessage());
        }
        return String.format("[%s][%s] %s - %s", getCategory().name(), getThreadName(), getMessage(), getExtras());
    }

    public long getLength() {
        return (this.message != null ? r0.length() : 0) + 40;
    }

    private static String timeUtils(long j, String str) {
        Date date = new Date(j);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return new SimpleDateFormat(str, Locale.CHINA).format(gregorianCalendar.getTime());
    }
}
