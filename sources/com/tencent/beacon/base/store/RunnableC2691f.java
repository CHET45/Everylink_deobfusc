package com.tencent.beacon.base.store;

/* JADX INFO: renamed from: com.tencent.beacon.base.store.f */
/* JADX INFO: compiled from: PropertiesFile.java */
/* JADX INFO: loaded from: classes4.dex */
class RunnableC2691f implements Runnable {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ Runnable f1379a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ C2692g f1380b;

    RunnableC2691f(C2692g c2692g, Runnable runnable) {
        this.f1380b = c2692g;
        this.f1379a = runnable;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0043 A[EXC_TOP_SPLITTER, PHI: r2
  0x0043: PHI (r2v3 java.nio.channels.FileLock) = (r2v2 java.nio.channels.FileLock), (r2v4 java.nio.channels.FileLock) binds: [B:16:0x0041, B:6:0x0015] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() {
        /*
            r6 = this;
            java.lang.String r0 = "file get lock error:"
            com.tencent.beacon.base.store.g r1 = r6.f1380b
            monitor-enter(r1)
            r2 = 0
            com.tencent.beacon.base.store.g r3 = r6.f1380b     // Catch: java.lang.Exception -> L18 java.lang.Throwable -> L1a
            java.nio.channels.FileChannel r3 = com.tencent.beacon.base.store.C2692g.m1415e(r3)     // Catch: java.lang.Exception -> L18 java.lang.Throwable -> L1a
            java.nio.channels.FileLock r2 = r3.lock()     // Catch: java.lang.Exception -> L18 java.lang.Throwable -> L1a
            java.lang.Runnable r3 = r6.f1379a     // Catch: java.lang.Exception -> L18 java.lang.Throwable -> L1a
            r3.run()     // Catch: java.lang.Exception -> L18 java.lang.Throwable -> L1a
            if (r2 == 0) goto L46
            goto L43
        L18:
            r3 = move-exception
            goto L1c
        L1a:
            r0 = move-exception
            goto L48
        L1c:
            java.lang.String r4 = "[properties]"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L1a
            r5.<init>(r0)     // Catch: java.lang.Throwable -> L1a
            java.lang.String r0 = r3.getMessage()     // Catch: java.lang.Throwable -> L1a
            java.lang.StringBuilder r0 = r5.append(r0)     // Catch: java.lang.Throwable -> L1a
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L1a
            java.lang.Object[] r0 = new java.lang.Object[]{r0}     // Catch: java.lang.Throwable -> L1a
            com.tencent.beacon.base.util.C2695c.m1468b(r4, r0)     // Catch: java.lang.Throwable -> L1a
            com.tencent.beacon.a.b.j r0 = com.tencent.beacon.p015a.p017b.C2624j.m1031e()     // Catch: java.lang.Throwable -> L1a
            java.lang.String r4 = "504"
            java.lang.String r5 = "[properties] File get lock error!"
            r0.m1024a(r4, r5, r3)     // Catch: java.lang.Throwable -> L1a
            if (r2 == 0) goto L46
        L43:
            r2.release()     // Catch: java.io.IOException -> L46 java.lang.Throwable -> L4e
        L46:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L4e
            return
        L48:
            if (r2 == 0) goto L4d
            r2.release()     // Catch: java.io.IOException -> L4d java.lang.Throwable -> L4e
        L4d:
            throw r0     // Catch: java.lang.Throwable -> L4e
        L4e:
            r0 = move-exception
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L4e
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.beacon.base.store.RunnableC2691f.run():void");
    }
}
