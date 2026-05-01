package com.tencent.beacon.p028d;

import android.content.Context;
import android.util.Base64;
import com.tencent.beacon.base.net.p021b.C2668c;
import com.tencent.beacon.base.store.SharedPreferencesC2686a;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.p015a.p017b.AbstractC2616b;
import com.tencent.beacon.p015a.p017b.C2624j;
import com.tencent.beacon.p015a.p018c.C2630c;

/* JADX INFO: renamed from: com.tencent.beacon.d.h */
/* JADX INFO: compiled from: StrategyHolder.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2716h {

    /* JADX INFO: renamed from: a */
    private static volatile C2716h f1489a;

    /* JADX INFO: renamed from: c */
    private InterfaceC2712d f1491c;

    /* JADX INFO: renamed from: i */
    private String f1497i;

    /* JADX INFO: renamed from: b */
    private final String f1490b = "sid";

    /* JADX INFO: renamed from: d */
    private String f1492d = "";

    /* JADX INFO: renamed from: e */
    private boolean f1493e = true;

    /* JADX INFO: renamed from: f */
    private int f1494f = 8081;

    /* JADX INFO: renamed from: g */
    private String f1495g = "";

    /* JADX INFO: renamed from: h */
    private String f1496h = "";

    private C2716h() {
        AbstractC2616b.m1001a().mo1011a(new RunnableC2714f(this));
    }

    /* JADX INFO: renamed from: d */
    public static C2716h m1581d() {
        if (f1489a == null) {
            synchronized (C2716h.class) {
                if (f1489a == null) {
                    f1489a = new C2716h();
                }
            }
        }
        return f1489a;
    }

    /* JADX INFO: renamed from: a */
    public void m1583a(InterfaceC2712d interfaceC2712d) {
        this.f1491c = interfaceC2712d;
    }

    /* JADX INFO: renamed from: b */
    public synchronized void m1586b(String str) {
        this.f1492d = str;
    }

    /* JADX INFO: renamed from: c */
    public synchronized void m1587c() {
        m1584a(SharedPreferencesC2686a.m1391a().getString("ias_cookie", ""));
        m1591g();
    }

    /* JADX INFO: renamed from: e */
    public synchronized String m1589e() {
        return this.f1492d;
    }

    /* JADX INFO: renamed from: f */
    public synchronized String m1590f() {
        return this.f1495g;
    }

    /* JADX INFO: renamed from: g */
    public synchronized void m1591g() {
        Context contextM1067b = C2630c.m1059c().m1067b();
        if (contextM1067b == null) {
            return;
        }
        String strM1448b = C2694b.m1448b();
        this.f1496h = strM1448b;
        byte[] bArrM1328a = C2668c.m1328a(contextM1067b, strM1448b);
        if (bArrM1328a != null) {
            this.f1495g = Base64.encodeToString(bArrM1328a, 2);
        } else {
            C2695c.m1468b("rsaEncryKey is null.", new Object[0]);
            C2624j.m1031e().m1023a("506", "rsaEncryKey is null.");
        }
    }

    /* JADX INFO: renamed from: a */
    public synchronized String m1582a() {
        return this.f1496h;
    }

    /* JADX INFO: renamed from: b */
    public String m1585b() {
        return this.f1497i;
    }

    /* JADX INFO: renamed from: a */
    public void m1584a(String str) {
        this.f1497i = str;
    }

    /* JADX INFO: renamed from: c */
    public void m1588c(String str) {
        if (str == null || str.equals(this.f1497i)) {
            return;
        }
        this.f1497i = str;
        AbstractC2616b.m1001a().mo1011a(new RunnableC2715g(this, str));
    }
}
