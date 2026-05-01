package com.tencent.beacon.event;

import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.event.p031c.C2732d;
import com.tencent.beacon.p015a.p017b.C2624j;

/* JADX INFO: renamed from: com.tencent.beacon.event.a */
/* JADX INFO: compiled from: EventManager.java */
/* JADX INFO: loaded from: classes4.dex */
class RunnableC2721a implements Runnable {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ EventBean f1510a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ C2737e f1511b;

    RunnableC2721a(C2737e c2737e, EventBean eventBean) {
        this.f1511b = c2737e;
        this.f1510a = eventBean;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f1511b.f1578b.m1620a(this.f1510a.getEventType())) {
            C2624j.m1031e().m1023a("602", "type: " + C2732d.m1640a(this.f1510a.getEventType()) + " max db count!");
            C2695c.m1461a("[EventModule]", 2, "event: %s. insert to DB false. reason: DB count max!", this.f1510a.getEventCode());
            return;
        }
        boolean zM1621a = this.f1511b.f1578b.m1621a(this.f1510a);
        C2695c.m1461a("[EventModule]", 2, "event: %s. insert to DB %s", this.f1510a.getEventCode(), Boolean.valueOf(zM1621a));
        if (zM1621a) {
            C2695c.m1464a("insert success, resumedPollingReport.", new Object[0]);
            this.f1511b.mo1673a(0);
        }
    }
}
