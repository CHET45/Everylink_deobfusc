package com.tencent.beacon.event.p031c;

import android.text.TextUtils;
import com.tencent.beacon.base.net.p021b.C2669d;
import com.tencent.beacon.event.EventBean;
import com.tencent.beacon.event.open.BeaconEvent;
import com.tencent.beacon.p015a.p018c.C2630c;
import com.tencent.beacon.p015a.p018c.C2632e;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.beacon.event.c.a */
/* JADX INFO: compiled from: EventBeanParser.java */
/* JADX INFO: loaded from: classes4.dex */
public final class C2729a {
    /* JADX INFO: renamed from: a */
    public static EventBean m1628a(BeaconEvent beaconEvent) {
        Map<String, String> mapM1334a = C2669d.m1334a(beaconEvent.getCode(), beaconEvent.getParams());
        if (mapM1334a == null) {
            return null;
        }
        EventBean eventBean = new EventBean();
        String code = beaconEvent.getCode();
        eventBean.setEventCode(code);
        eventBean.setAppKey(beaconEvent.getAppKey());
        eventBean.setApn(C2632e.m1082l().m1106r());
        eventBean.setSrcIp(C2630c.m1059c().m1061a());
        eventBean.setEventCode(code);
        eventBean.setValueType(0);
        eventBean.setEventValue(mapM1334a);
        String str = beaconEvent.getParams().get("A34");
        if (!TextUtils.isEmpty(str)) {
            eventBean.setEventTime(Long.parseLong(str));
        }
        eventBean.setEventResult(beaconEvent.isSucceed());
        eventBean.setEventType(C2732d.m1632a(beaconEvent.getType()));
        eventBean.setReserved("");
        return eventBean;
    }
}
