package com.aivox.base.util;

import android.content.Context;
import android.content.Intent;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class AlarmUtils {

    public interface PermissionListener {
        void isGranted(boolean z);
    }

    public static void addAlarm(Context context, int i, int i2, String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(6);
        Intent intentPutExtra = new Intent("android.intent.action.SET_ALARM").putExtra("android.intent.extra.alarm.DAYS", "").putExtra("android.intent.extra.alarm.HOUR", i).putExtra("android.intent.extra.alarm.MINUTES", i2).putExtra("android.intent.extra.alarm.MESSAGE", str).putExtra("android.intent.extra.alarm.VIBRATE", true).putExtra("android.intent.extra.alarm.SKIP_UI", true);
        if (intentPutExtra.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intentPutExtra);
        }
    }
}
