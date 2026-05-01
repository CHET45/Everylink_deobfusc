package com.tencent.beacon.base.util;

/* JADX INFO: renamed from: com.tencent.beacon.base.util.a */
/* JADX INFO: compiled from: CloseUnitTestUtils.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2693a {

    /* JADX INFO: renamed from: a */
    private static String f1389a;

    /* JADX INFO: renamed from: a */
    public static String m1430a() {
        String str = f1389a;
        if (str != null) {
            return str;
        }
        try {
            f1389a = (String) Class.forName("android.app.ActivityThread").getDeclaredMethod("currentProcessName", new Class[0]).invoke(null, new Object[0]);
            return "";
        } catch (Throwable th) {
            C2695c.m1465a(th);
            return "";
        }
    }
}
