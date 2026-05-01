package com.aivox.common.util;

import android.content.Context;
import android.os.Vibrator;

/* JADX INFO: loaded from: classes.dex */
public class VibrateHelp {
    private static Vibrator vibrator;

    public static void vSimple(Context context, int i) {
        Vibrator vibrator2 = (Vibrator) context.getSystemService("vibrator");
        vibrator = vibrator2;
        vibrator2.vibrate(i);
    }

    public static void vComplicated(Context context, long[] jArr, int i) {
        Vibrator vibrator2 = (Vibrator) context.getSystemService("vibrator");
        vibrator = vibrator2;
        vibrator2.vibrate(jArr, i);
    }

    public static void stop() {
        Vibrator vibrator2 = vibrator;
        if (vibrator2 != null) {
            vibrator2.cancel();
        }
    }
}
