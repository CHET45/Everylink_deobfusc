package com.tencent.beacon.p028d;

import com.aivox.base.common.Constant;
import com.blankj.utilcode.constant.TimeConstants;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.tencent.beacon.base.net.p021b.C2667b;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.p015a.p016a.C2612b;
import com.tencent.beacon.p015a.p016a.C2613c;
import com.tencent.beacon.p015a.p016a.InterfaceC2614d;
import com.tencent.beacon.p015a.p018c.C2632e;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/* JADX INFO: renamed from: com.tencent.beacon.d.b */
/* JADX INFO: compiled from: DefaultStrategy.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2710b implements InterfaceC2614d {

    /* JADX INFO: renamed from: a */
    private static volatile C2710b f1430a;

    /* JADX INFO: renamed from: b */
    private static final Random f1431b = new Random();

    /* JADX INFO: renamed from: c */
    private static final Map<String, Map<String, Integer>> f1432c = new HashMap();

    /* JADX INFO: renamed from: i */
    protected Boolean f1462i;

    /* JADX INFO: renamed from: k */
    protected InterfaceC2711c f1464k;

    /* JADX INFO: renamed from: d */
    protected int f1457d = 48;

    /* JADX INFO: renamed from: e */
    protected int f1458e = 2000;

    /* JADX INFO: renamed from: f */
    protected int f1459f = 48;

    /* JADX INFO: renamed from: g */
    protected int f1460g = 5000;

    /* JADX INFO: renamed from: h */
    protected int f1461h = 60000;

    /* JADX INFO: renamed from: j */
    protected boolean f1463j = true;

    /* JADX INFO: renamed from: l */
    protected boolean f1465l = true;

    /* JADX INFO: renamed from: m */
    protected boolean f1466m = true;

    /* JADX INFO: renamed from: n */
    protected Set<String> f1467n = null;

    /* JADX INFO: renamed from: o */
    protected Map<String, Float> f1468o = null;

    /* JADX INFO: renamed from: p */
    protected boolean f1469p = false;

    /* JADX INFO: renamed from: q */
    protected boolean f1470q = false;

    /* JADX INFO: renamed from: r */
    protected boolean f1471r = false;

    /* JADX INFO: renamed from: s */
    protected float f1472s = 1.0f;

    /* JADX INFO: renamed from: t */
    protected boolean f1473t = false;

    /* JADX INFO: renamed from: u */
    protected boolean f1474u = false;

    /* JADX INFO: renamed from: v */
    protected boolean f1475v = false;

    /* JADX INFO: renamed from: w */
    protected int f1476w = 1;

    /* JADX INFO: renamed from: x */
    protected long f1477x = 6400;

    /* JADX INFO: renamed from: y */
    protected int f1478y = 20;

    /* JADX INFO: renamed from: z */
    protected int f1479z = Constant.EVENT.BLE_CONNECTED;

    /* JADX INFO: renamed from: A */
    protected boolean f1433A = true;

    /* JADX INFO: renamed from: B */
    protected boolean f1434B = true;

    /* JADX INFO: renamed from: C */
    protected boolean f1435C = true;

    /* JADX INFO: renamed from: D */
    protected boolean f1436D = true;

    /* JADX INFO: renamed from: E */
    protected boolean f1437E = true;

    /* JADX INFO: renamed from: F */
    protected boolean f1438F = true;

    /* JADX INFO: renamed from: G */
    protected boolean f1439G = true;

    /* JADX INFO: renamed from: H */
    protected int f1440H = 10000;

    /* JADX INFO: renamed from: I */
    protected boolean f1441I = true;

    /* JADX INFO: renamed from: J */
    protected boolean f1442J = true;

    /* JADX INFO: renamed from: K */
    protected boolean f1443K = true;

    /* JADX INFO: renamed from: L */
    protected boolean f1444L = false;

    /* JADX INFO: renamed from: M */
    protected int f1445M = 1;

    /* JADX INFO: renamed from: N */
    private boolean f1446N = false;

    /* JADX INFO: renamed from: O */
    protected int f1447O = 10;

    /* JADX INFO: renamed from: P */
    private int f1448P = 2;

    /* JADX INFO: renamed from: Q */
    private int f1449Q = 0;

    /* JADX INFO: renamed from: R */
    private int f1450R = 2;

    /* JADX INFO: renamed from: S */
    protected boolean f1451S = true;

    /* JADX INFO: renamed from: T */
    private boolean f1452T = true;

    /* JADX INFO: renamed from: U */
    private boolean f1453U = false;

    /* JADX INFO: renamed from: V */
    private Set<String> f1454V = new HashSet();

    /* JADX INFO: renamed from: W */
    private Set<String> f1455W = new HashSet();

    /* JADX INFO: renamed from: X */
    private boolean f1456X = false;

    protected C2710b() {
        C2612b.m991a().m997a(8, this);
    }

    /* JADX INFO: renamed from: D */
    private void m1516D() {
        InterfaceC2711c interfaceC2711c = this.f1464k;
        if (interfaceC2711c != null) {
            interfaceC2711c.mo1573c();
        }
    }

    /* JADX INFO: renamed from: b */
    private void m1517b(Map<String, String> map) {
        HashMap map2 = new HashMap();
        map2.put("d_m", new HashMap(map));
        C2612b.m991a().m999b(new C2613c(2, map2));
    }

    /* JADX INFO: renamed from: d */
    public static C2710b m1518d() {
        if (f1430a == null) {
            synchronized (C2710b.class) {
                if (f1430a == null) {
                    f1430a = new C2710b();
                }
            }
        }
        return f1430a;
    }

    /* JADX INFO: renamed from: f */
    private void m1519f(int i) {
        if (this.f1449Q != i) {
            C2716h.m1581d().m1591g();
        }
        this.f1449Q = i;
    }

    /* JADX INFO: renamed from: A */
    public boolean m1520A() {
        return this.f1435C;
    }

    /* JADX INFO: renamed from: B */
    public synchronized boolean m1521B() {
        return this.f1444L;
    }

    /* JADX INFO: renamed from: C */
    public boolean m1522C() {
        return this.f1456X;
    }

    @Override // com.tencent.beacon.p015a.p016a.InterfaceC2614d
    /* JADX INFO: renamed from: a */
    public void mo1000a(C2613c c2613c) {
        if (c2613c.f1020a != 8) {
            return;
        }
        this.f1434B = c2613c.f1021b.containsKey("u_c_a_e") ? ((Boolean) c2613c.f1021b.get("u_c_a_e")).booleanValue() : this.f1434B;
        this.f1433A = c2613c.f1021b.containsKey("u_c_b_e") ? ((Boolean) c2613c.f1021b.get("u_c_b_e")).booleanValue() : this.f1433A;
        this.f1440H = c2613c.f1021b.containsKey("u_c_d_s") ? ((Integer) c2613c.f1021b.get("u_c_d_s")).intValue() : this.f1440H;
        this.f1465l = c2613c.f1021b.containsKey("u_c_p_s") ? ((Boolean) c2613c.f1021b.get("u_c_p_s")).booleanValue() : this.f1465l;
        this.f1435C = c2613c.f1021b.containsKey("u_s_o_h") ? ((Boolean) c2613c.f1021b.get("u_s_o_h")).booleanValue() : this.f1435C;
    }

    /* JADX INFO: renamed from: c */
    public void m1541c(boolean z) {
        this.f1437E = z;
    }

    /* JADX INFO: renamed from: e */
    public void m1546e(boolean z) {
        this.f1436D = z;
    }

    /* JADX INFO: renamed from: g */
    public void m1550g(boolean z) {
        this.f1439G = z;
    }

    /* JADX INFO: renamed from: h */
    public void m1551h(boolean z) {
        this.f1443K = z;
    }

    /* JADX INFO: renamed from: i */
    public synchronized int m1553i() {
        return this.f1460g;
    }

    /* JADX INFO: renamed from: j */
    public synchronized int m1555j() {
        return this.f1459f;
    }

    /* JADX INFO: renamed from: k */
    public synchronized int m1557k() {
        return this.f1458e;
    }

    /* JADX INFO: renamed from: l */
    public synchronized int m1558l() {
        return this.f1457d;
    }

    /* JADX INFO: renamed from: m */
    public int m1559m() {
        return this.f1449Q;
    }

    /* JADX INFO: renamed from: n */
    public synchronized int m1560n() {
        return this.f1445M;
    }

    /* JADX INFO: renamed from: o */
    public boolean m1561o() {
        return this.f1452T;
    }

    /* JADX INFO: renamed from: p */
    public synchronized int m1562p() {
        return this.f1461h;
    }

    /* JADX INFO: renamed from: q */
    public boolean m1563q() {
        Boolean bool = this.f1462i;
        return bool == null ? this.f1463j : bool.booleanValue();
    }

    /* JADX INFO: renamed from: r */
    public boolean m1564r() {
        return this.f1446N;
    }

    /* JADX INFO: renamed from: s */
    public synchronized boolean m1565s() {
        return this.f1470q;
    }

    /* JADX INFO: renamed from: t */
    public boolean m1566t() {
        return this.f1437E;
    }

    /* JADX INFO: renamed from: u */
    public boolean m1567u() {
        return this.f1438F;
    }

    /* JADX INFO: renamed from: v */
    public boolean m1568v() {
        return this.f1436D;
    }

    /* JADX INFO: renamed from: w */
    public boolean m1569w() {
        return this.f1465l;
    }

    /* JADX INFO: renamed from: x */
    public boolean m1570x() {
        return this.f1439G;
    }

    /* JADX INFO: renamed from: y */
    public boolean m1571y() {
        return this.f1443K;
    }

    /* JADX INFO: renamed from: z */
    public boolean m1572z() {
        return this.f1453U;
    }

    /* JADX INFO: renamed from: c */
    public void m1540c(int i) {
        if (i < 24 || i > 100) {
            return;
        }
        this.f1459f = i;
    }

    /* JADX INFO: renamed from: e */
    public void m1545e(int i) {
        this.f1449Q = i;
    }

    /* JADX INFO: renamed from: g */
    public int m1549g() {
        return this.f1447O;
    }

    /* JADX INFO: renamed from: h */
    public boolean m1552h() {
        return this.f1451S;
    }

    /* JADX INFO: renamed from: i */
    public void m1554i(boolean z) {
        this.f1452T = z;
    }

    /* JADX INFO: renamed from: j */
    public void m1556j(boolean z) {
        this.f1456X = z;
    }

    /* JADX INFO: renamed from: c */
    public Set<String> m1539c() {
        return this.f1455W;
    }

    /* JADX INFO: renamed from: e */
    public int m1544e() {
        return this.f1450R;
    }

    /* JADX INFO: renamed from: b */
    public synchronized void m1536b(Set<String> set) {
        if (this.f1468o == null) {
            this.f1468o = new HashMap();
        }
        if (set == null) {
            return;
        }
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String[] strArrSplit = it.next().split(PunctuationConst.COMMA);
            if (strArrSplit.length == 3) {
                try {
                    this.f1468o.put(strArrSplit[0].toLowerCase(), Float.valueOf(Float.valueOf(strArrSplit[1]).floatValue() / Float.valueOf(strArrSplit[2]).floatValue()));
                } catch (Exception e) {
                    C2695c.m1465a(e);
                }
            }
        }
    }

    /* JADX INFO: renamed from: f */
    public int m1547f() {
        return this.f1440H;
    }

    /* JADX INFO: renamed from: f */
    public void m1548f(boolean z) {
        this.f1451S = z;
        if (z) {
            C2632e.m1082l().mo1090a();
        }
    }

    /* JADX INFO: renamed from: d */
    public void m1542d(int i) {
        if (i < 24 || i > 100) {
            return;
        }
        this.f1457d = i;
    }

    /* JADX INFO: renamed from: d */
    public void m1543d(boolean z) {
        this.f1438F = z;
    }

    /* JADX INFO: renamed from: a */
    public synchronized void m1527a(Map<String, String> map) {
        if (map != null) {
            m1517b(map);
            try {
                if (this.f1457d == 48) {
                    this.f1457d = C2694b.m1431a(map.get("realtimeUploadNum"), this.f1457d, 24, 100);
                }
                if (this.f1459f == 48) {
                    this.f1459f = C2694b.m1431a(map.get("normalUploadNum"), this.f1459f, 24, 100);
                }
                if (this.f1460g == 5000) {
                    this.f1460g = C2694b.m1431a(map.get("normalPollingTime"), this.f1460g, 2000, TimeConstants.HOUR);
                }
                if (this.f1458e == 2000) {
                    this.f1458e = C2694b.m1431a(map.get("realtimePollingTime"), this.f1458e, 1000, 10000);
                }
                this.f1466m = C2694b.m1443a(map.get("heartOnOff"), this.f1466m);
                this.f1469p = C2694b.m1443a(map.get("tidyEF"), this.f1469p);
                this.f1470q = C2694b.m1443a(map.get("lauEveSim"), this.f1470q);
                this.f1471r = C2694b.m1443a(map.get("zeroPeakOnOff"), this.f1471r);
                String str = map.get("zeroPeakRate");
                if (str != null) {
                    String strTrim = str.trim();
                    if (strTrim.length() > 0) {
                        String[] strArrSplit = strTrim.split(PunctuationConst.COMMA);
                        if (strArrSplit.length == 2) {
                            try {
                                this.f1472s = Float.valueOf(strArrSplit[0]).floatValue() / Float.valueOf(strArrSplit[1]).floatValue();
                            } catch (Exception e) {
                                C2695c.m1465a(e);
                            }
                        }
                    }
                }
                this.f1444L = C2694b.m1443a(map.get("straOnOff"), this.f1444L);
                this.f1445M = C2694b.m1431a(map.get("straDayMaxCount"), this.f1445M, 1, Integer.MAX_VALUE);
                this.f1473t = C2694b.m1443a(map.get("acceleEnable"), this.f1473t);
                this.f1474u = C2694b.m1443a(map.get("gyroEnable"), this.f1474u);
                this.f1475v = C2694b.m1443a(map.get("magneticEnable"), this.f1475v);
                this.f1476w = C2694b.m1431a(map.get("gatherCount"), this.f1476w, 1, 50);
                this.f1477x = C2694b.m1432a(map.get("gatherDur"), this.f1477x, 1000L, 20000L);
                this.f1478y = C2694b.m1431a(map.get("hertzCount"), this.f1478y, 20, 100);
                this.f1479z = C2694b.m1431a(map.get("consuming"), this.f1479z, 60, 86400);
                this.f1433A = C2694b.m1443a(map.get("bidEnable"), this.f1433A);
                this.f1434B = C2694b.m1443a(map.get("auditEnable"), this.f1434B);
                this.f1440H = C2694b.m1431a(map.get("maxDBCount"), this.f1440H, 10000, 100000);
                this.f1447O = C2694b.m1431a(map.get("netFailureNumMax"), this.f1447O, 1, 50);
                this.f1461h = C2694b.m1431a(map.get("weekNetPollingTime"), this.f1461h, 2000, TimeConstants.HOUR);
                this.f1448P = C2694b.m1431a(map.get("zipType"), this.f1448P, 1, 3);
                m1519f(C2694b.m1431a(map.get("rsaPubKeyType"), this.f1449Q, 0, 1));
                this.f1438F = C2694b.m1443a(map.get("needLifeCycleListener"), this.f1438F);
                C2667b.m1324c(map.get("eventUrl"));
                C2667b.m1326e(map.get("strategyUrl"));
            } catch (Exception e2) {
                C2695c.m1465a(e2);
            }
        }
    }

    /* JADX INFO: renamed from: b */
    public synchronized boolean m1538b(String str) {
        Map<String, Float> map = this.f1468o;
        if (map != null && map.get(str.toLowerCase()) != null) {
            return f1431b.nextInt(1000) + 1 <= ((int) (this.f1468o.get(str.toLowerCase()).floatValue() * 1000.0f));
        }
        return true;
    }

    /* JADX INFO: renamed from: b */
    public int m1534b() {
        return this.f1448P;
    }

    /* JADX INFO: renamed from: b */
    public void m1535b(int i) {
        this.f1450R = i;
    }

    /* JADX INFO: renamed from: b */
    public void m1537b(boolean z) {
        this.f1453U = z;
    }

    /* JADX INFO: renamed from: a */
    public synchronized void m1528a(Set<String> set) {
        this.f1467n = set;
    }

    /* JADX INFO: renamed from: a */
    public synchronized boolean m1532a(String str) {
        Set<String> set;
        set = this.f1467n;
        return (set == null || set.size() <= 0) ? false : this.f1467n.contains(str);
    }

    /* JADX INFO: renamed from: a */
    public synchronized void m1526a(String str, Map<String, Integer> map) {
        f1432c.put(str, map);
    }

    /* JADX INFO: renamed from: a */
    public synchronized boolean m1533a(String str, String str2) {
        Map<String, Integer> map = f1432c.get(str);
        if (map != null && map.get(str2) != null) {
            return f1431b.nextInt(10000) + 1 >= map.get(str2).intValue();
        }
        return false;
    }

    /* JADX INFO: renamed from: a */
    public void m1531a(boolean z, boolean z2) {
        C2695c.m1464a("event report state changed: " + z, new Object[0]);
        boolean zM1563q = m1563q();
        if (z2) {
            this.f1462i = Boolean.valueOf(z);
        } else {
            this.f1463j = z;
        }
        if (zM1563q != m1563q()) {
            m1516D();
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1525a(InterfaceC2711c interfaceC2711c) {
        this.f1464k = interfaceC2711c;
    }

    /* JADX INFO: renamed from: a */
    public void m1530a(boolean z) {
        this.f1446N = z;
    }

    /* JADX INFO: renamed from: a */
    public void m1524a(int i) {
        this.f1448P = i;
    }

    /* JADX INFO: renamed from: a */
    public void m1529a(Set<String> set, Set<String> set2) {
        this.f1454V = set;
        this.f1455W = set2;
    }

    /* JADX INFO: renamed from: a */
    public Set<String> m1523a() {
        return this.f1454V;
    }
}
