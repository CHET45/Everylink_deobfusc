package com.tencent.beacon.event.p030b;

import com.tencent.beacon.event.open.BeaconEvent;
import com.tencent.beacon.event.open.EventType;
import com.tencent.beacon.p015a.p018c.C2632e;
import com.tencent.beacon.p015a.p018c.C2634g;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.beacon.event.b.a */
/* JADX INFO: compiled from: DTEventHandler.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2725a extends AbstractC2727c {
    @Override // com.tencent.beacon.event.p030b.AbstractC2727c
    /* JADX INFO: renamed from: a */
    protected BeaconEvent mo1624a(BeaconEvent beaconEvent) {
        EventType type = beaconEvent.getType();
        if (type == EventType.DT_REALTIME || type == EventType.DT_NORMAL) {
            Map<String, String> params = beaconEvent.getParams();
            C2632e c2632eM1082l = C2632e.m1082l();
            C2634g c2634gM1115e = C2634g.m1115e();
            params.put("dt_imei2", "" + c2634gM1115e.m1120c());
            params.put("dt_meid", "" + c2634gM1115e.m1127g());
            params.put("dt_mf", "" + c2632eM1082l.m1103o());
            beaconEvent.setParams(params);
        }
        return beaconEvent;
    }
}
