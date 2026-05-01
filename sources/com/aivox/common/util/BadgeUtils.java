package com.aivox.common.util;

import android.app.Notification;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import com.blankj.utilcode.util.RomUtils;

/* JADX INFO: loaded from: classes.dex */
public class BadgeUtils {
    private static final String TAG = "BadgeUtils";

    public static String getLauncherClassName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setPackage(context.getPackageName());
        intent.addCategory("android.intent.category.LAUNCHER");
        ResolveInfo resolveInfoResolveActivity = packageManager.resolveActivity(intent, 65536);
        if (resolveInfoResolveActivity == null) {
            resolveInfoResolveActivity = packageManager.resolveActivity(intent, 0);
        }
        return resolveInfoResolveActivity.activityInfo.name;
    }

    public static void setBadgeCount(Context context, int i) {
        int iMax = i > 0 ? Math.max(0, Math.min(i, 99)) : 0;
        Log.e(TAG, "当前设备类型: " + Build.MANUFACTURER);
        if (RomUtils.isHuawei()) {
            setBadgeOfEXUI(context, iMax);
            return;
        }
        if (Build.MANUFACTURER.toLowerCase().contains("nova")) {
            setBadgeOfNova(context, iMax);
            return;
        }
        if (Build.MANUFACTURER.toLowerCase().contains("zuk")) {
            setBadgeOfZuk(context, iMax);
            return;
        }
        if (RomUtils.isSony()) {
            setBadgeOfSony(context, iMax);
            return;
        }
        if (RomUtils.isSamsung() || RomUtils.isLg()) {
            setBadgeOfSumsung(context, iMax);
        } else if (RomUtils.isHtc()) {
            setBadgeOfHTC(context, iMax);
        } else if (RomUtils.isVivo()) {
            setVivoBadge(context, iMax);
        }
    }

    private static void setBadgeOfEXUI(Context context, int i) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString("package", context.getPackageName());
            bundle.putString("class", getLauncherClassName(context));
            bundle.putInt("badgenumber", i);
            context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", (String) null, bundle);
        } catch (Exception unused) {
        }
    }

    private static void setBadgeOfNova(Context context, int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tag", context.getPackageName() + "/" + getLauncherClassName(context));
        contentValues.put("count", Integer.valueOf(i));
        context.getContentResolver().insert(Uri.parse("content://com.teslacoilsw.notifier/unread_count"), contentValues);
    }

    private static void setBadgeOfZuk(Context context, int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("app_badge_count", i);
        context.getContentResolver().call(Uri.parse("content://com.android.badge/badge"), "setAppBadgeCount", (String) null, bundle);
    }

    public static void getBadgeOfMINU(Notification notification, int i) {
        try {
            Object obj = notification.getClass().getDeclaredField("extraNotification").get(notification);
            obj.getClass().getDeclaredMethod("setMessageCount", Integer.TYPE).invoke(obj, Integer.valueOf(i));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setBadgeOfSony(Context context, int i) {
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        boolean z = i != 0;
        Intent intent = new Intent();
        intent.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", z);
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", launcherClassName);
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", String.valueOf(i));
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", context.getPackageName());
        context.sendBroadcast(intent);
    }

    private static void setBadgeOfSumsung(Context context, int i) {
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", i);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }

    private static void setBadgeOfHTC(Context context, int i) {
        Intent intent = new Intent("com.htc.launcher.action.SET_NOTIFICATION");
        intent.putExtra("com.htc.launcher.extra.COMPONENT", new ComponentName(context.getPackageName(), getLauncherClassName(context)).flattenToShortString());
        intent.putExtra("com.htc.launcher.extra.COUNT", i);
        context.sendBroadcast(intent);
        Intent intent2 = new Intent("com.htc.launcher.action.UPDATE_SHORTCUT");
        intent2.putExtra("packagename", context.getPackageName());
        intent2.putExtra("count", i);
        context.sendBroadcast(intent2);
    }

    private static void setVivoBadge(Context context, int i) {
        try {
            String className = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent().getClassName();
            Intent intent = new Intent();
            intent.setAction("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
            intent.putExtra("packageName", context.getPackageName());
            intent.putExtra("className", className);
            intent.putExtra("notificationNum", i);
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void badgerRemoveAll(Context context) {
        if (RomUtils.isHuawei()) {
            setBadgeOfEXUI(context, 0);
            return;
        }
        if (Build.MANUFACTURER.toLowerCase().contains("nova")) {
            setBadgeOfNova(context, 0);
            return;
        }
        if (Build.MANUFACTURER.toLowerCase().contains("zuk")) {
            setBadgeOfZuk(context, 0);
            return;
        }
        if (RomUtils.isVivo()) {
            setVivoBadge(context, 0);
            return;
        }
        if (RomUtils.isSamsung() || RomUtils.isLg()) {
            setBadgeOfSumsung(context, 0);
        } else if (RomUtils.isHtc()) {
            setBadgeOfHTC(context, 0);
        } else if (RomUtils.isSony()) {
            setBadgeOfSony(context, 0);
        }
    }
}
