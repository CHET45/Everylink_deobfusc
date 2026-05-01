package com.tencent.beacon.p015a.p017b;

import com.tencent.beacon.base.util.C2695c;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: renamed from: com.tencent.beacon.a.b.m */
/* JADX INFO: compiled from: BeaconThreadFactory.java */
/* JADX INFO: loaded from: classes4.dex */
public final class ThreadFactoryC2627m implements ThreadFactory {

    /* JADX INFO: renamed from: a */
    private final AtomicInteger f1065a = new AtomicInteger(1);

    /* JADX INFO: renamed from: a */
    public String m1041a() {
        return "beacon-thread-" + this.f1065a.getAndIncrement();
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        try {
            return new Thread(runnable, m1041a());
        } catch (Exception e) {
            C2695c.m1465a(e);
            return null;
        } catch (OutOfMemoryError unused) {
            C2695c.m1468b("[task] memory not enough, create thread failed.", new Object[0]);
            return null;
        }
    }
}
