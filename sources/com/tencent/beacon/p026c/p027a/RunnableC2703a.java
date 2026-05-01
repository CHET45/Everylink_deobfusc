package com.tencent.beacon.p026c.p027a;

import com.github.houbb.heaven.util.lang.BoolUtil;
import com.tencent.beacon.p015a.p018c.C2629b;
import com.tencent.beacon.p015a.p018c.C2632e;
import com.tencent.beacon.p015a.p018c.C2634g;

/* JADX INFO: renamed from: com.tencent.beacon.c.a.a */
/* JADX INFO: compiled from: LifecycleCallbacks.java */
/* JADX INFO: loaded from: classes4.dex */
class RunnableC2703a implements Runnable {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ C2705c f1402a;

    RunnableC2703a(C2705c c2705c) {
        this.f1402a = c2705c;
    }

    @Override // java.lang.Runnable
    public void run() {
        C2632e c2632eM1082l = C2632e.m1082l();
        C2634g c2634gM1115e = C2634g.m1115e();
        this.f1402a.f1411g.put("A19", c2632eM1082l.m1105q());
        this.f1402a.f1411g.put("A85", C2629b.f1072d ? BoolUtil.f541Y : "N");
        this.f1402a.f1411g.put("A20", c2634gM1115e.m1133j());
        this.f1402a.f1411g.put("A69", c2634gM1115e.m1135k());
        this.f1402a.f1412h.m1775a(this.f1402a.f1411g);
    }
}
