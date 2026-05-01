package com.tencent.beacon.base.util;

import android.util.Log;
import com.github.houbb.heaven.constant.PunctuationConst;
import java.util.Locale;

/* JADX INFO: renamed from: com.tencent.beacon.base.util.c */
/* JADX INFO: compiled from: ELog.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2695c {

    /* JADX INFO: renamed from: a */
    public static boolean f1393a = true;

    /* JADX INFO: renamed from: b */
    private static BeaconLogger f1394b = null;

    /* JADX INFO: renamed from: c */
    private static boolean f1395c = false;

    private C2695c() {
    }

    /* JADX INFO: renamed from: a */
    public static void m1460a(BeaconLogger beaconLogger) {
        f1394b = beaconLogger;
    }

    /* JADX INFO: renamed from: b */
    public static synchronized boolean m1470b() {
        return f1393a;
    }

    /* JADX INFO: renamed from: c */
    private static boolean m1472c() {
        return m1467a();
    }

    /* JADX INFO: renamed from: d */
    public static void m1474d(String str, Object... objArr) {
        if (m1472c()) {
            BeaconLogger beaconLogger = f1394b;
            if (beaconLogger == null) {
                Log.i("beacon", m1471c(str, objArr));
            } else {
                beaconLogger.m1427i("beacon", m1471c(str, objArr));
            }
        }
    }

    /* JADX INFO: renamed from: e */
    public static void m1476e(String str, Object... objArr) {
        if (m1472c()) {
            BeaconLogger beaconLogger = f1394b;
            if (beaconLogger == null) {
                Log.w("beacon", m1471c(str, objArr));
            } else {
                beaconLogger.m1429w("beacon", m1471c(str, objArr));
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public static synchronized boolean m1467a() {
        return f1395c;
    }

    /* JADX INFO: renamed from: b */
    public static synchronized void m1469b(boolean z) {
        f1393a = z;
    }

    /* JADX INFO: renamed from: c */
    public static String m1471c(String str, Object... objArr) {
        String strM1475e = m1475e();
        return str == null ? strM1475e + "msg is null" : (objArr == null || objArr.length == 0) ? strM1475e + str : strM1475e + String.format(Locale.US, str, objArr);
    }

    /* JADX INFO: renamed from: a */
    public static synchronized void m1466a(boolean z) {
        Log.i("beacon", "beacon logAble: " + z);
        f1395c = z;
    }

    /* JADX INFO: renamed from: b */
    public static void m1468b(String str, Object... objArr) {
        if (m1472c()) {
            BeaconLogger beaconLogger = f1394b;
            if (beaconLogger == null) {
                Log.e("beacon", m1471c(str, objArr));
            } else {
                beaconLogger.m1426e("beacon", m1471c(str, objArr));
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m1464a(String str, Object... objArr) {
        if (m1472c()) {
            BeaconLogger beaconLogger = f1394b;
            if (beaconLogger == null) {
                Log.d("beacon", m1471c(str, objArr));
            } else {
                beaconLogger.m1425d("beacon", m1471c(str, objArr));
            }
        }
    }

    /* JADX INFO: renamed from: d */
    private static StackTraceElement m1473d() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int iM1459a = m1459a(stackTrace, C2695c.class);
        if (iM1459a == -1 && (iM1459a = m1459a(stackTrace, Log.class)) == -1) {
            return null;
        }
        return stackTrace[iM1459a];
    }

    /* JADX INFO: renamed from: e */
    private static String m1475e() {
        StackTraceElement stackTraceElementM1473d;
        if (!m1470b() || (stackTraceElementM1473d = m1473d()) == null) {
            return "";
        }
        String fileName = stackTraceElementM1473d.getFileName();
        String str = fileName != null ? fileName : "";
        String methodName = stackTraceElementM1473d.getMethodName();
        if (methodName.contains(PunctuationConst.DOLLAR)) {
            methodName = methodName.substring(methodName.indexOf(PunctuationConst.DOLLAR) + 1, methodName.lastIndexOf(PunctuationConst.DOLLAR) - 2);
        }
        return "(" + str + ":" + stackTraceElementM1473d.getLineNumber() + ")" + methodName + " ";
    }

    /* JADX INFO: renamed from: a */
    public static void m1463a(String str, String str2, Object... objArr) {
        if (m1472c()) {
            BeaconLogger beaconLogger = f1394b;
            if (beaconLogger == null) {
                Log.d("beacon", m1471c(str + " " + str2, objArr));
            } else {
                beaconLogger.m1425d("beacon", m1471c(str + " " + str2, objArr));
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m1461a(String str, int i, String str2, Object... objArr) {
        if (m1472c()) {
            BeaconLogger beaconLogger = f1394b;
            if (beaconLogger == null) {
                Log.d("beacon", m1471c(str + " step: " + i + ". " + str2, objArr));
            } else {
                beaconLogger.m1425d("beacon", m1471c(str + " step: " + i + ". " + str2, objArr));
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m1465a(Throwable th) {
        if (th == null || !m1472c()) {
            return;
        }
        BeaconLogger beaconLogger = f1394b;
        if (beaconLogger == null) {
            th.printStackTrace();
        } else {
            beaconLogger.printStackTrace(th);
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m1462a(String str, String str2) {
        Log.e("beacon", String.format("BeaconSDK init success! appkey is: %s, packageName is: %s ", str, str2));
    }

    /* JADX INFO: renamed from: a */
    private static int m1459a(StackTraceElement[] stackTraceElementArr, Class cls) {
        for (int i = 5; i < stackTraceElementArr.length; i++) {
            String className = stackTraceElementArr[i].getClassName();
            if (!(cls.equals(Log.class) && i < stackTraceElementArr.length - 1 && stackTraceElementArr[i + 1].getClassName().equals(Log.class.getName())) && className.equals(cls.getName())) {
                return i + 1;
            }
        }
        return -1;
    }
}
