package com.tencent.beacon.p019b;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.tencent.beacon.p015a.p018c.C2629b;

/* JADX INFO: renamed from: com.tencent.beacon.b.a */
/* JADX INFO: compiled from: SimpleLifecycleCallbacks.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2637a implements Application.ActivityLifecycleCallbacks {
    /* JADX INFO: renamed from: a */
    public void m1154a(Activity activity) {
        C2629b.f1072d = true;
        activity.getApplication().unregisterActivityLifecycleCallbacks(this);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        m1154a(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        m1154a(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        m1154a(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        m1154a(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        m1154a(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        m1154a(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        m1154a(activity);
    }
}
