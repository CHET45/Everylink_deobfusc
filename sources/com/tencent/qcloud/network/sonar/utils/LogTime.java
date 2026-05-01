package com.tencent.qcloud.network.sonar.utils;

import android.os.SystemClock;

/* JADX INFO: loaded from: classes4.dex */
public final class LogTime {
    private static final double MILLIS_MULTIPLIER = 1.0d / Math.pow(10.0d, 6.0d);

    private LogTime() {
    }

    public static long getLogTime() {
        return SystemClock.elapsedRealtimeNanos();
    }

    public static int getElapsedMillis(long j) {
        return (int) ((getLogTime() - j) * MILLIS_MULTIPLIER);
    }
}
