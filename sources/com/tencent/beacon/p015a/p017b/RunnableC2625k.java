package com.tencent.beacon.p015a.p017b;

import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.base.util.C2700h;

/* JADX INFO: renamed from: com.tencent.beacon.a.b.k */
/* JADX INFO: compiled from: BeaconAsyncTask.java */
/* JADX INFO: loaded from: classes4.dex */
class RunnableC2625k implements Runnable {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ Runnable f1050a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ C2626l f1051b;

    RunnableC2625k(C2626l c2626l, Runnable runnable) {
        this.f1051b = c2626l;
        this.f1050a = runnable;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.f1050a.run();
        } catch (Throwable th) {
            if (C2626l.f1054f.addAndGet(1) < 100) {
                C2624j.m1031e().m1024a("599", "[task] run occur error!", th);
            }
            C2700h.m1485a(th.getMessage());
            C2695c.m1465a(th);
        }
    }
}
