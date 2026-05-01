package com.tencent.qcloud.core.util;

import android.content.Context;

/* JADX INFO: loaded from: classes4.dex */
public class ContextHolder {
    private static Context appContext;

    public static void setContext(Context context) {
        appContext = context.getApplicationContext();
    }

    public static Context getAppContext() {
        return appContext;
    }
}
