package com.tencent.beacon.p015a.p017b;

import android.os.Handler;
import java.util.concurrent.ScheduledExecutorService;

/* JADX INFO: renamed from: com.tencent.beacon.a.b.b */
/* JADX INFO: compiled from: AbsAsyncTask.java */
/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractC2616b {

    /* JADX INFO: renamed from: a */
    protected static volatile AbstractC2616b f1023a;

    /* JADX INFO: renamed from: b */
    protected int f1024b = 0;

    /* JADX INFO: renamed from: c */
    private boolean f1025c = false;

    /* JADX INFO: renamed from: com.tencent.beacon.a.b.b$a */
    /* JADX INFO: compiled from: AbsAsyncTask.java */
    private static class a {

        /* JADX INFO: renamed from: a */
        static final AbstractC2616b f1026a = new C2626l();
    }

    /* JADX INFO: renamed from: b */
    public static AbstractC2616b m1004b() {
        return a.f1026a;
    }

    /* JADX INFO: renamed from: a */
    public abstract Handler mo1005a(int i);

    /* JADX INFO: renamed from: a */
    public abstract void mo1006a(int i, long j, int i2);

    /* JADX INFO: renamed from: a */
    public abstract void mo1007a(int i, long j, long j2, Runnable runnable);

    /* JADX INFO: renamed from: a */
    public abstract void mo1008a(int i, boolean z, int i2);

    /* JADX INFO: renamed from: a */
    public abstract void mo1009a(long j, int i);

    /* JADX INFO: renamed from: a */
    public abstract void mo1010a(long j, Runnable runnable);

    /* JADX INFO: renamed from: a */
    public abstract void mo1011a(Runnable runnable);

    /* JADX INFO: renamed from: a */
    public abstract void mo1012a(boolean z, int i);

    /* JADX INFO: renamed from: a */
    public static synchronized AbstractC2616b m1001a() {
        if (f1023a == null) {
            f1023a = new C2626l();
        }
        return f1023a;
    }

    /* JADX INFO: renamed from: b */
    public synchronized void m1013b(int i) {
        if (this.f1025c) {
            return;
        }
        mo1012a(true, 1);
        mo1010a(i, new RunnableC2615a(this));
        this.f1025c = true;
    }

    /* JADX INFO: renamed from: a */
    public static synchronized void m1002a(ScheduledExecutorService scheduledExecutorService) {
        if (f1023a == null) {
            f1023a = new C2626l(scheduledExecutorService);
        }
    }
}
