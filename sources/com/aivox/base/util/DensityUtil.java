package com.aivox.base.util;

import android.content.Context;
import android.util.TypedValue;

/* JADX INFO: loaded from: classes.dex */
public class DensityUtil {
    private DensityUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static int dp2px(Context context, float f) {
        return (int) TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float f) {
        int iApplyDimension = (int) TypedValue.applyDimension(2, f, context.getResources().getDisplayMetrics());
        LogUtil.m338i("sp2px:" + iApplyDimension);
        return iApplyDimension;
    }

    public static int px2dp(Context context, float f) {
        int i = (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
        LogUtil.m338i("px2dp:" + i);
        return i;
    }

    public static float px2sp(Context context, float f) {
        float f2 = f / context.getResources().getDisplayMetrics().scaledDensity;
        LogUtil.m338i("px2sp:" + f2);
        return f2;
    }
}
