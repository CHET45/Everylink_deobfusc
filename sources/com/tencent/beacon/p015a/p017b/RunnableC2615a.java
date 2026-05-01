package com.tencent.beacon.p015a.p017b;

import com.tencent.beacon.base.net.C2671c;
import com.tencent.beacon.p028d.C2710b;

/* JADX INFO: renamed from: com.tencent.beacon.a.b.a */
/* JADX INFO: compiled from: AbsAsyncTask.java */
/* JADX INFO: loaded from: classes4.dex */
class RunnableC2615a implements Runnable {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ AbstractC2616b f1022a;

    RunnableC2615a(AbstractC2616b abstractC2616b) {
        this.f1022a = abstractC2616b;
    }

    @Override // java.lang.Runnable
    public void run() {
        C2671c.m1351d().m1356a(C2710b.m1518d().m1549g() - 1);
        this.f1022a.mo1009a(0L, 1);
        this.f1022a.f1025c = false;
    }
}
