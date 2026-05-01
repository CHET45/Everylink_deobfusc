package com.tencent.beacon.module;

import android.content.Context;
import android.text.TextUtils;
import com.github.houbb.heaven.util.lang.BoolUtil;
import com.tencent.beacon.base.net.C2671c;
import com.tencent.beacon.base.net.p021b.RunnableC2670e;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.base.util.C2700h;
import com.tencent.beacon.event.C2737e;
import com.tencent.beacon.event.EventBean;
import com.tencent.beacon.event.InterfaceC2741i;
import com.tencent.beacon.event.immediate.IBeaconImmediateReport;
import com.tencent.beacon.event.open.BeaconEvent;
import com.tencent.beacon.event.open.BeaconReport;
import com.tencent.beacon.event.open.EventResult;
import com.tencent.beacon.event.p029a.C2722a;
import com.tencent.beacon.event.p030b.AbstractC2727c;
import com.tencent.beacon.event.p030b.C2725a;
import com.tencent.beacon.event.p030b.C2726b;
import com.tencent.beacon.event.p031c.C2732d;
import com.tencent.beacon.event.p031c.C2735g;
import com.tencent.beacon.event.quic.IBeaconQuicReport;
import com.tencent.beacon.p015a.p016a.C2612b;
import com.tencent.beacon.p015a.p016a.C2613c;
import com.tencent.beacon.p015a.p016a.InterfaceC2614d;
import com.tencent.beacon.p015a.p017b.AbstractC2616b;
import com.tencent.beacon.p015a.p017b.C2623i;
import com.tencent.beacon.p015a.p017b.C2624j;
import com.tencent.beacon.p015a.p018c.C2630c;
import com.tencent.beacon.p015a.p018c.C2636i;
import com.tencent.beacon.p028d.C2710b;
import com.tencent.beacon.p028d.InterfaceC2711c;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes4.dex */
public class EventModule implements BeaconModule, InterfaceC2614d, RunnableC2670e.a, InterfaceC2711c {

    /* JADX INFO: renamed from: a */
    private static final Map<String, Map<String, String>> f1708a = new ConcurrentHashMap(3);

    /* JADX INFO: renamed from: b */
    private static final Map<String, String> f1709b = new ConcurrentHashMap(3);

    /* JADX INFO: renamed from: c */
    private static final Map<String, String> f1710c = new ConcurrentHashMap(3);

    /* JADX INFO: renamed from: d */
    private final List<AbstractC2727c> f1711d = new ArrayList(3);

    /* JADX INFO: renamed from: e */
    private AtomicInteger f1712e = new AtomicInteger(0);

    /* JADX INFO: renamed from: f */
    private AtomicBoolean f1713f = new AtomicBoolean(false);

    /* JADX INFO: renamed from: g */
    private AtomicInteger f1714g = new AtomicInteger(0);

    /* JADX INFO: renamed from: h */
    private AtomicBoolean f1715h = new AtomicBoolean(false);

    /* JADX INFO: renamed from: i */
    private final ConcurrentHashMap<String, List<BeaconEvent>> f1716i = new ConcurrentHashMap<>();

    /* JADX INFO: renamed from: j */
    private StrategyModule f1717j;

    /* JADX INFO: renamed from: k */
    private InterfaceC2741i f1718k;

    /* JADX INFO: renamed from: l */
    private volatile boolean f1719l;

    /* JADX INFO: renamed from: b */
    private void m1749b(BeaconEvent beaconEvent) {
        String appKey = beaconEvent.getAppKey();
        if (this.f1714g.addAndGet(1) > 1000) {
            String str = String.format("logid empty cache count over max , appKey: %s, event: %s", appKey, beaconEvent.getCode());
            C2695c.m1464a(str, new Object[0]);
            if (this.f1715h.compareAndSet(false, true)) {
                C2624j.m1031e().m1023a("518", str);
                return;
            }
            return;
        }
        List<BeaconEvent> arrayList = this.f1716i.get(appKey);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        arrayList.add(beaconEvent);
        this.f1716i.put(appKey, arrayList);
        C2695c.m1464a("logid empty and add to cache , appKey: %s, event: %s", appKey, beaconEvent.getCode());
    }

    /* JADX INFO: renamed from: d */
    private String m1750d(String str) {
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        C2695c.m1476e("we strongly recommend you set appkey when create events.", new Object[0]);
        return C2630c.m1059c().m1072e();
    }

