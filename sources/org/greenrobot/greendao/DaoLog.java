package org.greenrobot.greendao;

import android.util.Log;

/* JADX INFO: loaded from: classes4.dex */
public class DaoLog {
    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int INFO = 4;
    private static final String TAG = "greenDAO";
    public static final int VERBOSE = 2;
    public static final int WARN = 5;

    public static boolean isLoggable(int i) {
        return Log.isLoggable(TAG, i);
    }

    public static String getStackTraceString(Throwable th) {
        return Log.getStackTraceString(th);
    }

    public static int println(int i, String str) {
        return Log.println(i, TAG, str);
    }

    /* JADX INFO: renamed from: v */
    public static int m1939v(String str) {
        return Log.v(TAG, str);
    }

    /* JADX INFO: renamed from: v */
    public static int m1940v(String str, Throwable th) {
        return Log.v(TAG, str, th);
    }

    /* JADX INFO: renamed from: d */
    public static int m1933d(String str) {
        return Log.d(TAG, str);
    }

    /* JADX INFO: renamed from: d */
    public static int m1934d(String str, Throwable th) {
        return Log.d(TAG, str, th);
    }

    /* JADX INFO: renamed from: i */
    public static int m1937i(String str) {
        return Log.i(TAG, str);
    }

    /* JADX INFO: renamed from: i */
    public static int m1938i(String str, Throwable th) {
        return Log.i(TAG, str, th);
    }

    /* JADX INFO: renamed from: w */
    public static int m1941w(String str) {
        return Log.w(TAG, str);
    }

    /* JADX INFO: renamed from: w */
    public static int m1942w(String str, Throwable th) {
        return Log.w(TAG, str, th);
    }

    /* JADX INFO: renamed from: w */
    public static int m1943w(Throwable th) {
        return Log.w(TAG, th);
    }

    /* JADX INFO: renamed from: e */
    public static int m1935e(String str) {
        return Log.w(TAG, str);
    }

    /* JADX INFO: renamed from: e */
    public static int m1936e(String str, Throwable th) {
        return Log.e(TAG, str, th);
    }
}
