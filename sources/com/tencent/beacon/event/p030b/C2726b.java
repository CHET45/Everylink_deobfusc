package com.tencent.beacon.event.p030b;

import android.content.Context;
import android.text.TextUtils;
import com.github.houbb.heaven.util.lang.BoolUtil;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.event.open.BeaconEvent;
import com.tencent.beacon.event.open.EventType;
import com.tencent.beacon.event.p031c.C2735g;
import com.tencent.beacon.module.EventModule;
import com.tencent.beacon.module.ModuleName;
import com.tencent.beacon.p015a.p016a.C2612b;
import com.tencent.beacon.p015a.p016a.C2613c;
import com.tencent.beacon.p015a.p016a.InterfaceC2614d;
import com.tencent.beacon.p015a.p018c.C2629b;
import com.tencent.beacon.p015a.p018c.C2630c;
import com.tencent.beacon.p015a.p018c.C2632e;
import com.tencent.beacon.p015a.p018c.C2636i;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.beacon.event.b.b */
/* JADX INFO: compiled from: DefEventHandler.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2726b extends AbstractC2727c implements InterfaceC2614d {

    /* JADX INFO: renamed from: b */
    private boolean f1530b = false;

    /* JADX INFO: renamed from: c */
    private String f1531c;

    public C2726b() {
        C2612b.m991a().m997a(2, this);
    }

    /* JADX INFO: renamed from: c */
    private boolean m1625c(BeaconEvent beaconEvent) {
        return beaconEvent.getType() == EventType.IMMEDIATE_MSF || beaconEvent.getType() == EventType.IMMEDIATE;
    }

    @Override // com.tencent.beacon.event.p030b.AbstractC2727c
    /* JADX INFO: renamed from: a */
    BeaconEvent mo1624a(BeaconEvent beaconEvent) {
        if (this.f1530b && !C2694b.m1442a(beaconEvent.getCode())) {
            return beaconEvent;
        }
        C2630c c2630cM1059c = C2630c.m1059c();
        Context contextM1067b = c2630cM1059c.m1067b();
        Map<String, String> params = beaconEvent.getParams();
        params.put("A3", C2636i.m1147b());
        params.put("A153", C2636i.m1149c());
        params.put("A157", C2636i.m1151e());
        params.put("A19", C2632e.m1082l().m1105q());
        params.put("A95", "" + C2629b.m1042a());
        params.put("A48", C2630c.m1059c().m1070d());
        params.put("A99", beaconEvent.getLogidPrefix());
        params.put("A72", c2630cM1059c.m1076i());
        if (!TextUtils.isEmpty(C2630c.m1059c().m1073f())) {
            params.put("A143", C2630c.m1059c().m1073f());
        }
        if (!TextUtils.isEmpty(C2629b.m1048c())) {
            params.put("QQ", C2629b.m1048c());
        }
        if (TextUtils.isEmpty(C2636i.m1149c())) {
            params.put("A141", C2636i.m1152f());
        }
        String appKey = beaconEvent.getAppKey();
        EventModule eventModule = (EventModule) c2630cM1059c.m1060a(ModuleName.EVENT);
        if (!TextUtils.isEmpty(c2630cM1059c.m1062a(appKey))) {
            params.put("A23", c2630cM1059c.m1062a(appKey));
        }
        if (!TextUtils.isEmpty(eventModule.m1763c(appKey))) {
            params.put("A1", eventModule.m1763c(appKey));
        }
        if (!TextUtils.isEmpty(eventModule.m1760b(appKey))) {
            params.put("A8", eventModule.m1760b(appKey));
        }
        params.put("A34", String.valueOf(C2694b.m1457d()));
        params.put("A156", m1625c(beaconEvent) ? BoolUtil.f541Y : "N");
        if (!params.containsKey("A88")) {
            if (TextUtils.isEmpty(this.f1531c)) {
                this.f1531c = C2629b.m1043a(contextM1067b);
            }
            params.put("A88", this.f1531c);
        }
        params.put("A100", C2735g.m1649a(contextM1067b, appKey).m1667a(beaconEvent.getCode(), beaconEvent.getType()));
        Map<String, String> mapM1756a = eventModule.m1756a(appKey);
        if (mapM1756a != null) {
            params.putAll(mapM1756a);
        }
        beaconEvent.setParams(params);
        return beaconEvent;
    }

    @Override // com.tencent.beacon.p015a.p016a.InterfaceC2614d
    /* JADX INFO: renamed from: a */
    public void mo1000a(C2613c c2613c) {
        HashMap map;
        if (c2613c.f1020a == 2 && (map = (HashMap) c2613c.f1021b.get("d_m")) != null) {
            this.f1530b = C2694b.m1443a((String) map.get("tidyEF"), this.f1530b);
        }
    }
}
