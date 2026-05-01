package com.tencent.beacon.p026c.p027a;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.tencent.beacon.module.StatModule;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.beacon.c.a.d */
/* JADX INFO: compiled from: PageTimeLifeCallbacks.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2706d implements Application.ActivityLifecycleCallbacks {

    /* JADX INFO: renamed from: a */
    private long f1413a = System.currentTimeMillis();

    /* JADX INFO: renamed from: b */
    private Map<Activity, Long> f1414b = new HashMap(3);

    /* JADX INFO: renamed from: c */
    private StatModule f1415c;

    public C2706d(StatModule statModule) {
        this.f1415c = statModule;
    }

    /* JADX INFO: renamed from: a */
    private void m1501a(Activity activity) {
        Long lValueOf = this.f1414b.get(activity);
        if (lValueOf == null) {
            lValueOf = Long.valueOf(this.f1413a);
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.f1415c.m1777b(activity.getLocalClassName(), jCurrentTimeMillis - lValueOf.longValue(), jCurrentTimeMillis);
        this.f1414b.remove(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        m1501a(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        this.f1414b.put(activity, Long.valueOf(System.currentTimeMillis()));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }
}
