package com.aivox.common_ui.pulltorefresh.internal;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class Utils {
    static final String LOG_TAG = "PullToRefresh";

    public static void warnDeprecation(String str, String str2) {
        Log.w(LOG_TAG, "You're using the deprecated " + str + " attr, please switch over to " + str2);
    }
}
