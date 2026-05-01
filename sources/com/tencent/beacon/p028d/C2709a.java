package com.tencent.beacon.p028d;

import com.tencent.beacon.base.util.C2695c;
import java.util.Calendar;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.beacon.d.a */
/* JADX INFO: compiled from: BeaconStrategy.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2709a implements InterfaceC2712d {

    /* JADX INFO: renamed from: a */
    private static volatile C2709a f1422a;

    /* JADX INFO: renamed from: b */
    public boolean f1423b = false;

    /* JADX INFO: renamed from: c */
    private String f1424c = "oth.str.mdt.qq.com";

    /* JADX INFO: renamed from: d */
    private int f1425d = 360;

    /* JADX INFO: renamed from: e */
    private int f1426e = 100;

    /* JADX INFO: renamed from: f */
    private Map<String, String> f1427f = null;

    /* JADX INFO: renamed from: g */
    private boolean f1428g = false;

    /* JADX INFO: renamed from: h */
    private C2713e f1429h = new C2713e(1);

    private C2709a() {
    }

    /* JADX INFO: renamed from: a */
    public static C2709a m1508a() {
        if (f1422a == null) {
            synchronized (C2709a.class) {
                if (f1422a == null) {
                    f1422a = new C2709a();
                }
            }
        }
        return f1422a;
    }

    /* JADX INFO: renamed from: b */
    public int m1512b() {
        return this.f1425d;
    }

    /* JADX INFO: renamed from: c */
    public synchronized int m1513c() {
        String str;
        Map<String, String> map = this.f1427f;
        if (map == null || (str = map.get("maxStrategyQueryOneDay")) == null || str.trim().equals("")) {
            return this.f1426e;
        }
        int i = this.f1426e;
        try {
            i = Integer.parseInt(str);
        } catch (Exception e) {
            C2695c.m1465a(e);
        }
        return i;
    }

    /* JADX INFO: renamed from: d */
    public C2713e m1514d() {
        return this.f1429h;
    }

    /* JADX INFO: renamed from: e */
    public synchronized boolean m1515e() {
        Map<String, String> map = this.f1427f;
        if (map == null || !"y".equalsIgnoreCase(map.get("zeroPeak"))) {
            return false;
        }
        return Calendar.getInstance().get(11) == 0;
    }

    /* JADX INFO: renamed from: a */
    public void m1510a(int i) {
        this.f1425d = i;
    }

    /* JADX INFO: renamed from: a */
    public synchronized void m1511a(Map<String, String> map) {
        this.f1427f = map;
    }

    /* JADX INFO: renamed from: a */
    public String m1509a(String str) {
        Map<String, String> mapM1574a;
        C2713e c2713e = this.f1429h;
        if (c2713e == null || (mapM1574a = c2713e.m1574a()) == null) {
            return null;
        }
        return mapM1574a.get(str);
    }
}
