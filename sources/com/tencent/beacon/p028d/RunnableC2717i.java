package com.tencent.beacon.p028d;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import com.github.houbb.heaven.util.lang.BoolUtil;
import com.tencent.beacon.base.net.C2671c;
import com.tencent.beacon.base.net.C2684d;
import com.tencent.beacon.base.net.RequestType;
import com.tencent.beacon.base.net.call.Callback;
import com.tencent.beacon.base.net.call.JceRequestEntity;
import com.tencent.beacon.base.net.p021b.C2667b;
import com.tencent.beacon.base.store.SharedPreferencesC2686a;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.module.StrategyModule;
import com.tencent.beacon.p015a.p016a.C2612b;
import com.tencent.beacon.p015a.p016a.C2613c;
import com.tencent.beacon.p015a.p017b.AbstractC2616b;
import com.tencent.beacon.p015a.p018c.C2629b;
import com.tencent.beacon.p015a.p018c.C2630c;
import com.tencent.beacon.p015a.p018c.C2632e;
import com.tencent.beacon.p015a.p018c.C2634g;
import com.tencent.beacon.p015a.p018c.C2636i;
import com.tencent.beacon.pack.C2755a;
import com.tencent.beacon.pack.CommonStrategy;
import com.tencent.beacon.pack.ModuleStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.beacon.d.i */
/* JADX INFO: compiled from: StrategyQueryTask.java */
/* JADX INFO: loaded from: classes4.dex */
public class RunnableC2717i implements Runnable, Callback<byte[]> {

    /* JADX INFO: renamed from: b */
    private final StrategyModule f1499b;

    /* JADX INFO: renamed from: d */
    private boolean f1501d;

    /* JADX INFO: renamed from: c */
    private volatile boolean f1500c = false;

    /* JADX INFO: renamed from: a */
    private final Context f1498a = C2630c.m1059c().m1067b();

    public RunnableC2717i(StrategyModule strategyModule) {
        this.f1499b = strategyModule;
    }

    /* JADX INFO: renamed from: c */
    private void m1597c() {
        m1601f();
        this.f1500c = false;
    }

    /* JADX INFO: renamed from: d */
    private void m1599d() {
        HashMap map = new HashMap();
        map.put("s_e_e", false);
        C2612b.m991a().m998a(new C2613c(7, map));
    }

    /* JADX INFO: renamed from: e */
    private void m1600e() {
        C2695c.m1464a("local strategyQuery finish!", new Object[0]);
        C2612b.m991a().m998a(new C2613c(10));
    }

    /* JADX INFO: renamed from: f */
    private void m1601f() {
        long jM1512b = ((long) this.f1499b.m1782a().m1512b()) * 60000;
        AbstractC2616b.m1001a().mo1010a(jM1512b, this);
        C2695c.m1464a("[strategy] next time: %d", Long.valueOf(jM1512b));
    }

