package com.tencent.qcloud.core.logger;

/* JADX INFO: loaded from: classes4.dex */
public final class QCloudLogger {
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;

    private QCloudLogger() {
    }

    /* JADX INFO: renamed from: v */
    public static void m1865v(String str, String str2, Object... objArr) {
        print(2, str, null, str2, objArr);
    }

    /* JADX INFO: renamed from: v */
    public static void m1866v(String str, Throwable th, String str2, Object... objArr) {
        print(2, str, th, str2, objArr);
    }

    /* JADX INFO: renamed from: d */
    public static void m1859d(String str, String str2, Object... objArr) {
        print(3, str, null, str2, objArr);
    }

    /* JADX INFO: renamed from: d */
    public static void m1860d(String str, Throwable th, String str2, Object... objArr) {
        print(3, str, th, str2, objArr);
    }

    /* JADX INFO: renamed from: i */
    public static void m1863i(String str, String str2, Object... objArr) {
        print(4, str, null, str2, objArr);
    }

    /* JADX INFO: renamed from: i */
    public static void m1864i(String str, Throwable th, String str2, Object... objArr) {
        print(4, str, th, str2, objArr);
    }

    /* JADX INFO: renamed from: w */
    public static void m1867w(String str, String str2, Object... objArr) {
        print(5, str, null, str2, objArr);
    }

    /* JADX INFO: renamed from: w */
    public static void m1868w(String str, Throwable th, String str2, Object... objArr) {
        print(5, str, th, str2, objArr);
    }

    /* JADX INFO: renamed from: e */
    public static void m1861e(String str, String str2, Object... objArr) {
        print(6, str, null, str2, objArr);
    }

    /* JADX INFO: renamed from: e */
    public static void m1862e(String str, Throwable th, String str2, Object... objArr) {
        print(6, str, th, str2, objArr);
    }

    private static void print(int i, String str, Throwable th, String str2, Object... objArr) {
        LogLevel logLevel;
        if (objArr != null) {
            try {
                if (objArr.length > 0) {
                    str2 = String.format(str2, objArr);
                }
            } catch (Exception unused) {
                str2 = str2 + ": !!!! Log format exception: ";
            }
        }
        String str3 = str2;
        if (i == 3) {
            logLevel = LogLevel.DEBUG;
        } else if (i == 4) {
            logLevel = LogLevel.INFO;
        } else if (i == 5) {
            logLevel = LogLevel.WARN;
        } else if (i == 6) {
            logLevel = LogLevel.ERROR;
        } else {
            logLevel = LogLevel.VERBOSE;
        }
        LogLevel logLevel2 = logLevel;
        synchronized (QCloudLogger.class) {
            COSLogger.getInstance().log(logLevel2, LogCategory.PROCESS, str, str3, th);
        }
    }
}
