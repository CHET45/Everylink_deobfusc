package com.tencent.beacon.event.p030b;

import com.tencent.beacon.event.EventBean;
import com.tencent.beacon.event.open.BeaconEvent;
import com.tencent.beacon.event.p031c.C2729a;

/* JADX INFO: renamed from: com.tencent.beacon.event.b.c */
/* JADX INFO: compiled from: EventHandler.java */
/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractC2727c {

    /* JADX INFO: renamed from: a */
    private AbstractC2727c f1532a;

    /* JADX INFO: renamed from: a */
    abstract BeaconEvent mo1624a(BeaconEvent beaconEvent);

    /* JADX INFO: renamed from: a */
    public void m1626a(AbstractC2727c abstractC2727c) {
        this.f1532a = abstractC2727c;
    }

    /* JADX INFO: renamed from: b */
    public final EventBean m1627b(BeaconEvent beaconEvent) {
        BeaconEvent beaconEventMo1624a = mo1624a(beaconEvent);
        AbstractC2727c abstractC2727c = this.f1532a;
        return abstractC2727c != null ? abstractC2727c.m1627b(beaconEventMo1624a) : C2729a.m1628a(beaconEventMo1624a);
    }
}
