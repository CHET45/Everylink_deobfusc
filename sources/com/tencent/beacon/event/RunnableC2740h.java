package com.tencent.beacon.event;

import com.github.houbb.heaven.constant.PunctuationConst;
import com.tencent.beacon.base.net.C2671c;
import com.tencent.beacon.base.net.call.JceRequestEntity;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.event.p029a.C2722a;
import com.tencent.beacon.event.p031c.C2732d;
import com.tencent.beacon.p015a.p016a.C2612b;
import com.tencent.beacon.p015a.p016a.C2613c;
import com.tencent.beacon.p015a.p016a.InterfaceC2614d;
import com.tencent.beacon.p015a.p017b.AbstractC2616b;
import com.tencent.beacon.p015a.p017b.C2623i;
import com.tencent.beacon.p028d.C2710b;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.tencent.beacon.event.h */
/* JADX INFO: compiled from: EventReportTask.java */
/* JADX INFO: loaded from: classes4.dex */
public class RunnableC2740h implements Runnable, InterfaceC2614d {

    /* JADX INFO: renamed from: a */
    private final String f1592a;

    /* JADX INFO: renamed from: b */
    private final int f1593b;

    /* JADX INFO: renamed from: c */
    private final C2722a f1594c;

    /* JADX INFO: renamed from: g */
    private final String f1598g;

    /* JADX INFO: renamed from: h */
    private boolean f1599h;

    /* JADX INFO: renamed from: i */
    private int f1600i;

    /* JADX INFO: renamed from: j */
    private boolean f1601j;

    /* JADX INFO: renamed from: l */
    private String f1603l;

    /* JADX INFO: renamed from: d */
    private final Set<Long> f1595d = new HashSet();

    /* JADX INFO: renamed from: e */
    private final Set<Long> f1596e = new HashSet();

    /* JADX INFO: renamed from: f */
    private final List<Long> f1597f = new ArrayList();

    /* JADX INFO: renamed from: k */
    private boolean f1602k = false;

    public RunnableC2740h(int i, C2722a c2722a, boolean z) {
        this.f1593b = i;
        this.f1594c = c2722a;
        this.f1599h = z;
        String str = z ? "t_r_e" : "t_n_e";
        this.f1592a = str;
        this.f1600i = z ? C2710b.m1518d().m1558l() : C2710b.m1518d().m1555j();
        this.f1598g = "[EventReport (" + str + ")]";
    }

    /* JADX INFO: renamed from: a */
    private void m1687a(List<EventBean> list, Set<Long> set) {
        JceRequestEntity jceRequestEntityM1633a = C2732d.m1633a(list, this.f1599h);
        C2695c.m1461a(this.f1598g, 2, "event request entity: %s", jceRequestEntityM1633a.toString());
        C2671c.m1351d().m1361b(jceRequestEntityM1633a).m1388a(new C2739g(this, this.f1592a, this.f1594c, set, this.f1603l));
    }

    /* JADX INFO: renamed from: d */
    private List<EventBean> m1688d() {
        StringBuilder sb = new StringBuilder();
        Iterator<Long> it = this.f1595d.iterator();
        while (it.hasNext()) {
            sb.append(it.next()).append(PunctuationConst.COMMA);
        }
        return this.f1594c.m1619a(this.f1592a, sb.length() > 0 ? sb.substring(0, sb.lastIndexOf(PunctuationConst.COMMA)) : "", this.f1600i);
    }

    /* JADX INFO: renamed from: e */
    private void m1689e() {
        C2612b.m991a().m997a(2, this);
    }

    /* JADX INFO: renamed from: b */
    public int m1693b() {
        return this.f1600i;
    }

    /* JADX INFO: renamed from: c */
    public synchronized void m1694c() {
        if (this.f1602k) {
            this.f1600i = this.f1599h ? C2710b.m1518d().m1558l() : C2710b.m1518d().m1555j();
            this.f1602k = false;
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        if (!this.f1601j) {
            m1689e();
            this.f1601j = true;
        }
        if (C2671c.m1351d().m1364f()) {
            AbstractC2616b.m1001a().mo1008a(this.f1593b, false, 0);
            return;
        }
        if (C2671c.m1351d().m1362c() >= C2710b.m1518d().m1549g()) {
            AbstractC2616b.m1001a().m1013b(C2710b.m1518d().m1562p());
            return;
        }
        synchronized (this.f1595d) {
            C2695c.m1461a(this.f1598g, 0, "start read EventBean from DB.", new Object[0]);
            List<EventBean> listM1688d = m1688d();
            if (listM1688d != null && !listM1688d.isEmpty()) {
                HashMap map = new HashMap();
                for (EventBean eventBean : listM1688d) {
                    long cid = eventBean.getCid();
                    this.f1595d.add(Long.valueOf(cid));
                    this.f1596e.add(Long.valueOf(cid));
                    Map<String, String> eventValue = eventBean.getEventValue();
                    if (eventValue != null) {
                        String appKey = eventBean.getAppKey();
                        String str = (String) map.get(appKey);
                        if (str == null) {
                            str = appKey + ": ";
                        }
                        map.put(appKey, str + eventValue.get("A100") + ", ");
                    }
                }
                StringBuilder sb = new StringBuilder("--logID: \n");
                Iterator it = map.entrySet().iterator();
                while (it.hasNext()) {
                    sb.append((String) ((Map.Entry) it.next()).getValue()).append("\n");
                }
                String string = sb.toString();
                this.f1603l = string;
                C2695c.m1461a(this.f1598g, 1, "send LogID: %s", string);
                m1687a(listM1688d, this.f1596e);
                listM1688d.clear();
                this.f1596e.clear();
                return;
            }
            C2695c.m1461a(this.f1598g, 1, "EventBean List == null. Task end!", new Object[0]);
            AbstractC2616b.m1001a().mo1008a(this.f1593b, false, 0);
        }
    }

    @Override // com.tencent.beacon.p015a.p016a.InterfaceC2614d
    /* JADX INFO: renamed from: a */
    public void mo1000a(C2613c c2613c) {
        Map map;
        if (c2613c.f1020a != 2 || (map = (Map) c2613c.f1021b.get("d_m")) == null) {
            return;
        }
        if (this.f1599h) {
            this.f1600i = C2694b.m1431a((String) map.get("realtimeUploadNum"), this.f1600i, 24, 100);
        } else {
            this.f1600i = C2694b.m1431a((String) map.get("normalUploadNum"), this.f1600i, 24, 100);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1692a(Set<Long> set) {
        synchronized (this.f1595d) {
            this.f1595d.removeAll(set);
            set.clear();
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1691a(long j) {
        synchronized (this.f1597f) {
            this.f1597f.add(Long.valueOf(j));
            if (this.f1597f.size() >= 10) {
                Iterator<Long> it = this.f1597f.iterator();
                long jLongValue = 0;
                while (it.hasNext()) {
                    jLongValue += it.next().longValue();
                }
                C2623i.m1030e().m1023a("703", (jLongValue / ((long) this.f1597f.size())) + "");
                this.f1597f.clear();
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public synchronized void m1690a() {
        int i = this.f1600i;
        if (i >= 2) {
            this.f1600i = i >> 1;
            this.f1602k = true;
        }
    }
}
