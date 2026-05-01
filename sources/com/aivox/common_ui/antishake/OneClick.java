package com.aivox.common_ui.antishake;

import android.util.Log;
import java.util.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class OneClick {
    private long lastClickTime = 0;
    private String methodName;

    public OneClick(String str) {
        this.methodName = str;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public boolean check(long j) {
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        Log.i("tag", "点击：" + timeInMillis);
        if (timeInMillis - this.lastClickTime <= j) {
            return true;
        }
        this.lastClickTime = timeInMillis;
        return false;
    }
}
