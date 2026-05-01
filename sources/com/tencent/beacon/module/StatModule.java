package com.tencent.beacon.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import com.github.houbb.heaven.util.lang.BoolUtil;
import com.tencent.beacon.base.store.SharedPreferencesC2686a;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.event.open.BeaconEvent;
import com.tencent.beacon.event.open.BeaconReport;
import com.tencent.beacon.event.open.EventType;
import com.tencent.beacon.p015a.p016a.C2612b;
import com.tencent.beacon.p015a.p016a.C2613c;
import com.tencent.beacon.p015a.p016a.InterfaceC2614d;
import com.tencent.beacon.p015a.p017b.AbstractC2616b;
import com.tencent.beacon.p015a.p018c.C2629b;
import com.tencent.beacon.p015a.p018c.C2632e;
import com.tencent.beacon.p015a.p018c.C2634g;
import com.tencent.beacon.p026c.C2708c;
import com.tencent.beacon.p026c.p027a.C2705c;
import com.tencent.beacon.p026c.p027a.C2706d;
import com.tencent.beacon.p028d.C2710b;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class StatModule implements BeaconModule, InterfaceC2614d {

    /* JADX INFO: renamed from: a */
    private Context f1723a;

    /* JADX INFO: renamed from: d */
    private C2710b f1726d;

    /* JADX INFO: renamed from: b */
    private boolean f1724b = true;

    /* JADX INFO: renamed from: c */
    private boolean f1725c = true;

    /* JADX INFO: renamed from: e */
    private long f1727e = 0;

    /* JADX INFO: renamed from: com.tencent.beacon.module.StatModule$4 */
    class RunnableC27514 implements Runnable {

        /* JADX INFO: renamed from: a */
        final /* synthetic */ Map f1737a;

        @Override // java.lang.Runnable
        public void run() {
            BeaconReport.getInstance().report(BeaconEvent.builder().withCode("rqd_sensor").withParams(this.f1737a).build());
        }
    }

    /* JADX INFO: renamed from: com.tencent.beacon.module.StatModule$5 */
    class RunnableC27525 implements Runnable {

        /* JADX INFO: renamed from: a */
        final /* synthetic */ Map f1738a;

        @Override // java.lang.Runnable
        public void run() {
            BeaconReport.getInstance().report(BeaconEvent.builder().withCode("rqd_appresumed").withIsSucceed(true).withParams(this.f1738a).withType(EventType.REALTIME).build());
        }
    }

    /* JADX INFO: renamed from: b */
    static /* synthetic */ long m1769b(StatModule statModule, long j) {
        long j2 = statModule.f1727e + j;
        statModule.f1727e = j2;
        return j2;
    }

    /* JADX INFO: renamed from: c */
    private void m1770c() {
        ((Application) this.f1723a).registerActivityLifecycleCallbacks(new C2706d(this));
    }

    /* JADX INFO: renamed from: d */
    private void m1771d() {
        if (this.f1726d.m1568v()) {
            SharedPreferencesC2686a sharedPreferencesC2686aM1391a = SharedPreferencesC2686a.m1391a();
            if (C2694b.m1458e().equals(sharedPreferencesC2686aM1391a.getString("rqd_model", ""))) {
                return;
            }
            AbstractC2616b.m1001a().mo1010a(50000L, new Runnable() { // from class: com.tencent.beacon.module.StatModule.1
                @Override // java.lang.Runnable
                public void run() {
                    C2632e c2632eM1082l = C2632e.m1082l();
                    C2634g c2634gM1115e = C2634g.m1115e();
                    BeaconEvent.Builder builderWithParams = BeaconEvent.builder().withParams("A9", Build.BRAND).withParams("A10", c2634gM1115e.m1129h()).withParams("A11", c2632eM1082l.m1096g()).withParams("A12", c2632eM1082l.m1102n()).withParams("A13", c2632eM1082l.m1083A()).withParams("A14", c2632eM1082l.m1112x() + "m").withParams("A15", c2632eM1082l.m1109u() + "m").withParams("A16", c2632eM1082l.m1097h()).withParams("A17", c2632eM1082l.m1110v()).withParams("A18", "").withParams("A20", c2634gM1115e.m1133j()).withParams("A30", c2632eM1082l.m1113y() + "m").withParams("A19", c2632eM1082l.m1105q()).withParams("A52", "" + c2632eM1082l.m1114z()).withParams("A53", "" + c2632eM1082l.m1094e() + "m").withParams("A54", "" + c2632eM1082l.m1107s()).withParams("A55", c2632eM1082l.m1095f());
                    boolean zM1087E = c2632eM1082l.m1087E();
                    String str = BoolUtil.f541Y;
                    BeaconEvent.Builder builderWithParams2 = builderWithParams.withParams("A56", zM1087E ? BoolUtil.f541Y : "N").withParams("A57", c2632eM1082l.m1084B());
                    if (!c2632eM1082l.m1101m()) {
                        str = "N";
                    }
                    BeaconReport.getInstance().report(builderWithParams2.withParams("A58", str).withParams("A59", c2632eM1082l.m1100k() + "m").withParams("A69", c2634gM1115e.m1135k()).withParams("A82", c2632eM1082l.m1111w()).withType(EventType.REALTIME).withCode("rqd_model").build());
                }
            });
            SharedPreferencesC2686a.a aVarEdit = sharedPreferencesC2686aM1391a.edit();
            if (C2694b.m1441a((SharedPreferences.Editor) aVarEdit)) {
                aVarEdit.putString("rqd_model", C2694b.m1458e());
            }
        }
    }

    /* JADX INFO: renamed from: e */
    private void m1772e() {
        if (this.f1726d.m1568v()) {
            if (this.f1726d.m1565s()) {
                if (C2694b.m1458e().equals(SharedPreferencesC2686a.m1391a().getString("LAUEVE_DENGTA", ""))) {
                    C2695c.m1474d("[event] APP_LAUNCHED_EVENT has been uploaded!", new Object[0]);
                    return;
                }
            }
            C2632e c2632eM1082l = C2632e.m1082l();
            HashMap map = new HashMap();
            map.put("A19", c2632eM1082l.m1105q());
            String str = BoolUtil.f541Y;
            map.put("A63", BoolUtil.f541Y);
            map.put("A21", C2629b.m1056g() ? BoolUtil.f541Y : "N");
            map.put("A45", C2629b.m1053e(this.f1723a) ? BoolUtil.f541Y : "N");
            map.put("A66", C2629b.m1055f(this.f1723a) ? "F" : "B");
            map.put("A68", "" + C2629b.m1046b(this.f1723a));
            if (!C2629b.f1072d) {
                str = "N";
            }
            map.put("A85", str);
            map.put("A9", Build.BRAND);
            map.put("A14", c2632eM1082l.m1112x());
            C2634g c2634gM1115e = C2634g.m1115e();
            map.put("A20", c2634gM1115e.m1133j());
            map.put("A69", c2634gM1115e.m1135k());
            if (m1775a(map)) {
                SharedPreferencesC2686a.a aVarEdit = SharedPreferencesC2686a.m1391a().edit();
                if (C2694b.m1441a((SharedPreferences.Editor) aVarEdit)) {
                    aVarEdit.putString("LAUEVE_DENGTA", C2694b.m1458e());
                }
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1773a() {
    }

    /* JADX INFO: renamed from: b */
    public void m1776b() {
    }

    /* JADX INFO: renamed from: b */
    public void m1777b(final String str, final long j, final long j2) {
        if (this.f1726d.m1568v()) {
            AbstractC2616b.m1001a().mo1011a(new Runnable() { // from class: com.tencent.beacon.module.StatModule.3
                @Override // java.lang.Runnable
                public void run() {
                    BeaconReport.getInstance().report(BeaconEvent.builder().withParams("A110", String.valueOf(j2)).withParams("A111", str).withParams("A112", String.valueOf(j)).withCode("rqd_page").withType(EventType.NORMAL).build());
                    StatModule.m1769b(StatModule.this, j);
                    if (StatModule.this.f1727e >= 15000) {
                        StatModule.this.f1727e = 0L;
                    }
                }
            });
        }
    }

    @Override // com.tencent.beacon.module.BeaconModule
    /* JADX INFO: renamed from: a */
    public void mo1746a(Context context) {
        this.f1723a = context;
        if (!C2629b.m1057g(context)) {
            C2695c.m1464a("non-main process. do not report rqd event", new Object[0]);
            return;
        }
        C2710b c2710bM1784b = ((StrategyModule) BeaconModule.f1707a.get(ModuleName.STRATEGY)).m1784b();
        this.f1726d = c2710bM1784b;
        this.f1725c = c2710bM1784b.m1569w();
        if (this.f1726d.m1567u()) {
            ((Application) this.f1723a).registerActivityLifecycleCallbacks(new C2705c(this));
        }
        C2612b.m991a().m997a(2, this);
        C2612b.m991a().m997a(10, this);
    }

    /* JADX INFO: renamed from: b */
    public boolean m1778b(Map<String, String> map) {
        if (!this.f1726d.m1568v()) {
            return true;
        }
        return BeaconReport.getInstance().report(BeaconEvent.builder().withParams(map).withCode("rqd_heartbeat").withType(EventType.REALTIME).build()).isSuccess();
    }

    @Override // com.tencent.beacon.p015a.p016a.InterfaceC2614d
    /* JADX INFO: renamed from: a */
    public void mo1000a(C2613c c2613c) {
        int i = c2613c.f1020a;
        if (i == 2) {
            Map map = (Map) c2613c.f1021b.get("d_m");
            if (map != null) {
                this.f1724b = C2694b.m1443a((String) map.get("modelEventUsable"), this.f1724b);
                this.f1725c = C2694b.m1443a((String) map.get("isPagePath"), this.f1725c);
                return;
            }
            return;
        }
        if (i != 10) {
            return;
        }
        m1772e();
        if (C2629b.m1057g(this.f1723a)) {
            new C2708c(this.f1723a).m1507a(this.f1726d);
        }
        if (this.f1724b) {
            m1771d();
        }
        if (this.f1725c) {
            m1770c();
        }
    }

    /* JADX INFO: renamed from: a */
    public boolean m1775a(Map<String, String> map) {
        if (!this.f1726d.m1568v()) {
            return true;
        }
        return BeaconReport.getInstance().report(BeaconEvent.builder().withCode("rqd_applaunched").withParams(map).withType(EventType.REALTIME).build()).isSuccess();
    }

    /* JADX INFO: renamed from: a */
    public void m1774a(final String str, final long j, final long j2) {
        if (this.f1726d.m1568v()) {
            AbstractC2616b.m1001a().mo1011a(new Runnable() { // from class: com.tencent.beacon.module.StatModule.2
                @Override // java.lang.Runnable
                public void run() {
                    BeaconReport.getInstance().report(BeaconEvent.builder().withParams("A110", String.valueOf(j2)).withParams("A111", str).withParams("A112", String.valueOf(j)).withCode("rqd_page_fgt").withType(EventType.REALTIME).build());
                }
            });
        }
    }
}
