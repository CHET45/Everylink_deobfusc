package com.tencent.beacon.event.p031c;

import android.content.Context;
import android.os.Handler;
import com.tencent.beacon.base.store.SharedPreferencesC2686a;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.event.open.EventType;
import com.tencent.beacon.p015a.p016a.C2612b;
import com.tencent.beacon.p015a.p016a.C2613c;
import com.tencent.beacon.p015a.p017b.AbstractC2616b;
import com.tencent.beacon.p015a.p017b.C2623i;
import com.tencent.beacon.p015a.p018c.C2629b;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: renamed from: com.tencent.beacon.event.c.g */
/* JADX INFO: compiled from: LogIDGenerator.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2735g {

    /* JADX INFO: renamed from: a */
    private static final Map<String, C2735g> f1547a = new ConcurrentHashMap(5);

    /* JADX INFO: renamed from: b */
    private static volatile Handler f1548b;

    /* JADX INFO: renamed from: A */
    private volatile boolean f1549A;

    /* JADX INFO: renamed from: m */
    private final Context f1560m;

    /* JADX INFO: renamed from: n */
    private final String f1561n;

    /* JADX INFO: renamed from: p */
    private long f1563p;

    /* JADX INFO: renamed from: t */
    private long f1567t;

    /* JADX INFO: renamed from: u */
    private long f1568u;

    /* JADX INFO: renamed from: v */
    private long f1569v;

    /* JADX INFO: renamed from: w */
    private long f1570w;

    /* JADX INFO: renamed from: x */
    private long f1571x;

    /* JADX INFO: renamed from: y */
    private long f1572y;

    /* JADX INFO: renamed from: c */
    private final String f1550c = "normal_log_id";

    /* JADX INFO: renamed from: d */
    private final String f1551d = "realtime_log_id";

    /* JADX INFO: renamed from: e */
    private final String f1552e = "immediate_log_id";

    /* JADX INFO: renamed from: f */
    private final String f1553f = "normal_min_log_id";

    /* JADX INFO: renamed from: g */
    private final String f1554g = "normal_max_log_id";

    /* JADX INFO: renamed from: h */
    private final String f1555h = "realtime_min_log_id";

    /* JADX INFO: renamed from: i */
    private final String f1556i = "realtime_max_log_id";

    /* JADX INFO: renamed from: j */
    private final String f1557j = "immediate_min_log_id";

    /* JADX INFO: renamed from: k */
    private final String f1558k = "immediate_max_log_id";

    /* JADX INFO: renamed from: l */
    private final String f1559l = "on_date";

    /* JADX INFO: renamed from: o */
    private final List<String> f1562o = new ArrayList();

    /* JADX INFO: renamed from: q */
    private AtomicLong f1564q = new AtomicLong(0);

    /* JADX INFO: renamed from: r */
    private AtomicLong f1565r = new AtomicLong(0);

    /* JADX INFO: renamed from: s */
    private AtomicLong f1566s = new AtomicLong(0);

    /* JADX INFO: renamed from: z */
    private final Runnable f1573z = new RunnableC2733e(this);

    private C2735g(Context context, String str) {
        this.f1560m = context;
        this.f1561n = str;
    }

    /* JADX INFO: renamed from: a */
    public static synchronized C2735g m1649a(Context context, String str) {
        C2735g c2735g;
        Map<String, C2735g> map = f1547a;
        c2735g = map.get(str);
        if (c2735g == null) {
            c2735g = new C2735g(context, str);
            map.put(str, c2735g);
        }
        return c2735g;
    }

    /* JADX INFO: renamed from: c */
    private void m1654c() {
        if (m1658e()) {
            String str = this.f1563p + "";
            String strM1049c = C2629b.m1049c(this.f1560m);
            String packageName = this.f1560m.getPackageName();
            C2623i.m1030e().m1023a("701", "process_name=" + strM1049c + "&real_logid_min=" + (this.f1567t == 0 ? "" : (this.f1567t - 1) + "") + "&real_logid_max=" + (this.f1568u == 0 ? "" : this.f1568u + "") + "&normal_logid_min=" + (this.f1569v == 0 ? "" : (this.f1569v - 1) + "") + "&normal_logid_max=" + (this.f1570w == 0 ? "" : this.f1570w + "") + "&immediate_logid_min=" + (this.f1571x == 0 ? "" : (this.f1571x - 1) + "") + "&immediate_logid_max=" + (this.f1572y != 0 ? this.f1572y + "" : "") + "&logid_day=" + str.substring(0, str.length() - 3) + "&appkey=" + this.f1561n + "&bundleid=" + packageName);
            m1662g();
        }
    }

    /* JADX INFO: renamed from: d */
    private void m1656d() {
        SharedPreferencesC2686a sharedPreferencesC2686aM1391a = SharedPreferencesC2686a.m1391a();
        this.f1563p = sharedPreferencesC2686aM1391a.getLong("on_date", 0L);
        this.f1565r.set(sharedPreferencesC2686aM1391a.getLong("realtime_log_id", 0L));
        this.f1564q.set(sharedPreferencesC2686aM1391a.getLong("normal_log_id", 0L));
        this.f1566s.set(sharedPreferencesC2686aM1391a.getLong("immediate_log_id", 0L));
        this.f1567t = sharedPreferencesC2686aM1391a.getLong("realtime_min_log_id", 0L);
        this.f1568u = sharedPreferencesC2686aM1391a.getLong("realtime_max_log_id", 0L);
        this.f1569v = sharedPreferencesC2686aM1391a.getLong("normal_min_log_id", 0L);
        this.f1570w = sharedPreferencesC2686aM1391a.getLong("normal_max_log_id", 0L);
        this.f1571x = sharedPreferencesC2686aM1391a.getLong("immediate_min_log_id", 0L);
        this.f1572y = sharedPreferencesC2686aM1391a.getLong("immediate_max_log_id", 0L);
        C2695c.m1463a("[LogID " + this.f1561n + "]", " load LogID from sp, date: %s , realtime: %d, normal: %d, immediate: %d", Long.valueOf(this.f1563p), Long.valueOf(this.f1565r.get()), Long.valueOf(this.f1564q.get()), Long.valueOf(this.f1566s.get()));
    }

    /* JADX INFO: renamed from: e */
    private boolean m1658e() {
        long jM1457d = C2694b.m1457d();
        long j = this.f1563p;
        return (j == 0 || C2694b.m1440a(jM1457d, j)) ? false : true;
    }

    /* JADX INFO: renamed from: f */
    private void m1660f() {
        HashMap map = new HashMap();
        map.put("e_l_e_k", this.f1561n);
        C2612b.m991a().m998a(new C2613c(14, map));
    }

    /* JADX INFO: renamed from: g */
    private void m1662g() {
        this.f1567t = this.f1568u + 1;
        this.f1569v = this.f1570w + 1;
        this.f1571x = this.f1572y + 1;
    }

    /* JADX INFO: renamed from: b */
    public synchronized boolean m1669b() {
        return this.f1549A;
    }

    /* JADX INFO: renamed from: a */
    public synchronized String m1667a(String str, EventType eventType) {
        if (!this.f1549A) {
            m1668a();
            this.f1549A = true;
        }
        if (this.f1562o.contains(str)) {
            return "";
        }
        long jM1648a = m1648a(eventType);
        m1654c();
        this.f1563p = C2694b.m1457d();
        m1651a(jM1648a, eventType);
        C2695c.m1463a("[stat " + this.f1561n + "]", "type: %s, code: %s, logID: %s.", eventType, str, Long.valueOf(jM1648a));
        f1548b.post(this.f1573z);
        return jM1648a + "";
    }

    /* JADX INFO: renamed from: a */
    private long m1648a(EventType eventType) {
        switch (C2734f.f1546a[eventType.ordinal()]) {
            case 1:
            case 2:
                return this.f1564q.incrementAndGet();
            case 3:
            case 4:
                return this.f1565r.incrementAndGet();
            case 5:
                return this.f1566s.incrementAndGet();
            case 6:
                return this.f1566s.incrementAndGet();
            default:
                return -1L;
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1651a(long j, EventType eventType) {
        if (eventType != EventType.REALTIME && eventType != EventType.DT_REALTIME) {
            if (eventType != EventType.NORMAL && eventType != EventType.DT_NORMAL) {
                if (eventType == EventType.IMMEDIATE_MSF || eventType == EventType.IMMEDIATE) {
                    long j2 = this.f1572y;
                    this.f1572y = j2 == 0 ? j : Math.max(j, j2);
                    long j3 = this.f1571x;
                    if (j3 != 0) {
                        j = Math.min(j, j3);
                    }
                    this.f1571x = j;
                    return;
                }
                return;
            }
            long j4 = this.f1570w;
            this.f1570w = j4 == 0 ? j : Math.max(j, j4);
            long j5 = this.f1569v;
            if (j5 != 0) {
                j = Math.min(j, j5);
            }
            this.f1569v = j;
            return;
        }
        long j6 = this.f1568u;
        this.f1568u = j6 == 0 ? j : Math.max(j, j6);
        long j7 = this.f1567t;
        if (j7 != 0) {
            j = Math.min(j, j7);
        }
        this.f1567t = j;
    }

    /* JADX INFO: renamed from: a */
    public synchronized void m1668a() {
        if (this.f1549A) {
            return;
        }
        C2695c.m1464a("LogIDGenerator init appkey = %s", this.f1561n);
        f1548b = AbstractC2616b.m1001a().mo1005a(113);
        this.f1562o.add("rqd_model");
        this.f1562o.add("rqd_appresumed");
        m1656d();
        m1660f();
        this.f1549A = true;
    }
}
