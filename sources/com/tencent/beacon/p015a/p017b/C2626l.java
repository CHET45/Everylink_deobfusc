package com.tencent.beacon.p015a.p017b;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.SparseArray;
import com.tencent.beacon.base.util.C2695c;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: renamed from: com.tencent.beacon.a.b.l */
/* JADX INFO: compiled from: BeaconAsyncTask.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2626l extends AbstractC2616b {

    /* JADX INFO: renamed from: d */
    private static final int f1052d;

    /* JADX INFO: renamed from: e */
    private static final int f1053e;

    /* JADX INFO: renamed from: f */
    private static final AtomicInteger f1054f;

    /* JADX INFO: renamed from: g */
    private final ScheduledExecutorService f1055g;

    /* JADX INFO: renamed from: h */
    private final SparseArray<a> f1056h;

    /* JADX INFO: renamed from: i */
    private final SparseArray<Handler> f1057i;

    /* JADX INFO: renamed from: j */
    private final ThreadFactoryC2627m f1058j;

    /* JADX INFO: renamed from: k */
    private boolean f1059k;

    /* JADX INFO: renamed from: com.tencent.beacon.a.b.l$a */
    /* JADX INFO: compiled from: BeaconAsyncTask.java */
    private static final class a {

        /* JADX INFO: renamed from: a */
        private final Runnable f1060a;

        /* JADX INFO: renamed from: b */
        private final long f1061b;

        /* JADX INFO: renamed from: c */
        private final long f1062c;

        /* JADX INFO: renamed from: d */
        private final TimeUnit f1063d;

        /* JADX INFO: renamed from: e */
        private Future<?> f1064e;

        a(Future<?> future, Runnable runnable, long j, long j2, TimeUnit timeUnit) {
            this.f1064e = future;
            this.f1060a = runnable;
            this.f1061b = j;
            this.f1062c = j2;
            this.f1063d = timeUnit;
        }

        /* JADX INFO: renamed from: a */
        boolean m1040a(boolean z) {
            return this.f1064e.cancel(z);
        }

        /* JADX INFO: renamed from: a */
        boolean m1039a() {
            return this.f1064e.isCancelled();
        }
    }

    static {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        f1052d = iAvailableProcessors;
        f1053e = Math.max(2, Math.min(iAvailableProcessors - 1, 3));
        f1054f = new AtomicInteger(0);
    }

    protected C2626l() {
        this(null);
    }

    /* JADX INFO: renamed from: b */
    private Runnable m1032b(Runnable runnable) {
        return new RunnableC2625k(this, runnable);
    }

    /* JADX INFO: renamed from: d */
    private boolean m1034d() {
        if (!this.f1059k) {
            return false;
        }
        C2695c.m1468b("[task] was closed , should all stopped!", new Object[0]);
        return true;
    }

    @Override // com.tencent.beacon.p015a.p017b.AbstractC2616b
    /* JADX INFO: renamed from: a */
    public synchronized void mo1011a(Runnable runnable) {
        if (m1034d()) {
            return;
        }
        this.f1055g.execute(m1032b(runnable));
    }

    protected C2626l(ScheduledExecutorService scheduledExecutorService) {
        this.f1059k = false;
        ThreadFactoryC2627m threadFactoryC2627m = new ThreadFactoryC2627m();
        this.f1058j = threadFactoryC2627m;
        this.f1055g = scheduledExecutorService == null ? Executors.newScheduledThreadPool(f1053e, threadFactoryC2627m) : scheduledExecutorService;
        this.f1056h = new SparseArray<>();
        this.f1057i = new SparseArray<>();
    }

    @Override // com.tencent.beacon.p015a.p017b.AbstractC2616b
    /* JADX INFO: renamed from: a */
    public synchronized void mo1010a(long j, Runnable runnable) {
        if (m1034d()) {
            return;
        }
        Runnable runnableM1032b = m1032b(runnable);
        if (j <= 0) {
            j = 0;
        }
        this.f1055g.schedule(runnableM1032b, j, TimeUnit.MILLISECONDS);
    }

    @Override // com.tencent.beacon.p015a.p017b.AbstractC2616b
    /* JADX INFO: renamed from: a */
    public synchronized void mo1007a(int i, long j, long j2, Runnable runnable) {
        if (m1034d()) {
            return;
        }
        a aVar = this.f1056h.get(i);
        if (aVar == null || aVar.m1039a()) {
            Runnable runnableM1032b = m1032b(runnable);
            if (j <= 0) {
                j = 0;
            }
            if (j2 < 100) {
                j2 = 100;
            }
            a aVar2 = new a(this.f1055g.scheduleAtFixedRate(runnableM1032b, j, j2, TimeUnit.MILLISECONDS), runnableM1032b, j, j2, TimeUnit.MILLISECONDS);
            C2695c.m1464a("[task] add a new polling task! taskId: %d , periodTime: %d", Integer.valueOf(i), Long.valueOf(j2));
            this.f1056h.put(i, aVar2);
        }
    }

    @Override // com.tencent.beacon.p015a.p017b.AbstractC2616b
    /* JADX INFO: renamed from: a */
    public void mo1008a(int i, boolean z, int i2) {
        a aVar = this.f1056h.get(i);
        if (aVar != null && !aVar.m1039a()) {
            C2695c.m1464a("[task] cancel a old pollingTaskWrapper!", new Object[0]);
            aVar.m1040a(z);
        }
        this.f1024b = i2;
    }

    @Override // com.tencent.beacon.p015a.p017b.AbstractC2616b
    /* JADX INFO: renamed from: a */
    public synchronized void mo1012a(boolean z, int i) {
        if (m1034d()) {
            return;
        }
        for (int i2 = 0; i2 < this.f1056h.size(); i2++) {
            mo1008a(this.f1056h.keyAt(i2), z, i);
        }
        C2695c.m1464a("[task] All schedule tasks stop", new Object[0]);
    }

    @Override // com.tencent.beacon.p015a.p017b.AbstractC2616b
    /* JADX INFO: renamed from: a */
    public synchronized void mo1009a(long j, int i) {
        if (i < this.f1024b) {
            return;
        }
        if (m1034d()) {
            return;
        }
        for (int i2 = 0; i2 < this.f1056h.size(); i2++) {
            mo1006a(this.f1056h.keyAt(i2), j, i);
        }
        C2695c.m1464a("[task] Resumed all schedule task", new Object[0]);
    }

    @Override // com.tencent.beacon.p015a.p017b.AbstractC2616b
    /* JADX INFO: renamed from: a */
    public synchronized void mo1006a(int i, long j, int i2) {
        if (i2 < this.f1024b) {
            return;
        }
        a aVar = this.f1056h.get(i);
        if (aVar != null) {
            if (!aVar.m1039a()) {
                return;
            } else {
                aVar.f1064e = this.f1055g.scheduleAtFixedRate(aVar.f1060a, j, aVar.f1062c, aVar.f1063d);
            }
        }
        this.f1024b = 0;
    }

    @Override // com.tencent.beacon.p015a.p017b.AbstractC2616b
    /* JADX INFO: renamed from: a */
    public synchronized Handler mo1005a(int i) {
        Handler handler;
        handler = this.f1057i.get(i);
        if (handler == null) {
            HandlerThread handlerThread = new HandlerThread(this.f1058j.m1041a());
            handlerThread.start();
            handler = new Handler(handlerThread.getLooper());
        }
        this.f1057i.put(i, handler);
        return handler;
    }
}