    /* JADX INFO: renamed from: g */
    private void m1602g() {
        if (C2719k.m1612b() || C2719k.m1613c()) {
            C2695c.m1476e("[strategy] query times or query success times arrive max, return!", new Object[0]);
            this.f1499b.m1783a(true);
        } else {
            C2632e c2632eM1082l = C2632e.m1082l();
            C2634g c2634gM1115e = C2634g.m1115e();
            C2671c.m1351d().m1361b(JceRequestEntity.builder().m1373a(RequestType.STRATEGY).m1372a(100).m1380b(101).m1375a(C2630c.m1059c().m1072e()).m1381b(C2667b.m1321b(false)).m1376a(C2667b.m1321b(true), 8081).m1382b("A1", C2630c.m1059c().m1078k()).m1382b("A2", c2634gM1115e.m1118b()).m1382b("A4", c2634gM1115e.m1122d()).m1382b("A6", c2634gM1115e.m1125f()).m1382b("A7", c2632eM1082l.m1093d()).m1382b("A23", C2630c.m1059c().m1062a(C2630c.m1059c().m1072e())).m1382b("A31", c2632eM1082l.m1104p()).m1382b("A19", c2632eM1082l.m1105q()).m1382b("A66", C2629b.m1055f(this.f1498a) ? "F" : "B").m1382b("A67", C2629b.m1049c(this.f1498a)).m1382b("A68", String.valueOf(C2629b.m1046b(this.f1498a))).m1382b("A85", C2629b.f1072d ? BoolUtil.f541Y : "N").m1378a(C2636i.m1150d()).m1379a()).m1390b(this);
        }
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onResponse(byte[] bArr) {
        m1604a(bArr, true);
        this.f1499b.m1783a(true);
        m1597c();
    }

    /* JADX INFO: renamed from: b */
    public void m1608b() {
        byte[] bArrDecode;
        try {
            try {
                String string = SharedPreferencesC2686a.m1391a().getString("strategy_data", "");
                if (TextUtils.isEmpty(string)) {
                    C2718j c2718jM1609a = C2719k.m1609a(this.f1498a, 101);
                    bArrDecode = c2718jM1609a != null ? c2718jM1609a.f1504c : null;
                } else {
                    bArrDecode = Base64.decode(string, 0);
                }
                if (bArrDecode != null) {
                    m1604a(bArrDecode, false);
                } else {
                    C2695c.m1464a("[strategy] local strategy is null!", new Object[0]);
                }
            } catch (Exception e) {
                C2695c.m1465a(e);
            }
        } finally {
            m1600e();
        }
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    public void onFailure(C2684d c2684d) {
        m1597c();
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f1500c = true;
        if (this.f1499b.m1782a().m1515e()) {
            return;
        }
        m1602g();
    }

    /* JADX INFO: renamed from: c */
    private void m1598c(C2713e c2713e, C2710b c2710b, ModuleStrategy moduleStrategy) {
        ArrayList<String> arrayList = moduleStrategy.sampleEvent;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        C2695c.m1464a("[strategy] mid: %d , SampleEventSet changed...", Byte.valueOf(moduleStrategy.mId));
        this.f1501d = true;
        c2713e.m1579b(C2694b.m1437a(moduleStrategy.sampleEvent));
        c2710b.m1536b(C2694b.m1437a(moduleStrategy.sampleEvent));
    }

    /* JADX INFO: renamed from: a */
    public void m1604a(byte[] bArr, boolean z) {
        try {
            CommonStrategy commonStrategy = new CommonStrategy();
            commonStrategy.readFrom(new C2755a(bArr));
            C2695c.m1464a("[strategy] -> common strategy: %s", commonStrategy);
            if (m1606a(commonStrategy, C2709a.m1508a()) && z) {
                SharedPreferencesC2686a.a aVarEdit = SharedPreferencesC2686a.m1391a().edit();
                if (C2694b.m1441a((SharedPreferences.Editor) aVarEdit)) {
                    aVarEdit.putString("strategy_data", Base64.encodeToString(bArr, 0));
                }
            }
            if (z) {
                C2719k.m1614d();
            }
        } catch (Throwable th) {
            C2695c.m1465a(th);
            C2695c.m1468b("[strategy] error to common strategy!", new Object[0]);
        }
    }

    /* JADX INFO: renamed from: b */
    private void m1596b(C2713e c2713e, C2710b c2710b, ModuleStrategy moduleStrategy) {
        ArrayList<String> arrayList = moduleStrategy.preventEventCode;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        C2695c.m1464a("[strategy] mid: %d , PreventEventCode changed...", Byte.valueOf(moduleStrategy.mId));
        this.f1501d = true;
        c2713e.m1576a(C2694b.m1437a(moduleStrategy.preventEventCode));
        c2710b.m1528a(C2694b.m1437a(moduleStrategy.preventEventCode));
    }

    /* JADX INFO: renamed from: a */
    protected boolean m1606a(CommonStrategy commonStrategy, C2709a c2709a) {
        if (commonStrategy == null || c2709a == null) {
            return false;
        }
        String strM1318a = C2667b.m1318a(commonStrategy.url);
        if (!strM1318a.equals(C2667b.m1321b(true))) {
            C2695c.m1464a("[strategy] url changed to: %s", commonStrategy.url);
            this.f1501d = true;
            C2667b.m1325d(strM1318a);
        }
        if (commonStrategy.queryInterval != c2709a.m1512b()) {
            C2695c.m1464a("[strategy] QueryPeriod changed to: %d", Integer.valueOf(commonStrategy.queryInterval));
            this.f1501d = true;
            c2709a.m1510a(commonStrategy.queryInterval);
        }
        if (m1607a(commonStrategy.moduleList)) {
            this.f1501d = true;
        }
        if (m1595a(commonStrategy.cloudParas, c2709a)) {
            this.f1501d = true;
        }
        return this.f1501d;
    }

    /* JADX INFO: renamed from: a */
    boolean m1607a(ArrayList<ModuleStrategy> arrayList) {
        C2713e c2713eM1514d = this.f1499b.m1782a().m1514d();
        if (arrayList == null) {
            c2713eM1514d.m1577a(false);
            m1599d();
            return false;
        }
        C2710b c2710bM1784b = this.f1499b.m1784b();
        for (ModuleStrategy moduleStrategy : arrayList) {
            if (moduleStrategy.mId == c2713eM1514d.m1578b()) {
                m1593a(c2713eM1514d, moduleStrategy);
                m1594a(moduleStrategy);
                m1592a(c2713eM1514d, c2710bM1784b, moduleStrategy);
                m1596b(c2713eM1514d, c2710bM1784b, moduleStrategy);
                m1598c(c2713eM1514d, c2710bM1784b, moduleStrategy);
            }
        }
        return this.f1501d;
    }

    /* JADX INFO: renamed from: a */
    private void m1592a(C2713e c2713e, C2710b c2710b, ModuleStrategy moduleStrategy) {
        if (moduleStrategy.detail != null) {
            C2695c.m1464a("[strategy] mid: %d , detail changed...", Byte.valueOf(moduleStrategy.mId));
            c2713e.m1575a(moduleStrategy.detail);
            c2710b.m1527a(moduleStrategy.detail);
            this.f1501d = true;
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1593a(C2713e c2713e, ModuleStrategy moduleStrategy) {
        boolean z = moduleStrategy.onOff == 1;
        if (c2713e.m1580c() != z) {
            C2695c.m1464a("[strategy] mid: %d , isUsable changed: %b ", Byte.valueOf(moduleStrategy.mId), Boolean.valueOf(z));
            c2713e.m1577a(z);
            this.f1501d = true;
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1594a(ModuleStrategy moduleStrategy) {
        String strM1318a = C2667b.m1318a(moduleStrategy.url);
        if (C2667b.m1319a(true).equals(strM1318a)) {
            return;
        }
        C2695c.m1464a("[strategy] mid: %d , url changed: %s", Byte.valueOf(moduleStrategy.mId), moduleStrategy.url);
        C2667b.m1322b(strM1318a);
        this.f1501d = true;
    }

    /* JADX INFO: renamed from: a */
    private boolean m1595a(Map<String, String> map, C2709a c2709a) {
        if (c2709a == null || map == null) {
            return false;
        }
        c2709a.m1511a(map);
        return true;
    }

    /* JADX INFO: renamed from: a */
    public boolean m1605a() {
        return this.f1500c;
    }
}
