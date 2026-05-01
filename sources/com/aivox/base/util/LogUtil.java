package com.aivox.base.util;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class LogUtil {
    private static final String TAG = "LogUtil";
    private static final boolean isDebug = false;

    /* JADX INFO: renamed from: d */
    public static void m334d(String str) {
    }

    /* JADX INFO: renamed from: d */
    public static void m335d(String str, String str2) {
    }

    /* JADX INFO: renamed from: e */
    public static void m336e(String str) {
    }

    /* JADX INFO: renamed from: e */
    public static void m337e(String str, String str2) {
    }

    /* JADX INFO: renamed from: i */
    public static void m338i(String str) {
    }

    /* JADX INFO: renamed from: i */
    public static void m339i(String str, String str2) {
    }

    public static boolean isDebug() {
        return false;
    }

    /* JADX INFO: renamed from: v */
    public static void m340v(String str) {
    }

    /* JADX INFO: renamed from: v */
    public static void m341v(String str, String str2) {
    }

    private LogUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void checkLength(String str, String str2, String str3) {
        int iMin;
        if (str2.length() < 3800) {
            str3.hashCode();
            switch (str3) {
                case "d":
                    Log.d(str, str2);
                    break;
                case "e":
                    Log.e(str, str2);
                    break;
                case "i":
                    Log.i(str, str2);
                    break;
                case "v":
                    Log.v(str, str2);
                    break;
            }
            return;
        }
        int length = str2.length();
        int i = 0;
        while (i < length) {
            int i2 = i + 3800;
            iMin = Math.min(i2, length);
            str3.hashCode();
            switch (str3) {
                case "d":
                    Log.d(str, str2.substring(i, iMin));
                    break;
                case "e":
                    Log.e(str, str2.substring(i, iMin));
                    break;
                case "i":
                    Log.i(str, str2.substring(i, iMin));
                    break;
                case "v":
                    Log.v(str, str2.substring(i, iMin));
                    break;
            }
            i = i2;
        }
    }

    public static void checkLength(String str, String str2) {
        checkLength(TAG, str, str2);
    }
}
