package com.aivox.base.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.lzy.okgo.cache.CacheHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class BaseAppUtils {
    static Intent intent;
    private static Context mContext;

    public static int getMainTransType(int i) {
        int i2 = 1;
        if (i != 1 && i != 101 && i != 102 && i != 103) {
            i2 = 2;
            if (i != 2 && i != 104 && i != 107 && i != 105 && i != 106) {
                return 3;
            }
        }
        return i2;
    }

    public static void init(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }

    public static PackageInfo getPackageInfo() throws PackageManager.NameNotFoundException {
        return getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
    }

    public static PackageInfo getPackageInfo(String str) throws Exception {
        return getContext().getPackageManager().getPackageArchiveInfo(str, 1);
    }

    public static void openSettingView(Context context) {
        Intent intent2 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent2.addFlags(268435456);
        intent2.setData(Uri.fromParts("package", context.getPackageName(), null));
        context.startActivity(intent2);
    }

    public static void jump2OpenBT(Context context) {
        context.startActivity(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"));
    }

    public static void jump2OpenWifi(Context context) {
        context.startActivity(new Intent("android.net.wifi.PICK_WIFI_NETWORK"));
    }

    public static boolean isKeyguardLocked(Context context) {
        return ((KeyguardManager) context.getSystemService("keyguard")).isKeyguardLocked();
    }

    public static boolean isContextExisted(Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            return !((Activity) context).isFinishing();
        }
        if (context instanceof Service) {
            return isServiceExisted(context, context.getClass().getName());
        }
        return context instanceof Application;
    }

    public static boolean isServiceExisted(Context context, String str) {
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE);
        if (runningServices.size() <= 0) {
            return false;
        }
        for (int i = 0; i < runningServices.size(); i++) {
            if (runningServices.get(i).service.getClassName().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static String getAppName(Context context) {
        try {
            return context.getResources().getString(getPackageInfo().applicationInfo.labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            printErrorMsg(e);
            return null;
        }
    }

    public static String getVersionName() {
        try {
            return getPackageInfo().versionName;
        } catch (PackageManager.NameNotFoundException e) {
            printErrorMsg(e);
            return "";
        }
    }

    public static int getVersionCode() {
        try {
            return getPackageInfo().versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            printErrorMsg(e);
            return 0;
        }
    }

    public static long getAppVersionCode(String str) {
        try {
            return getPackageInfo(str).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public static String getAppVersionName(String str) {
        try {
            return getPackageInfo(str).versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "0.0.0";
        }
    }

    public static String getAppPackageName(String str) {
        try {
            return getPackageInfo(str).packageName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isAppRunning(Context context) {
        for (ActivityManager.RunningTaskInfo runningTaskInfo : ((ActivityManager) context.getSystemService("activity")).getRunningTasks(100)) {
            if (runningTaskInfo.topActivity.getPackageName().equals(context.getPackageName()) && runningTaskInfo.baseActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAppOnForeground(Context context) {
        String packageName = context.getPackageName();
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.processName.equals(packageName) && runningAppProcessInfo.importance == 100) {
                return true;
            }
        }
        return false;
    }

    public static boolean isActivityInTheForeground(Context context, String str) {
        List<ActivityManager.RunningTaskInfo> runningTasks;
        ComponentName componentName;
        return (context == null || BaseStringUtil.isEmpty(str) || (runningTasks = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(Integer.MAX_VALUE)) == null || runningTasks.size() <= 0 || (componentName = runningTasks.get(0).topActivity) == null || !str.equals(componentName.getClassName())) ? false : true;
    }

    public static boolean isServiceWork(Context context, String str) {
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) context.getSystemService("activity")).getRunningServices(40);
        if (runningServices.size() == 0) {
            return false;
        }
        for (int i = 0; i < runningServices.size(); i++) {
            if (runningServices.get(i).service.getClassName().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLogin() {
        return BaseStringUtil.isNotEmpty((String) SPUtil.get(SPUtil.TOKEN, "")) && BaseStringUtil.isNotEmpty((String) SPUtil.get(SPUtil.REFRESH_TOKEN, ""));
    }

    public static void startActivity(Context context, Class<?> cls) {
        Intent intent2 = new Intent(context, cls);
        intent = intent2;
        context.startActivity(intent2);
    }

    public static void startActivity(Context context, Class<?> cls, Bundle bundle) {
        Intent intent2 = new Intent(context, cls);
        intent = intent2;
        if (bundle != null) {
            intent2.putExtras(bundle);
        }
        intent.addFlags(268435456);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class<?> cls, HashMap<String, Object> map) {
        intent = new Intent(context, cls);
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof Integer) {
                    intent.putExtra(key, (Integer) value);
                } else if (value instanceof String) {
                    intent.putExtra(key, (String) value);
                } else if (value instanceof Boolean) {
                    intent.putExtra(key, (Boolean) value);
                }
            }
        }
        context.startActivity(intent);
    }

    public static void startActivityForWeb(Context context, String str, boolean z, Class<?> cls) {
        Intent intent2 = new Intent(context, cls);
        intent = intent2;
        intent2.putExtra(z ? "url" : "data", str);
        intent.setFlags(276824064);
        context.startActivity(intent);
    }

    public static void startActivityForWeb(Activity activity, String str, Class<?> cls) {
        Intent intent2 = new Intent(activity, cls);
        intent = intent2;
        intent2.putExtra("urlOrPath", str);
        activity.startActivity(intent);
    }

    public static void startActivityForWeb(Activity activity, String str, String str2, Class<?> cls) {
        Intent intent2 = new Intent(activity, cls);
        intent = intent2;
        intent2.putExtra("urlOrPath", str);
        intent.putExtra(Constant.KEY_TITLE, str2);
        activity.startActivity(intent);
    }

    public static void startActivityForResult(Activity activity, Class<?> cls, int i) {
        Intent intent2 = new Intent(activity, cls);
        intent = intent2;
        activity.startActivityForResult(intent2, i);
    }

    public static void startActivityForResult(Activity activity, Class<?> cls, Bundle bundle, int i) {
        Intent intent2 = new Intent(activity, cls);
        intent = intent2;
        if (bundle != null) {
            intent2.putExtras(bundle);
        }
        activity.startActivityForResult(intent, i);
    }

    public static void startBrowser(Context context, String str) {
        Intent intent2 = new Intent();
        intent = intent2;
        intent2.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        context.startActivity(intent);
    }

    public static void goToDesktop(Context context) {
        Intent intent2 = new Intent("android.intent.action.MAIN");
        intent2.addCategory("android.intent.category.HOME");
        intent2.setFlags(536870912);
        context.startActivity(intent2);
    }

    public static boolean isApkInDebug(Context context) {
        try {
            return (context.getApplicationInfo().flags & 2) != 0;
        } catch (Exception e) {
            printErrorMsg(e);
            return false;
        }
    }

    public static void createPinnedShortcuts(Context context, Class<?> cls) {
        ShortcutManager shortcutManager = (ShortcutManager) context.getSystemService(ShortcutManager.class);
        if (shortcutManager != null && shortcutManager.isRequestPinShortcutSupported()) {
            Intent intent2 = new Intent(context, cls);
            intent2.setAction("android.intent.action.VIEW");
            intent2.putExtra(CacheHelper.KEY, "fromPinnedShortcut");
            ShortcutInfo shortcutInfoBuild = new ShortcutInfo.Builder(context, "my-shortcut").setShortLabel("short").setLongLabel("long").setIcon(Icon.createWithResource(context, C0874R.mipmap.ic_dialog_tanhao)).setIntent(intent2).build();
            LogUtil.m338i("set pinned shortcuts " + (shortcutManager.requestPinShortcut(shortcutInfoBuild, PendingIntent.getBroadcast(context, 0, shortcutManager.createShortcutResultIntent(shortcutInfoBuild), 67108864).getIntentSender()) ? "success" : "failed") + PunctuationConst.NOT);
            return;
        }
        LogUtil.m338i("设备是否支持创建快捷方式：" + shortcutManager.isRequestPinShortcutSupported());
    }

    public static void printErrorMsg(Throwable th, String str) {
        LogUtil.m337e("ERROR MSG", str);
        th.printStackTrace();
    }

    public static void printErrorMsg(Throwable th) {
        printErrorMsg(th, "");
    }
}
