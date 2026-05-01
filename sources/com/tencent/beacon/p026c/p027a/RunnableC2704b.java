package com.tencent.beacon.p026c.p027a;

import android.app.Activity;
import com.tencent.beacon.p026c.C2708c;
import java.lang.ref.WeakReference;

/* JADX INFO: renamed from: com.tencent.beacon.c.a.b */
/* JADX INFO: compiled from: LifecycleCallbacks.java */
/* JADX INFO: loaded from: classes4.dex */
class RunnableC2704b implements Runnable {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ WeakReference f1403a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ C2705c f1404b;

    RunnableC2704b(C2705c c2705c, WeakReference weakReference) {
        this.f1404b = c2705c;
        this.f1403a = weakReference;
    }

    @Override // java.lang.Runnable
    public void run() {
        new C2708c(((Activity) this.f1403a.get()).getApplicationContext()).m1506a();
    }
}
