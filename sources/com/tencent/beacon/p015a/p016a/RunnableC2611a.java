package com.tencent.beacon.p015a.p016a;

/* JADX INFO: renamed from: com.tencent.beacon.a.a.a */
/* JADX INFO: compiled from: BeaconBus.java */
/* JADX INFO: loaded from: classes4.dex */
class RunnableC2611a implements Runnable {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ C2613c f1012a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ C2612b f1013b;

    RunnableC2611a(C2612b c2612b, C2613c c2613c) {
        this.f1013b = c2612b;
        this.f1012a = c2613c;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f1013b.m999b(this.f1012a);
    }
}
