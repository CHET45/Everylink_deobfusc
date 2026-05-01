package com.lzy.okgo.utils;

import android.util.Log;

/* JADX INFO: loaded from: classes4.dex */
public class OkLogger {
    private static boolean isLogEnable = true;
    public static String tag = "OkGo";

    public static void debug(boolean z) {
        debug(tag, z);
    }

    public static void debug(String str, boolean z) {
        tag = str;
        isLogEnable = z;
    }

    /* JADX INFO: renamed from: v */
    public static void m867v(String str) {
        m868v(tag, str);
    }

    /* JADX INFO: renamed from: v */
    public static void m868v(String str, String str2) {
        if (isLogEnable) {
            Log.v(str, str2);
        }
    }

    /* JADX INFO: renamed from: d */
    public static void m860d(String str) {
        m861d(tag, str);
    }

    /* JADX INFO: renamed from: d */
    public static void m861d(String str, String str2) {
        if (isLogEnable) {
            Log.d(str, str2);
        }
    }

    /* JADX INFO: renamed from: i */
    public static void m865i(String str) {
        m866i(tag, str);
    }

    /* JADX INFO: renamed from: i */
    public static void m866i(String str, String str2) {
        if (isLogEnable) {
            Log.i(str, str2);
        }
    }

    /* JADX INFO: renamed from: w */
    public static void m869w(String str) {
        m870w(tag, str);
    }

    /* JADX INFO: renamed from: w */
    public static void m870w(String str, String str2) {
        if (isLogEnable) {
            Log.w(str, str2);
        }
    }

    /* JADX INFO: renamed from: e */
    public static void m862e(String str) {
        m863e(tag, str);
    }

    /* JADX INFO: renamed from: e */
    public static void m863e(String str, String str2) {
        if (isLogEnable) {
            Log.e(str, str2);
        }
    }

    /* JADX INFO: renamed from: e */
    public static void m864e(Throwable th) {
        if (isLogEnable) {
            th.printStackTrace();
        }
    }
}
