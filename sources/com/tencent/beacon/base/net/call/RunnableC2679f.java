package com.tencent.beacon.base.net.call;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.call.f */
/* JADX INFO: compiled from: JceCall.java */
/* JADX INFO: loaded from: classes4.dex */
class RunnableC2679f implements Runnable {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ Callback f1355a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ C2682i f1356b;

    RunnableC2679f(C2682i c2682i, Callback callback) {
        this.f1356b = c2682i;
        this.f1355a = callback;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f1356b.m1390b(this.f1355a);
    }
}
