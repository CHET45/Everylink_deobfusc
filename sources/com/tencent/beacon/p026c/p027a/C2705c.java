package com.tencent.beacon.p026c.p027a;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import com.tencent.beacon.base.store.SharedPreferencesC2686a;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.module.StatModule;
import com.tencent.beacon.p015a.p017b.AbstractC2616b;
import com.tencent.beacon.p015a.p018c.C2629b;
import com.tencent.beacon.p028d.C2709a;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.beacon.c.a.c */
/* JADX INFO: compiled from: LifecycleCallbacks.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2705c implements Application.ActivityLifecycleCallbacks {

    /* JADX INFO: renamed from: a */
    private static SparseArray<WeakReference<Activity>> f1405a = new SparseArray<>();

    /* JADX INFO: renamed from: b */
    private boolean f1406b = false;

    /* JADX INFO: renamed from: c */
    private long f1407c = 0;

    /* JADX INFO: renamed from: d */
    private long f1408d = 0;

    /* JADX INFO: renamed from: e */
    private long f1409e = 20000;

    /* JADX INFO: renamed from: f */
    private String f1410f = "";

    /* JADX INFO: renamed from: g */
    private Map<String, String> f1411g;

    /* JADX INFO: renamed from: h */
    private StatModule f1412h;

    public C2705c(StatModule statModule) {
        this.f1412h = statModule;
        HashMap map = new HashMap(6);
        this.f1411g = map;
        map.put("A63", "N");
        this.f1411g.put("A66", "F");
    }

    /* JADX INFO: renamed from: c */
    private boolean m1499c() {
        String strM1458e = C2694b.m1458e();
        if ("".equals(this.f1410f)) {
            this.f1410f = SharedPreferencesC2686a.m1391a().getString("LAUEVE_DENGTA", "");
        }
        boolean z = false;
        if (!strM1458e.equals(this.f1410f)) {
            SharedPreferencesC2686a.a aVarEdit = SharedPreferencesC2686a.m1391a().edit();
            if (C2694b.m1441a((SharedPreferences.Editor) aVarEdit)) {
                aVarEdit.putString("LAUEVE_DENGTA", strM1458e);
            }
            if (!"".equals(this.f1410f)) {
                C2695c.m1464a("[core] -> report new day launcher event.", new Object[0]);
                z = true;
            }
            this.f1410f = strM1458e;
        }
        return z;
    }

    /* JADX INFO: renamed from: d */
    private void m1500d() {
        AbstractC2616b.m1001a().mo1011a(new RunnableC2703a(this));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        m1494a(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        m1494a(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        m1494a(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        m1494a(activity);
        m1495a(true, activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        m1494a(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        m1494a(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        m1494a(activity);
        m1495a(false, activity);
    }

    /* JADX INFO: renamed from: a */
    public static SparseArray<WeakReference<Activity>> m1492a() {
        return f1405a;
    }

    /* JADX INFO: renamed from: b */
    private static void m1498b(Activity activity) {
        if (activity == null || f1405a == null) {
            return;
        }
        int iHashCode = activity.hashCode();
        if (f1405a.get(iHashCode) == null) {
            f1405a.put(iHashCode, new WeakReference<>(activity));
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1495a(boolean z, Activity activity) {
        if (z) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (this.f1407c > 0) {
                long j = this.f1408d;
                if (j > 0 && j + m1496b() <= jCurrentTimeMillis) {
                    C2695c.m1464a("[lifecycle] -> return foreground more than 20s.", new Object[0]);
                    m1500d();
                    StatModule statModule = this.f1412h;
                    if (statModule != null) {
                        statModule.m1773a();
                    }
                }
            }
            this.f1407c = jCurrentTimeMillis;
            this.f1408d = 0L;
            return;
        }
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        this.f1408d = jCurrentTimeMillis2;
        long j2 = this.f1407c;
        if (800 + j2 > jCurrentTimeMillis2) {
            C2695c.m1464a("[lifecycle] -> debounce activity switch.", new Object[0]);
            this.f1407c = 0L;
            return;
        }
        if (j2 == 0) {
            this.f1407c = jCurrentTimeMillis2;
        }
        StatModule statModule2 = this.f1412h;
        if (statModule2 != null) {
            statModule2.m1776b();
        }
    }

    /* JADX INFO: renamed from: b */
    private long m1496b() {
        if (this.f1409e <= 20000) {
            String strM1509a = C2709a.m1508a().m1509a("hotLauncher");
            if (strM1509a != null) {
                try {
                    this.f1409e = Long.valueOf(strM1509a).longValue();
                    C2695c.m1464a("[strategy] -> change launcher time: %s ms", strM1509a);
                } catch (NumberFormatException unused) {
                    C2695c.m1468b("[strategy] -> event param 'hotLauncher' error.", new Object[0]);
                }
            }
            this.f1409e++;
        }
        return this.f1409e;
    }

    /* JADX INFO: renamed from: a */
    private void m1494a(Activity activity) {
        C2629b.f1072d = true;
        m1498b(activity);
        try {
            WeakReference<Activity> weakReference = m1492a().get(activity.hashCode());
            if (!this.f1406b) {
                C2695c.m1464a("[event] lifecycle callback recover active user.", new Object[0]);
                AbstractC2616b.m1001a().mo1011a(new RunnableC2704b(this, weakReference));
                this.f1406b = true;
            }
        } catch (NullPointerException e) {
            Log.e("LifecycleCallbacks", "onActivityEvent: " + e.getMessage());
        }
        if (m1499c()) {
            m1500d();
        }
    }
}