    /* JADX INFO: renamed from: g */
    private void m1751g() {
        this.f1711d.add(new C2726b());
        this.f1711d.add(new C2725a());
        for (int i = 1; i < this.f1711d.size(); i++) {
            this.f1711d.get(i - 1).m1626a(this.f1711d.get(i));
        }
    }

    /* JADX INFO: renamed from: h */
    private void m1752h() {
        if (C2710b.m1518d().m1563q()) {
            C2737e c2737e = new C2737e();
            this.f1718k = c2737e;
            c2737e.mo1672a();
        }
    }

    /* JADX INFO: renamed from: i */
    private void m1753i() {
        C2612b.m991a().m997a(3, this);
        C2612b.m991a().m997a(4, this);
        C2612b.m991a().m997a(5, this);
        C2612b.m991a().m997a(6, this);
        C2612b.m991a().m997a(1, this);
        C2612b.m991a().m997a(7, this);
        C2612b.m991a().m997a(14, this);
    }

    /* JADX INFO: renamed from: j */
    private void m1754j() {
        AbstractC2616b.m1001a().mo1005a(3000).postAtFrontOfQueue(new Runnable() { // from class: com.tencent.beacon.module.EventModule.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    C2722a c2722aM1615a = C2722a.m1615a();
                    Map<String, Integer> mapM1623b = c2722aM1615a.m1623b("t_r_e");
                    EventModule.this.m1748a(mapM1623b, c2722aM1615a.m1623b("t_n_e"));
                } catch (Throwable th) {
                    C2695c.m1465a(th);
                    C2624j.m1031e().m1024a("204", "error while storageReport", th);
                }
            }
        });
    }

    /* JADX INFO: renamed from: c */
    public String m1763c(String str) {
        String str2;
        String strM1750d = m1750d(str);
        return (TextUtils.isEmpty(strM1750d) || (str2 = f1709b.get(strM1750d)) == null) ? "10000" : str2;
    }

    /* JADX INFO: renamed from: e */
    public boolean m1765e() {
        return this.f1719l;
    }

    /* JADX INFO: renamed from: f */
    public void m1766f() {
        C2695c.m1463a("[EventModule]", "resume report by user.", new Object[0]);
        this.f1718k.mo1673a(2);
        C2671c.m1351d().m1365g();
    }

    @Override // com.tencent.beacon.module.BeaconModule
    /* JADX INFO: renamed from: a */
    public void mo1746a(Context context) {
        m1754j();
        this.f1717j = (StrategyModule) BeaconModule.f1707a.get(ModuleName.STRATEGY);
        m1751g();
        m1752h();
        m1753i();
        RunnableC2670e.m1348a(context, this);
        C2710b.m1518d().m1525a(this);
        this.f1719l = true;
    }

    @Override // com.tencent.beacon.p028d.InterfaceC2711c
    /* JADX INFO: renamed from: c */
    public void mo1573c() {
        if (C2710b.m1518d().m1563q()) {
            if (this.f1718k == null) {
                m1752h();
                return;
            } else {
                m1766f();
                return;
            }
        }
        m1762b(true);
    }

    /* JADX INFO: renamed from: d */
    public InterfaceC2741i m1764d() {
        return this.f1718k;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m1748a(Map<String, Integer> map, Map<String, Integer> map2) {
        HashSet<String> hashSet = new HashSet();
        hashSet.addAll(map.keySet());
        hashSet.addAll(map2.keySet());
        if (hashSet.isEmpty()) {
            C2623i.m1030e().m1023a("702", String.format("real_logid_count=%s&normal_logid_count=%s&appkey=%s", 0, 0, C2630c.m1059c().m1072e()));
            return;
        }
        for (String str : hashSet) {
            C2623i.m1030e().m1023a("702", String.format("real_logid_count=%s&normal_logid_count=%s&appkey=%s", Integer.valueOf(map.containsKey(str) ? map.get(str).intValue() : 0), Integer.valueOf(map2.containsKey(str) ? map2.get(str).intValue() : 0), str));
        }
    }

    /* JADX INFO: renamed from: b */
    public void m1761b(String str, String str2) {
        f1709b.put(m1750d(str), C2732d.m1645e(str2));
    }

    /* JADX INFO: renamed from: b */
    public String m1760b(String str) {
        String str2;
        String strM1750d = m1750d(str);
        return (TextUtils.isEmpty(strM1750d) || (str2 = f1710c.get(strM1750d)) == null) ? "" : str2;
    }

    @Override // com.tencent.beacon.base.net.p021b.RunnableC2670e.a
    /* JADX INFO: renamed from: b */
    public void mo1091b() {
        m1762b(false);
    }

    /* JADX INFO: renamed from: b */
    public void m1762b(boolean z) {
        C2695c.m1463a("[EventModule]", "pause report by user.", new Object[0]);
        this.f1718k.mo1677a(z, 2);
        C2671c.m1351d().close();
    }

    @Override // com.tencent.beacon.p015a.p016a.InterfaceC2614d
    /* JADX INFO: renamed from: a */
    public void mo1000a(C2613c c2613c) {
        List<BeaconEvent> list;
        int i = c2613c.f1020a;
        if (i == 1) {
            C2612b.m991a().m997a(12, this);
            return;
        }
        if (i == 12) {
            Object obj = c2613c.f1021b.get("e_q_e_k");
            if (obj instanceof BeaconEvent) {
                BeaconEvent beaconEvent = (BeaconEvent) obj;
                beaconEvent.getParams().put("A93", BoolUtil.f541Y);
                C2695c.m1464a("ostar empty cache report , appKey: %s, event: %s", beaconEvent.getAppKey(), beaconEvent.getCode());
                m1755a(beaconEvent);
                return;
            }
            return;
        }
        if (i == 14) {
            Object obj2 = c2613c.f1021b.get("e_l_e_k");
            if (!(obj2 instanceof String) || (list = this.f1716i.get(obj2)) == null || list.isEmpty()) {
                return;
            }
            for (BeaconEvent beaconEvent2 : list) {
                C2695c.m1464a("logid empty cache report , appKey: %s, event: %s", obj2, beaconEvent2.getCode());
                m1755a(beaconEvent2);
            }
            return;
        }
        if (i == 3) {
            m1758a((String) c2613c.f1021b.get("i_c_ak"), (HashMap) c2613c.f1021b.get("i_c_ad"));
            return;
        }
        if (i == 4) {
            m1761b((String) c2613c.f1021b.get("i_c_ak"), (String) c2613c.f1021b.get("i_c_u_i"));
            return;
        }
        if (i == 5) {
            m1757a((String) c2613c.f1021b.get("i_c_ak"), (String) c2613c.f1021b.get("i_c_o_i"));
            return;
        }
        if (i != 6) {
            if (i != 7) {
                return;
            }
            C2710b.m1518d().m1531a(((Boolean) c2613c.f1021b.get("s_e_e")).booleanValue(), false);
        } else {
            Object obj3 = c2613c.f1021b.get("b_e");
            if (obj3 instanceof BeaconEvent) {
                m1755a((BeaconEvent) obj3);
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1759a(boolean z) {
        InterfaceC2741i interfaceC2741i = this.f1718k;
        if (interfaceC2741i != null) {
            interfaceC2741i.mo1676a(z);
        }
    }

    /* JADX INFO: renamed from: a */
    public EventResult m1755a(BeaconEvent beaconEvent) {
        StrategyModule strategyModule;
        C2695c.m1461a("[EventModule]", 0, "event: %s. go in EventModule", beaconEvent.getCode());
        if (!C2710b.m1518d().m1563q()) {
            C2695c.m1461a("[EventModule]", 1, "event: %s. EventModule is not enable", beaconEvent.getCode());
            return EventResult.C2742a.m1744a(102);
        }
        beaconEvent.setAppKey(m1750d(beaconEvent.getAppKey()));
        final C2735g c2735gM1649a = C2735g.m1649a(C2630c.m1059c().m1067b(), beaconEvent.getAppKey());
        if (!c2735gM1649a.m1669b()) {
            m1749b(beaconEvent);
            AbstractC2616b.m1001a().mo1011a(new Runnable() { // from class: com.tencent.beacon.module.EventModule.2
                @Override // java.lang.Runnable
                public void run() {
                    c2735gM1649a.m1668a();
                }
            });
            return EventResult.C2742a.m1744a(107);
        }
        C2700h.m1486a(beaconEvent.getParams());
        StrategyModule strategyModule2 = this.f1717j;
        if (strategyModule2 != null && strategyModule2.m1784b().m1532a(beaconEvent.getCode())) {
            C2695c.m1461a("[EventModule]", 1, "event: %s.  is not allowed in strategy (false)", beaconEvent.getCode());
            return EventResult.C2742a.m1744a(100);
        }
        if (beaconEvent.isSucceed() && (strategyModule = this.f1717j) != null && !strategyModule.m1784b().m1538b(beaconEvent.getCode())) {
            C2695c.m1461a("[EventModule]", 1, "event: %s. is sampled by svr rate (false)", beaconEvent.getCode());
            return EventResult.C2742a.m1744a(101);
        }
        StrategyModule strategyModule3 = this.f1717j;
        if (strategyModule3 != null && strategyModule3.m1784b().m1533a(beaconEvent.getAppKey(), beaconEvent.getCode())) {
            C2695c.m1461a("[EventModule]", 1, "appkey: %s, event: %s. is sampled by user", beaconEvent.getAppKey(), beaconEvent.getCode());
            return EventResult.C2742a.m1744a(108);
        }
        EventBean eventBeanM1627b = this.f1711d.get(0).m1627b(beaconEvent);
        if (eventBeanM1627b == null) {
            return EventResult.C2742a.m1744a(105);
        }
        if (TextUtils.isEmpty(C2636i.m1147b()) && TextUtils.isEmpty(C2636i.m1149c())) {
            if (this.f1712e.addAndGet(1) > 64) {
                String str = String.format("ostar empty cache count over max , appKey: %s, event: %s", beaconEvent.getAppKey(), beaconEvent.getCode());
                C2695c.m1464a(str, new Object[0]);
                if (this.f1713f.compareAndSet(false, true)) {
                    C2624j.m1031e().m1023a("510", str);
                }
            } else {
                C2695c.m1464a("ostar empty and add to cache , appKey: %s, event: %s", beaconEvent.getAppKey(), beaconEvent.getCode());
                BeaconEvent beaconEventBuild = BeaconEvent.newBuilder(beaconEvent).build();
                HashMap map = new HashMap();
                map.put("e_q_e_k", beaconEventBuild);
                C2612b.m991a().m998a(new C2613c(12, map));
            }
        }
        int eventType = eventBeanM1627b.getEventType();
        if (eventType == 3) {
            IBeaconImmediateReport immediateReport = BeaconReport.getInstance().getImmediateReport();
            C2700h.m1488a("immediateReport", immediateReport);
            if (immediateReport != null) {
                String str2 = beaconEvent.getParams().get("A100");
                this.f1718k.mo1679b(str2, eventBeanM1627b);
                return EventResult.C2742a.m1745a(Long.parseLong(str2));
            }
            C2695c.m1464a("immediateReport is null!", new Object[0]);
            C2624j.m1031e().m1023a("515", "immediateReport is null!");
        }
        if (eventType == 2) {
            IBeaconQuicReport beaconQuicReport = BeaconReport.getInstance().getBeaconQuicReport();
            C2700h.m1488a("beaconQuicReport", beaconQuicReport);
            if (beaconQuicReport != null) {
                String str3 = beaconEvent.getParams().get("A100");
                this.f1718k.mo1675a(str3, eventBeanM1627b);
                return EventResult.C2742a.m1745a(Long.parseLong(str3));
            }
            C2695c.m1464a("beaconQuicReport is null!", new Object[0]);
            C2624j.m1031e().m1023a("471", "beaconQuicReport is null!");
        }
        return this.f1718k.mo1680c(beaconEvent.getParams().get("A100"), eventBeanM1627b);
    }

    /* JADX INFO: renamed from: a */
    public void m1757a(String str, String str2) {
        f1710c.put(m1750d(str), C2732d.m1643c(str2));
    }

    /* JADX INFO: renamed from: a */
    public void m1758a(String str, Map<String, String> map) {
        if (map == null || map.isEmpty() || map.size() >= 50) {
            C2695c.m1464a("setAdditionalParams error , params.size: %s", Integer.valueOf(map != null ? map.size() : 0));
            return;
        }
        String strM1750d = m1750d(str);
        Map<String, Map<String, String>> map2 = f1708a;
        Map<String, String> map3 = map2.get(strM1750d);
        if (map3 != null) {
            if (map3.size() + map.size() >= 50) {
                C2695c.m1464a("setAdditionalParams error , params.size: can not more than 50", new Object[0]);
                return;
            }
            HashMap map4 = new HashMap();
            map4.putAll(map3);
            map4.putAll(map);
            map2.put(strM1750d, map4);
            return;
        }
        map2.put(strM1750d, new HashMap(map));
    }

    /* JADX INFO: renamed from: a */
    public Map<String, String> m1756a(String str) {
        return f1708a.get(m1750d(str));
    }

    @Override // com.tencent.beacon.base.net.p021b.RunnableC2670e.a
    /* JADX INFO: renamed from: a */
    public void mo1090a() {
        m1766f();
    }
}
