package com.tencent.beacon.base.net.call;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.call.g */
/* JADX INFO: compiled from: JceCall.java */
/* JADX INFO: loaded from: classes4.dex */
class RunnableC2680g implements Runnable {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ Callback f1357a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ C2682i f1358b;

    RunnableC2680g(C2682i c2682i, Callback callback) {
        this.f1358b = c2682i;
        this.f1357a = callback;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f1358b.m1390b(this.f1357a);
    }
}
