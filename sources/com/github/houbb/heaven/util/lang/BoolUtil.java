package com.github.houbb.heaven.util.lang;

import com.microsoft.azure.storage.Constants;

/* JADX INFO: loaded from: classes3.dex */
public final class BoolUtil {

    /* JADX INFO: renamed from: N */
    public static final String f540N = "N";

    /* JADX INFO: renamed from: Y */
    public static final String f541Y = "Y";

    private BoolUtil() {
    }

    public static boolean getBool(String str) {
        return "YES".equals(str) || f541Y.equals(str) || "1".equals(str) || Constants.TRUE.equals(str) || "是".equals(str);
    }

    public static String getYesOrNo(boolean z) {
        if (z) {
            return f541Y;
        }
        return "N";
    }
}
