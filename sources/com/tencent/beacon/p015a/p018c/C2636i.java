package com.tencent.beacon.p015a.p018c;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.p015a.p016a.C2612b;
import com.tencent.beacon.p015a.p016a.C2613c;
import com.tencent.beacon.p015a.p017b.C2624j;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.beacon.a.c.i */
/* JADX INFO: compiled from: QimeiWrapper.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2636i {

    /* JADX INFO: renamed from: a */
    private static C2635h f1115a = new C2635h();

    /* JADX INFO: renamed from: b */
    private static String f1116b = "";

    /* JADX INFO: renamed from: c */
    private static boolean f1117c = false;

    /* JADX INFO: renamed from: a */
    public static C2635h m1141a(Context context, String str) {
        return null;
    }

    /* JADX INFO: renamed from: a */
    public static void m1143a() {
    }

    /* JADX INFO: renamed from: a */
    public static synchronized void m1145a(String str, String str2) {
        if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
            C2695c.m1464a("qimei can not null!", new Object[0]);
            return;
        }
        if (!TextUtils.isEmpty(m1147b()) && !TextUtils.equals(m1147b(), str)) {
            String str3 = String.format("qimei16 changed! qimei16 = %s", str);
            C2695c.m1464a(str3, new Object[0]);
            C2624j.m1031e().m1023a("1102", str3);
        }
        if (!TextUtils.isEmpty(m1149c()) && !TextUtils.equals(m1149c(), str2)) {
            String str4 = String.format("qimei36 changed! qimei36 = %s", str2);
            C2695c.m1464a(str4, new Object[0]);
            C2624j.m1031e().m1023a("1103", str4);
        }
        C2695c.m1464a("setQimei: qimei16 = %s, qimei36 = %s", str, str2);
        if (f1115a.m1140c() && (!TextUtils.isEmpty(str) || !TextUtils.isEmpty(str2))) {
            C2612b.m991a().m998a(new C2613c(1));
        }
        f1115a.m1137a(str);
        f1115a.m1139b(str2);
        if (!f1117c && !TextUtils.isEmpty(m1147b())) {
            C2612b.m991a().m998a(new C2613c(13));
            f1117c = true;
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m1146a(boolean z) {
    }

    /* JADX INFO: renamed from: b */
    public static synchronized String m1147b() {
        return f1115a.m1136a();
    }

    /* JADX INFO: renamed from: c */
    public static synchronized String m1149c() {
        return f1115a.m1138b();
    }

    /* JADX INFO: renamed from: d */
    public static synchronized Map<String, String> m1150d() {
        HashMap map;
        map = new HashMap(2);
        map.put("A3", m1147b());
        map.put("A153", m1149c());
        return map;
    }

    /* JADX INFO: renamed from: e */
    public static synchronized String m1151e() {
        return f1116b;
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* JADX INFO: renamed from: f */
    public static synchronized String m1152f() {
        return "";
    }

    /* JADX INFO: renamed from: g */
    public static void m1153g() {
    }

    /* JADX INFO: renamed from: b */
    public static synchronized void m1148b(String str) {
        f1116b = str;
    }

    /* JADX INFO: renamed from: a */
    public static C2635h m1142a(String str) {
        return f1115a;
    }

    /* JADX INFO: renamed from: a */
    public static void m1144a(String str, Context context, InterfaceC2633f interfaceC2633f) {
        C2695c.m1476e("外部版该接口无效", new Object[0]);
    }
}
