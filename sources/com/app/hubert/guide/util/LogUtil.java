package com.app.hubert.guide.util;

import android.text.TextUtils;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class LogUtil {
    private static final int NONE = 8;
    public static final int level = 8;
    private static final String tagPrefix = "NewbieGuide";

    /* JADX INFO: renamed from: d */
    public static void m369d(String str) {
    }

    /* JADX INFO: renamed from: d */
    public static void m370d(String str, Throwable th) {
    }

    /* JADX INFO: renamed from: e */
    public static void m371e(String str) {
    }

    /* JADX INFO: renamed from: e */
    public static void m372e(String str, Throwable th) {
    }

    /* JADX INFO: renamed from: i */
    public static void m373i(String str) {
    }

    /* JADX INFO: renamed from: i */
    public static void m374i(String str, Throwable th) {
    }

    /* JADX INFO: renamed from: v */
    public static void m375v(String str) {
    }

    /* JADX INFO: renamed from: v */
    public static void m376v(String str, Throwable th) {
    }

    /* JADX INFO: renamed from: w */
    public static void m377w(String str) {
    }

    /* JADX INFO: renamed from: w */
    public static void m378w(String str, Throwable th) {
    }

    private static String generateTag() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
        String className = stackTraceElement.getClassName();
        String str = String.format(Locale.CHINA, "%s.%s(L:%d)", className.substring(className.lastIndexOf(".") + 1), stackTraceElement.getMethodName(), Integer.valueOf(stackTraceElement.getLineNumber()));
        return TextUtils.isEmpty("NewbieGuide") ? str : "NewbieGuide:" + str;
    }
}
