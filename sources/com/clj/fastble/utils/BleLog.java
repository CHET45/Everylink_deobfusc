package com.clj.fastble.utils;

import android.util.Log;

/* JADX INFO: loaded from: classes3.dex */
public final class BleLog {
    private static String defaultTag = "FastBle";
    public static boolean isPrint = true;

    /* JADX INFO: renamed from: d */
    public static void m503d(String str) {
        if (!isPrint || str == null) {
            return;
        }
        Log.d(defaultTag, str);
    }

    /* JADX INFO: renamed from: i */
    public static void m505i(String str) {
        if (!isPrint || str == null) {
            return;
        }
        Log.i(defaultTag, str);
    }

    /* JADX INFO: renamed from: w */
    public static void m506w(String str) {
        if (!isPrint || str == null) {
            return;
        }
        Log.w(defaultTag, str);
    }

    /* JADX INFO: renamed from: e */
    public static void m504e(String str) {
        if (!isPrint || str == null) {
            return;
        }
        Log.e(defaultTag, str);
    }
}
