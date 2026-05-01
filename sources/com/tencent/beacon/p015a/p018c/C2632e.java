package com.tencent.beacon.p015a.p018c;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.aivox.base.common.Constant;
import com.github.houbb.heaven.util.lang.BoolUtil;
import com.jieli.jl_bt_ota.constant.Command;
import com.tencent.beacon.base.net.p021b.C2669d;
import com.tencent.beacon.base.net.p021b.RunnableC2670e;
import com.tencent.beacon.base.store.SharedPreferencesC2686a;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.base.util.C2699g;
import com.tencent.beacon.p028d.C2710b;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Locale;

/* JADX INFO: renamed from: com.tencent.beacon.a.c.e */
/* JADX INFO: compiled from: DeviceInfo.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2632e implements RunnableC2670e.a {

    /* JADX INFO: renamed from: a */
    private static volatile C2632e f1094a;

    /* JADX INFO: renamed from: b */
    private String f1095b = "";

    /* JADX INFO: renamed from: c */
    private String f1096c = "";

    /* JADX INFO: renamed from: d */
    private String f1097d = "";

    /* JADX INFO: renamed from: e */
    private String f1098e = "";

    /* JADX INFO: renamed from: f */
    private String f1099f = "";

    /* JADX INFO: renamed from: g */
    private boolean f1100g = true;

    /* JADX INFO: renamed from: h */
    private String f1101h = "";

    private C2632e() {
    }

    /* JADX INFO: renamed from: G */
    private void m1079G() {
        m1080H();
        this.f1100g = C2669d.m1344d();
        this.f1101h = C2669d.m1341c();
    }

    /* JADX INFO: renamed from: H */
    private void m1080H() {
        this.f1098e = m1081I();
    }

    /* JADX INFO: renamed from: I */
    private String m1081I() {
        NetworkInfo activeNetworkInfo;
        TelephonyManager telephonyManager;
        Context contextM1067b = C2630c.m1059c().m1067b();
        if (contextM1067b == null || !C2710b.m1518d().m1552h()) {
            return "";
        }
        String str = "unknown";
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) contextM1067b.getSystemService("connectivity");
            if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null) {
                return "unknown";
            }
            if (activeNetworkInfo.getType() == 1) {
                str = "wifi";
            } else if (activeNetworkInfo.getType() == 0 && (telephonyManager = (TelephonyManager) contextM1067b.getSystemService(Constant.KEY_PHONE)) != null) {
                int networkType = telephonyManager.getNetworkType();
                if (networkType != 20) {
                    switch (networkType) {
                        case 1:
                        case 2:
                        case 4:
                        case 7:
                        case 11:
                            str = "2G";
                            break;
                        case 3:
                        case 5:
                        case 6:
                        case 8:
                        case 9:
                        case 10:
                        case 12:
                        case 14:
                        case 15:
                            str = "3G";
                            break;
                        case 13:
                            str = "4G";
                            break;
                        default:
                            str = "unknown_" + telephonyManager.getNetworkType();
                            break;
                    }
                } else {
                    str = "5G";
                }
            }
        } catch (Exception e) {
            C2695c.m1465a(e);
        }
        C2695c.m1464a("[DeviceInfo] NetWork Type:" + str, new Object[0]);
        return str;
    }

    /* JADX INFO: renamed from: l */
    public static C2632e m1082l() {
        if (f1094a == null) {
            synchronized (C2632e.class) {
                if (f1094a == null) {
                    f1094a = new C2632e();
                }
            }
        }
        return f1094a;
    }

    /* JADX INFO: renamed from: A */
    public String m1083A() {
        return "";
    }

    /* JADX INFO: renamed from: B */
    public String m1084B() {
        return "";
    }

    /* JADX INFO: renamed from: C */
    public void m1085C() {
        Context contextM1067b = C2630c.m1059c().m1067b();
        if (contextM1067b == null) {
            return;
        }
        RunnableC2670e.m1348a(contextM1067b, this);
        m1079G();
        m1086D();
    }

    /* JADX INFO: renamed from: D */
    public void m1086D() {
        String string = SharedPreferencesC2686a.m1391a().getString("BEACON_ANDROID_ID_DENGTA", "");
        this.f1095b = string;
        if (TextUtils.isEmpty(string)) {
            this.f1095b = C2694b.m1434a();
            SharedPreferencesC2686a.a aVarEdit = SharedPreferencesC2686a.m1391a().edit();
            if (C2694b.m1441a((SharedPreferences.Editor) aVarEdit)) {
                aVarEdit.putString("BEACON_ANDROID_ID_DENGTA", this.f1095b);
            }
        }
    }

    /* JADX INFO: renamed from: E */
    public boolean m1087E() {
        BufferedReader bufferedReader;
        boolean z;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(new String[]{"/system/bin/cat", "/proc/cpuinfo"}).getInputStream(), Charset.defaultCharset()));
            while (true) {
                try {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        z = false;
                        break;
                    }
                    if (-1 != line.toLowerCase().indexOf("armv7")) {
                        z = true;
                        break;
                    }
                } catch (Throwable th) {
                    th = th;
                    try {
                        C2695c.m1465a(th);
                        C2694b.m1438a(bufferedReader);
                        return false;
                    } catch (Throwable th2) {
                        C2694b.m1438a(bufferedReader);
                        throw th2;
                    }
                }
            }
            C2694b.m1438a(bufferedReader);
            return z;
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
        }
    }

    /* JADX INFO: renamed from: F */
    public String m1088F() {
        if (TextUtils.isEmpty(this.f1099f)) {
            try {
                Class<?> cls = Class.forName("com.huawei.system.BuildEx");
                String str = "harmony".equals(cls.getMethod("getOsBrand", new Class[0]).invoke(cls, new Object[0])) ? BoolUtil.f541Y : "N";
                this.f1099f = str;
                return str;
            } catch (Throwable unused) {
                this.f1099f = "N";
            }
        }
        return this.f1099f;
    }

    /* JADX INFO: renamed from: a */
    public String m1089a(Context context) {
        try {
            if (Integer.parseInt(Build.VERSION.SDK) < 9) {
                C2695c.m1468b("Api level < 9;return null!", new Object[0]);
                return "";
            }
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            C2695c.m1464a("get app_last_updated_time:" + String.valueOf(packageInfo.lastUpdateTime), new Object[0]);
            return String.valueOf(packageInfo.lastUpdateTime);
        } catch (Throwable th) {
            C2695c.m1465a(th);
            C2695c.m1468b("get app_last_updated_time failed!", new Object[0]);
            return "";
        }
    }

    @Override // com.tencent.beacon.base.net.p021b.RunnableC2670e.a
    /* JADX INFO: renamed from: b */
    public void mo1091b() {
        m1079G();
    }

    /* JADX INFO: renamed from: c */
    public boolean m1092c() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    /* JADX INFO: renamed from: d */
    public String m1093d() {
        return this.f1095b;
    }

    /* JADX INFO: renamed from: e */
    public int m1094e() {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(new String[]{"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"}).getInputStream(), Charset.forName("UTF-8")));
        } catch (Throwable th) {
            th = th;
            bufferedReader = null;
        }
        try {
            String line = bufferedReader.readLine();
            int i = line != null ? Integer.parseInt(line.trim()) / 1000 : 0;
            C2694b.m1438a(bufferedReader);
            return i;
        } catch (Throwable th2) {
            th = th2;
            try {
                C2695c.m1465a(th);
                C2694b.m1438a(bufferedReader);
                return 0;
            } catch (Throwable th3) {
                C2694b.m1438a(bufferedReader);
                throw th3;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x003b, code lost:
    
        r0 = r1.substring(r1.indexOf(":") + 1).trim();
     */
    /* JADX INFO: renamed from: f */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String m1095f() {
        /*
            r7 = this;
            java.lang.String r0 = ""
            r1 = 2
            java.lang.String[] r1 = new java.lang.String[r1]
            java.lang.String r2 = "/system/bin/cat"
            r3 = 0
            r1[r3] = r2
            java.lang.String r2 = "/proc/cpuinfo"
            r4 = 1
            r1[r4] = r2
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Throwable -> L52
            java.lang.Process r1 = r2.exec(r1)     // Catch: java.lang.Throwable -> L52
            java.io.InputStream r1 = r1.getInputStream()     // Catch: java.lang.Throwable -> L52
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L52
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L52
            java.lang.String r6 = "UTF-8"
            java.nio.charset.Charset r6 = java.nio.charset.Charset.forName(r6)     // Catch: java.lang.Throwable -> L52
            r5.<init>(r1, r6)     // Catch: java.lang.Throwable -> L52
            r2.<init>(r5)     // Catch: java.lang.Throwable -> L52
        L2b:
            java.lang.String r1 = r2.readLine()     // Catch: java.lang.Throwable -> L50
            if (r1 == 0) goto L48
            java.lang.String r5 = "Processor"
            boolean r5 = r1.contains(r5)     // Catch: java.lang.Throwable -> L50
            if (r5 == 0) goto L2b
            java.lang.String r5 = ":"
            int r5 = r1.indexOf(r5)     // Catch: java.lang.Throwable -> L50
            int r5 = r5 + r4
            java.lang.String r1 = r1.substring(r5)     // Catch: java.lang.Throwable -> L50
            java.lang.String r0 = r1.trim()     // Catch: java.lang.Throwable -> L50
        L48:
            java.io.Closeable[] r1 = new java.io.Closeable[r4]
            r1[r3] = r2
            com.tencent.beacon.base.util.C2694b.m1438a(r1)
            goto L5e
        L50:
            r1 = move-exception
            goto L54
        L52:
            r1 = move-exception
            r2 = 0
        L54:
            com.tencent.beacon.base.util.C2695c.m1465a(r1)     // Catch: java.lang.Throwable -> L5f
            java.io.Closeable[] r1 = new java.io.Closeable[r4]
            r1[r3] = r2
            com.tencent.beacon.base.util.C2694b.m1438a(r1)
        L5e:
            return r0
        L5f:
            r0 = move-exception
            java.io.Closeable[] r1 = new java.io.Closeable[r4]
            r1[r3] = r2
            com.tencent.beacon.base.util.C2694b.m1438a(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.beacon.p015a.p018c.C2632e.m1095f():java.lang.String");
    }

    /* JADX INFO: renamed from: g */
    public String m1096g() {
        return Locale.getDefault().getCountry();
    }

    /* JADX INFO: renamed from: h */
    public String m1097h() {
        return Build.HARDWARE;
    }

    /* JADX INFO: renamed from: i */
    public DisplayMetrics m1098i() {
        Context contextM1067b = C2630c.m1059c().m1067b();
        if (contextM1067b == null) {
            return null;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) contextM1067b.getSystemService("window");
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics;
    }

    /* JADX INFO: renamed from: j */
    public long m1099j() {
        try {
            Context contextM1067b = C2630c.m1059c().m1067b();
            if (contextM1067b == null) {
                return -1L;
            }
            ActivityManager activityManager = (ActivityManager) contextM1067b.getSystemService("activity");
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            if (activityManager != null) {
                activityManager.getMemoryInfo(memoryInfo);
                return memoryInfo.availMem;
            }
        } catch (Exception e) {
            C2695c.m1465a(e);
        }
        return -1L;
    }

    /* JADX INFO: renamed from: k */
    public String m1100k() {
        long jM1099j = m1099j();
        return jM1099j > 0 ? ((jM1099j / 1024) / 1024) + "" : "0";
    }

    /* JADX INFO: renamed from: m */
    public boolean m1101m() {
        return C2699g.m1483d();
    }

    /* JADX INFO: renamed from: n */
    public String m1102n() {
        return Locale.getDefault().getLanguage();
    }

    /* JADX INFO: renamed from: o */
    public String m1103o() {
        return Build.MANUFACTURER;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: PHI empty after try-catch fix!
        	at jadx.core.dex.visitors.ssa.SSATransform.fixPhiInTryCatch(SSATransform.java:222)
        	at jadx.core.dex.visitors.ssa.SSATransform.fixLastAssignInTry(SSATransform.java:202)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:58)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:44)
        */
    /* JADX INFO: renamed from: p */
    public java.lang.String m1104p() {
        /*
            Method dump skipped, instruction units count: 235
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.beacon.p015a.p018c.C2632e.m1104p():java.lang.String");
    }

    /* JADX INFO: renamed from: q */
    public String m1105q() {
        return this.f1098e;
    }

    /* JADX INFO: renamed from: r */
    public String m1106r() {
        return this.f1101h;
    }

    /* JADX INFO: renamed from: s */
    public int m1107s() {
        try {
            File[] fileArrListFiles = new File("/sys/devices/system/cpu/").listFiles(new C2631d(this));
            if (fileArrListFiles == null) {
                return 1;
            }
            return fileArrListFiles.length;
        } catch (Exception e) {
            C2695c.m1468b("[model] CPU Count: Failed.", new Object[0]);
            C2695c.m1465a(e);
            return 1;
        }
    }

    /* JADX INFO: renamed from: t */
    public String m1108t() {
        if (!TextUtils.isEmpty(this.f1096c)) {
            return this.f1096c;
        }
        String str = "Android " + Build.VERSION.RELEASE + ",level " + Build.VERSION.SDK;
        this.f1096c = str;
        C2695c.m1464a("[DeviceInfo] os version: %s", str);
        return this.f1096c;
    }

    /* JADX INFO: renamed from: u */
    public String m1109u() {
        BufferedReader bufferedReader;
        FileReader fileReader;
        try {
            fileReader = new FileReader("/proc/meminfo");
            try {
                bufferedReader = new BufferedReader(fileReader, 8192);
            } catch (Throwable th) {
                th = th;
                bufferedReader = null;
            }
            try {
                String line = bufferedReader.readLine();
                if (line != null) {
                    String str = (Long.parseLong(line.split(":\\s+", 2)[1].toLowerCase().replace("kb", "").trim()) / 1024) + "";
                    try {
                        bufferedReader.close();
                        fileReader.close();
                    } catch (Throwable th2) {
                        C2695c.m1468b("[model] IO close error!", new Object[0]);
                        C2695c.m1465a(th2);
                    }
                    return str;
                }
                try {
                    bufferedReader.close();
                    fileReader.close();
                } catch (Throwable th3) {
                    C2695c.m1468b("[model] IO close error!", new Object[0]);
                    C2695c.m1465a(th3);
                }
            } catch (Throwable th4) {
                th = th4;
                try {
                    C2695c.m1468b("[model] get free RAM error!", new Object[0]);
                    C2695c.m1465a(th);
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (Throwable th5) {
                            C2695c.m1468b("[model] IO close error!", new Object[0]);
                            C2695c.m1465a(th5);
                        }
                    }
                    if (fileReader != null) {
                        fileReader.close();
                    }
                } catch (Throwable th6) {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (Throwable th7) {
                            C2695c.m1468b("[model] IO close error!", new Object[0]);
                            C2695c.m1465a(th7);
                            throw th6;
                        }
                    }
                    if (fileReader != null) {
                        fileReader.close();
                    }
                    throw th6;
                }
            }
        } catch (Throwable th8) {
            th = th8;
            bufferedReader = null;
            fileReader = null;
        }
        return null;
    }

    /* JADX INFO: renamed from: v */
    public String m1110v() {
        DisplayMetrics displayMetricsM1098i = m1098i();
        return displayMetricsM1098i == null ? "" : displayMetricsM1098i.widthPixels + "*" + displayMetricsM1098i.heightPixels;
    }

    /* JADX INFO: renamed from: w */
    public String m1111w() {
        ArrayList<String> arrayListM1436a = C2694b.m1436a(new String[]{"/system/bin/sh", "-c", "getprop ro.build.fingerprint"});
        return (arrayListM1436a == null || arrayListM1436a.size() <= 0) ? "" : arrayListM1436a.get(0);
    }

    /* JADX INFO: renamed from: x */
    public String m1112x() {
        if (!TextUtils.isEmpty(this.f1097d)) {
            return this.f1097d;
        }
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        this.f1097d = (((((long) statFs.getBlockCount()) * statFs.getBlockSize()) / 1024) / 1024) + "";
        C2695c.m1464a("[DeviceInfo] Rom Size:" + this.f1097d, new Object[0]);
        return this.f1097d;
    }

    /* JADX INFO: renamed from: y */
    public long m1113y() {
        if (!m1092c()) {
            return 0L;
        }
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return ((((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / 1024) / 1024;
        } catch (Throwable th) {
            C2695c.m1465a(th);
            return 0L;
        }
    }

    /* JADX INFO: renamed from: z */
    public int m1114z() {
        Context contextM1067b = C2630c.m1059c().m1067b();
        if (contextM1067b == null) {
            return -1;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) contextM1067b.getSystemService("window");
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            if (C2710b.m1518d().m1522C()) {
                return displayMetrics.densityDpi;
            }
            double d = displayMetrics.density;
            if (d - 0.75d <= 1.0E-6d) {
                return 120;
            }
            if (d - 1.5d <= 1.0E-6d) {
                return Command.CMD_CUSTOM;
            }
            if (d - 2.0d <= 1.0E-6d) {
                return Constant.EVENT.BLE_SHOW_CONNECT_DIALOG;
            }
            if (d - 3.0d <= 1.0E-6d) {
                return 480;
            }
        }
        return 160;
    }

    @Override // com.tencent.beacon.base.net.p021b.RunnableC2670e.a
    /* JADX INFO: renamed from: a */
    public void mo1090a() {
        m1079G();
    }
}
