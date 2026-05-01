package com.tencent.beacon.p026c;

import android.content.Context;
import android.content.SharedPreferences;
import com.github.houbb.heaven.util.lang.BoolUtil;
import com.tencent.beacon.base.store.SharedPreferencesC2686a;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.module.ModuleName;
import com.tencent.beacon.module.StatModule;
import com.tencent.beacon.p015a.p017b.AbstractC2616b;
import com.tencent.beacon.p015a.p018c.C2629b;
import com.tencent.beacon.p015a.p018c.C2630c;
import com.tencent.beacon.p015a.p018c.C2632e;
import com.tencent.beacon.p015a.p018c.C2634g;
import com.tencent.beacon.p028d.C2710b;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.beacon.c.c */
/* JADX INFO: compiled from: Heartbeat.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2708c {

    /* JADX INFO: renamed from: a */
    protected final Context f1419a;

    /* JADX INFO: renamed from: b */
    private final boolean f1420b;

    /* JADX INFO: renamed from: c */
    private boolean f1421c = false;

    public C2708c(Context context) {
        this.f1419a = context;
        this.f1420b = C2629b.m1055f(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: c */
    public boolean m1505c() {
        return ((StatModule) C2630c.m1059c().m1060a(ModuleName.STAT)).m1778b(m1503b());
    }

    /* JADX INFO: renamed from: b */
    private Map<String, String> m1503b() {
        this.f1421c = C2629b.f1072d;
        HashMap map = new HashMap(8);
        C2632e c2632eM1082l = C2632e.m1082l();
        C2634g c2634gM1115e = C2634g.m1115e();
        map.put("A19", c2632eM1082l.m1105q());
        map.put("A66", C2629b.m1055f(this.f1419a) ? "F" : "B");
        map.put("A68", "" + C2629b.m1046b(this.f1419a));
        map.put("A85", this.f1421c ? BoolUtil.f541Y : "N");
        map.put("A20", c2634gM1115e.m1133j());
        map.put("A69", c2634gM1115e.m1135k());
        return map;
    }

    /* JADX INFO: renamed from: a */
    public void m1507a(C2710b c2710b) {
        String strM1458e = C2694b.m1458e();
        SharedPreferencesC2686a sharedPreferencesC2686aM1391a = SharedPreferencesC2686a.m1391a();
        String string = sharedPreferencesC2686aM1391a.getString("HEART_DENGTA", "");
        String string2 = sharedPreferencesC2686aM1391a.getString("active_user_date", "");
        if (strM1458e.equals(string) || string2.equals(strM1458e)) {
            C2695c.m1476e("[event] heartbeat had upload!", new Object[0]);
            return;
        }
        if (c2710b.m1532a("rqd_heartbeat")) {
            C2695c.m1476e("[event] rqd_heartbeat not allowed in strategy!", new Object[0]);
        } else if (c2710b.m1538b("rqd_heartbeat")) {
            AbstractC2616b.m1001a().mo1011a(new RunnableC2707b(this, strM1458e, sharedPreferencesC2686aM1391a));
        } else {
            C2695c.m1476e("[event] rqd_heartbeat is sampled by svr rate!", new Object[0]);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1506a() {
        SharedPreferencesC2686a sharedPreferencesC2686aM1391a = SharedPreferencesC2686a.m1391a();
        if (!C2694b.m1458e().equals(sharedPreferencesC2686aM1391a.getString("active_user_date", ""))) {
            C2695c.m1464a("[event] recover a heart beat for active user.", new Object[0]);
            if (m1505c()) {
                C2695c.m1464a("[event] rqd_heartbeat A85=Y report success", new Object[0]);
                SharedPreferencesC2686a.a aVarEdit = sharedPreferencesC2686aM1391a.edit();
                if (C2694b.m1441a((SharedPreferences.Editor) aVarEdit)) {
                    aVarEdit.putString("active_user_date", C2694b.m1458e()).apply();
                    return;
                }
                return;
            }
            return;
        }
        C2695c.m1476e("[event] active user event had upload.", new Object[0]);
    }
}
