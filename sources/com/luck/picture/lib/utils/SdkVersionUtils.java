package com.luck.picture.lib.utils;

import android.os.Build;

/* JADX INFO: loaded from: classes3.dex */
public class SdkVersionUtils {

    /* JADX INFO: renamed from: R */
    public static final int f832R = 30;
    public static final int TIRAMISU = 33;

    public static boolean isMaxN() {
        return true;
    }

    public static boolean isMinM() {
        return false;
    }

    public static boolean isN() {
        return false;
    }

    public static boolean isO() {
        return true;
    }

    public static boolean isP() {
        return Build.VERSION.SDK_INT >= 28;
    }

    public static boolean isQ() {
        return Build.VERSION.SDK_INT >= 29;
    }

    public static boolean isR() {
        return Build.VERSION.SDK_INT >= 30;
    }

    public static boolean isTIRAMISU() {
        return Build.VERSION.SDK_INT >= 33;
    }
}
