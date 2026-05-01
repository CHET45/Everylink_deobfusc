package com.tencent.beacon.event;

import com.tencent.beacon.base.net.adapter.C2651g;
import com.tencent.beacon.base.net.p021b.C2667b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.event.open.BeaconReport;
import com.tencent.beacon.event.p031c.C2732d;
import com.tencent.beacon.event.quic.BeaconQuicReportCallback;
import com.tencent.beacon.event.quic.QuicTransArgs;
import com.tencent.beacon.p015a.p017b.C2624j;
import com.tencent.beacon.p028d.C2710b;

/* JADX INFO: renamed from: com.tencent.beacon.event.c */
/* JADX INFO: compiled from: EventManager.java */
/* JADX INFO: loaded from: classes4.dex */
class RunnableC2728c implements Runnable {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ EventBean f1533a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ String f1534b;

    /* JADX INFO: renamed from: c */
    final /* synthetic */ C2737e f1535c;

    RunnableC2728c(C2737e c2737e, EventBean eventBean, String str) {
        this.f1535c = c2737e;
        this.f1533a = eventBean;
        this.f1534b = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            BeaconReport.getInstance().getBeaconQuicReport().reportByQuic(new QuicTransArgs(C2667b.m1317a(), this.f1533a, C2651g.m1173a().m1175a(C2732d.m1634a(this.f1533a))), new BeaconQuicReportCallback(this.f1535c, this.f1533a, this.f1534b));
        } catch (Throwable th) {
            String str = "[quic] report error! msg: " + th.getMessage();
            C2695c.m1468b(str, new Object[0]);
            C2695c.m1465a(th);
            this.f1535c.m1674a(this.f1533a, this.f1534b);
            C2624j.m1031e().m1024a(C2710b.m1518d().m1544e() == 1 ? "471" : "474", str, th);
        }
    }
}
