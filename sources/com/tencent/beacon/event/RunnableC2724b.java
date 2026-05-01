package com.tencent.beacon.event;

import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.event.immediate.Beacon2MsfTransferArgs;
import com.tencent.beacon.event.immediate.BeaconImmediateReportCallback;
import com.tencent.beacon.event.open.BeaconReport;
import com.tencent.beacon.event.p031c.C2732d;
import com.tencent.beacon.p015a.p017b.C2624j;

/* JADX INFO: renamed from: com.tencent.beacon.event.b */
/* JADX INFO: compiled from: EventManager.java */
/* JADX INFO: loaded from: classes4.dex */
class RunnableC2724b implements Runnable {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ EventBean f1527a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ String f1528b;

    /* JADX INFO: renamed from: c */
    final /* synthetic */ C2737e f1529c;

    RunnableC2724b(C2737e c2737e, EventBean eventBean, String str) {
        this.f1529c = c2737e;
        this.f1527a = eventBean;
        this.f1528b = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            BeaconReport.getInstance().getImmediateReport().reportImmediate(new Beacon2MsfTransferArgs(C2732d.m1634a(this.f1527a).toByteArray()), new BeaconImmediateReportCallback(this.f1529c, this.f1527a, this.f1528b));
        } catch (Throwable th) {
            C2695c.m1468b("[immediate] report error!", new Object[0]);
            C2695c.m1465a(th);
            this.f1529c.m1674a(this.f1527a, this.f1528b);
            C2624j.m1031e().m1024a("515", "immediate report error!", th);
        }
    }
}
