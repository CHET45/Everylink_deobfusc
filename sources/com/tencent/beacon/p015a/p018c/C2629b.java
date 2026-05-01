package com.tencent.beacon.p015a.p018c;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Process;
import android.text.TextUtils;
import com.azure.xml.implementation.aalto.util.XmlConsts;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.tencent.beacon.base.store.SharedPreferencesC2686a;
import com.tencent.beacon.base.util.C2693a;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.p015a.p017b.AbstractC2616b;
import com.tencent.beacon.p028d.C2710b;
import java.util.List;

/* JADX INFO: renamed from: com.tencent.beacon.a.c.b */
/* JADX INFO: compiled from: AppInfo.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2629b {

    /* JADX INFO: renamed from: a */
    public static String f1069a = null;

    /* JADX INFO: renamed from: b */
    public static int f1070b = 0;

    /* JADX INFO: renamed from: c */
    public static String f1071c = "";

    /* JADX INFO: renamed from: d */
    public static boolean f1072d = false;

    /* JADX INFO: renamed from: e */
    private static String f1073e = null;

    /* JADX INFO: renamed from: f */
    private static boolean f1074f = false;

    /* JADX INFO: renamed from: g */
    private static String f1075g = "";

    /* JADX INFO: renamed from: h */
    private static boolean f1076h = false;

    /* JADX INFO: renamed from: i */
    private static boolean f1077i = false;

    /* JADX INFO: renamed from: j */
    private static int f1078j = -2;

    /* JADX INFO: renamed from: k */
    private static boolean f1079k = true;

    /* JADX INFO: renamed from: a */
    public static String m1042a() {
        if (f1069a == null) {
            f1069a = m1052e();
        }
        return f1069a;
    }

    /* JADX INFO: renamed from: b */
    public static String m1047b() {
        Context contextM1067b = C2630c.m1059c().m1067b();
        if (contextM1067b == null) {
            return null;
        }
        String packageName = contextM1067b.getPackageName();
        return TextUtils.isEmpty(packageName) ? "" : packageName;
    }

    /* JADX INFO: renamed from: c */
    public static String m1049c(Context context) {
        return C2693a.m1430a();
    }

    /* JADX INFO: renamed from: d */
    public static String m1050d() {
        if (!"".equals(f1071c)) {
            return f1071c;
        }
        if (f1070b == 0) {
            f1070b = Process.myPid();
        }
        f1071c += f1070b + PunctuationConst.UNDERLINE;
        String str = f1071c + C2694b.m1454c();
        f1071c = str;
        return str;
    }

    /* JADX INFO: renamed from: e */
    public static synchronized boolean m1053e(Context context) {
        boolean z = false;
        if (context == null) {
            C2695c.m1468b("[appInfo] context is null", new Object[0]);
            return false;
        }
        SharedPreferencesC2686a sharedPreferencesC2686aM1391a = SharedPreferencesC2686a.m1391a();
        String string = sharedPreferencesC2686aM1391a.getString("APPVER_DENGTA", "");
        String strM1042a = m1042a();
        if (string.isEmpty() || !string.equals(strM1042a)) {
            SharedPreferencesC2686a.a aVarEdit = sharedPreferencesC2686aM1391a.edit();
            if (C2694b.m1441a((SharedPreferences.Editor) aVarEdit)) {
                aVarEdit.putString("APPVER_DENGTA", strM1042a);
            }
            z = true;
        }
        return z;
    }

    /* JADX INFO: renamed from: f */
    public static boolean m1055f(Context context) {
        return m1045a(context, context.getPackageName());
    }

    /* JADX INFO: renamed from: g */
    public static synchronized boolean m1056g() {
        boolean z;
        SharedPreferencesC2686a sharedPreferencesC2686aM1391a = SharedPreferencesC2686a.m1391a();
        String string = sharedPreferencesC2686aM1391a.getString("APPKEY_DENGTA", "");
        String strM1072e = C2630c.m1059c().m1072e();
        if (TextUtils.isEmpty(string) || !strM1072e.equals(string)) {
            SharedPreferencesC2686a.a aVarEdit = sharedPreferencesC2686aM1391a.edit();
            if (C2694b.m1441a((SharedPreferences.Editor) aVarEdit)) {
                aVarEdit.putString("APPKEY_DENGTA", strM1072e);
            }
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    /* JADX INFO: renamed from: h */
    private static void m1058h() {
        try {
            SharedPreferencesC2686a sharedPreferencesC2686aM1391a = SharedPreferencesC2686a.m1391a();
            String string = sharedPreferencesC2686aM1391a.getString("APPVER_DENGTA", "");
            String strM1042a = m1042a();
            if (TextUtils.isEmpty(string) || !strM1042a.equals(string)) {
                f1074f = true;
                SharedPreferencesC2686a.a aVarEdit = sharedPreferencesC2686aM1391a.edit();
                if (C2694b.m1441a((SharedPreferences.Editor) aVarEdit)) {
                    aVarEdit.putString("APPVER_DENGTA", strM1042a);
                }
            } else {
                f1074f = false;
            }
        } catch (Exception e) {
            C2695c.m1468b("[core] app version check fail!", new Object[0]);
            C2695c.m1465a(e);
        }
    }

    /* JADX INFO: renamed from: c */
    public static String m1048c() {
        return f1075g;
    }

    /* JADX INFO: renamed from: f */
    public static void m1054f() {
        m1058h();
    }

    /* JADX INFO: renamed from: a */
    public static boolean m1045a(Context context, String str) {
        if (f1077i) {
            return f1079k;
        }
        if (context != null && str != null && str.trim().length() > 0) {
            if (!C2710b.m1518d().m1570x()) {
                C2695c.m1464a("[DeviceInfo] current collect Process Info be refused! isCollect Process Info: %s", false);
                return true;
            }
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
            if (runningAppProcesses != null && runningAppProcesses.size() != 0) {
                for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                    if (runningAppProcessInfo.importance == 100) {
                        for (String str2 : runningAppProcessInfo.pkgList) {
                            if (str.equals(str2)) {
                                f1079k = true;
                                f1077i = true;
                                return true;
                            }
                        }
                    }
                }
                f1079k = false;
                f1077i = true;
                return false;
            }
            C2695c.m1476e("[appInfo] no running proc", new Object[0]);
        }
        return false;
    }

    /* JADX INFO: renamed from: b */
    public static int m1046b(Context context) {
        if (f1076h) {
            return f1078j;
        }
        if (f1070b == 0) {
            f1070b = Process.myPid();
        }
        if (!C2710b.m1518d().m1570x()) {
            C2695c.m1464a("[DeviceInfo] current collect Process Info be refused! isCollect Process Info: %s", false);
            return -2;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        if (activityManager != null && activityManager.getRunningAppProcesses() != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
                if (runningAppProcessInfo.pid == f1070b) {
                    int i = runningAppProcessInfo.importance;
                    f1078j = i;
                    f1076h = true;
                    return i;
                }
            }
        }
        f1078j = 0;
        f1076h = true;
        return 0;
    }

    /* JADX INFO: renamed from: d */
    public static boolean m1051d(Context context) {
        try {
            return (context.getApplicationInfo().flags & 2) != 0;
        } catch (Exception e) {
            C2695c.m1465a(e);
            return false;
        }
    }

    /* JADX INFO: renamed from: g */
    public static boolean m1057g(Context context) {
        if (context == null) {
            return true;
        }
        String strM1049c = m1049c(context);
        return TextUtils.isEmpty(strM1049c) || strM1049c.equals(context.getPackageName());
    }

    /* JADX INFO: renamed from: e */
    public static synchronized String m1052e() {
        String strM1047b = m1047b();
        if (TextUtils.isEmpty(strM1047b)) {
            return null;
        }
        try {
            PackageInfo packageInfo = C2630c.m1059c().m1067b().getPackageManager().getPackageInfo(strM1047b, 0);
            String str = packageInfo.versionName;
            int i = packageInfo.versionCode;
            if (str != null && str.trim().length() > 0) {
                String strReplace = str.trim().replace('\n', ' ').replace(XmlConsts.CHAR_CR, ' ').replace(PunctuationConst.f489OR, "%7C");
                int i2 = 0;
                for (char c : strReplace.toCharArray()) {
                    if (c == '.') {
                        i2++;
                    }
                }
                if (i2 < 3) {
                    C2695c.m1464a("[appInfo] add versionCode: %s", Integer.valueOf(i));
                    strReplace = strReplace + "." + i;
                }
                C2695c.m1464a("[appInfo] final Version: %s", strReplace);
                return strReplace;
            }
            return "" + i;
        } catch (Throwable th) {
            C2695c.m1465a(th);
            C2695c.m1468b(th.toString(), new Object[0]);
            return "";
        }
    }

    /* JADX INFO: renamed from: a */
    public static synchronized String m1043a(Context context) {
        if (TextUtils.isEmpty(f1073e)) {
            String str = "on_app_first_install_time_" + m1049c(context);
            SharedPreferencesC2686a sharedPreferencesC2686aM1391a = SharedPreferencesC2686a.m1391a();
            long jM1454c = sharedPreferencesC2686aM1391a.getLong(str, 0L);
            if (jM1454c == 0) {
                jM1454c = C2694b.m1454c();
                AbstractC2616b.m1001a().mo1011a(new RunnableC2628a(sharedPreferencesC2686aM1391a, str, jM1454c));
            }
            String strValueOf = String.valueOf(jM1454c);
            f1073e = strValueOf;
            C2695c.m1464a("[appInfo] process: %s, getAppFirstInstallTime: %s", str, strValueOf);
        }
        C2695c.m1464a("[appInfo] getAppFirstInstallTime: %s", f1073e);
        return f1073e;
    }

    /* JADX INFO: renamed from: a */
    public static void m1044a(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                if (Long.parseLong(str) > 10000) {
                    f1075g = str;
                    return;
                }
                return;
            } catch (Exception unused) {
                C2695c.m1476e("[appInfo] set qq is not available !", new Object[0]);
                return;
            }
        }
        C2695c.m1476e("[appInfo] set qq is null !", new Object[0]);
    }
}
