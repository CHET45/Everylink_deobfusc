package com.tencent.qcloud.network.sonar.utils;

import android.util.Log;

/* JADX INFO: loaded from: classes4.dex */
public final class SonarLog {
    private static final String TAG = "NETWORK-SONAR";
    public static boolean openLog = false;

    /* JADX INFO: renamed from: v */
    public static void m1876v(String str, Object obj) {
        if (openLog) {
            Log.v(TAG, format(str, obj));
        }
    }

    /* JADX INFO: renamed from: d */
    public static void m1874d(String str, Object obj) {
        if (openLog) {
            Log.d(TAG, format(str, obj));
        }
    }

    /* JADX INFO: renamed from: w */
    public static void m1877w(String str, Object obj) {
        if (openLog) {
            Log.w(TAG, format(str, obj));
        }
    }

    /* JADX INFO: renamed from: e */
    public static void m1875e(String str, Object obj) {
        if (openLog) {
            Log.e(TAG, format(str, obj));
        }
    }

    private static String format(String str, Object obj) {
        return String.format("module: %s, %s", str, toString(obj));
    }

    public static String format(String str, Object... objArr) {
        return String.format(str, objArr);
    }

    public static String toString(Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof Number) {
            return obj.toString();
        }
        if (obj instanceof Boolean) {
            return String.valueOf(obj);
        }
        return obj.toString();
    }
}
