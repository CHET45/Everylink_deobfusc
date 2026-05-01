package com.tencent.beacon.event.open;

import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.p015a.p017b.C2624j;
import com.tencent.beacon.p015a.p018c.C2629b;
import com.tencent.beacon.p015a.p018c.C2630c;
import com.tencent.beacon.p015a.p018c.C2636i;

/* JADX INFO: renamed from: com.tencent.beacon.event.open.b */
/* JADX INFO: compiled from: BeaconReport.java */
/* JADX INFO: loaded from: classes4.dex */
class RunnableC2744b implements Runnable {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ BeaconConfig f1704a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ BeaconReport f1705b;

    RunnableC2744b(BeaconReport beaconReport, BeaconConfig beaconConfig) {
        this.f1705b = beaconReport;
        this.f1704a = beaconConfig;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            C2636i.m1143a();
            C2636i.m1153g();
            this.f1705b.m1738a(this.f1704a);
            this.f1705b.m1736a();
            C2695c.m1463a("BeaconReport", "App: %s start success!", C2630c.m1059c().m1072e());
        } catch (Throwable th) {
            C2624j.m1031e().m1024a("201", "sdk init error! package name: " + C2629b.m1047b() + " , msg:" + th.getMessage(), th);
            C2695c.m1468b("BeaconReport init error: " + th.getMessage(), new Object[0]);
            C2695c.m1465a(th);
        }
    }
}
