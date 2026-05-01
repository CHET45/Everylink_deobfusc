package com.tencent.beacon.base.net.p021b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.p015a.p017b.AbstractC2616b;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.b.e */
/* JADX INFO: compiled from: NetworkStateReceiver.java */
/* JADX INFO: loaded from: classes4.dex */
public final class RunnableC2670e extends BroadcastReceiver implements Runnable {

    /* JADX INFO: renamed from: a */
    private static final List<a> f1319a = new ArrayList();

    /* JADX INFO: renamed from: b */
    private static volatile boolean f1320b = false;

    /* JADX INFO: renamed from: c */
    private boolean f1321c = true;

    /* JADX INFO: renamed from: d */
    private volatile boolean f1322d = false;

    /* JADX INFO: renamed from: com.tencent.beacon.base.net.b.e$a */
    /* JADX INFO: compiled from: NetworkStateReceiver.java */
    public interface a {
        /* JADX INFO: renamed from: a */
        void mo1090a();

        /* JADX INFO: renamed from: b */
        void mo1091b();
    }

    /* JADX INFO: renamed from: a */
    public static void m1348a(Context context, a aVar) {
        if (context == null) {
            C2695c.m1468b("[net] context == null!", new Object[0]);
            return;
        }
        List<a> list = f1319a;
        synchronized (list) {
            if (!list.contains(aVar)) {
                list.add(aVar);
            }
        }
        if (f1320b) {
            return;
        }
        context.registerReceiver(new RunnableC2670e(), new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        f1320b = true;
    }

    /* JADX INFO: renamed from: b */
    private void m1349b() {
        List<a> list = f1319a;
        synchronized (list) {
            Iterator<a> it = list.iterator();
            while (it.hasNext()) {
                it.next().mo1091b();
            }
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (this.f1321c) {
            this.f1321c = false;
        } else {
            if (this.f1322d) {
                return;
            }
            AbstractC2616b.m1001a().mo1011a(this);
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f1322d = true;
        if (C2669d.m1344d()) {
            C2695c.m1474d("[net] current network available!", new Object[0]);
            m1347a();
        } else {
            C2695c.m1474d("[net] current network unavailable!", new Object[0]);
            m1349b();
        }
        this.f1322d = false;
    }

    /* JADX INFO: renamed from: a */
    private void m1347a() {
        List<a> list = f1319a;
        synchronized (list) {
            Iterator<a> it = list.iterator();
            while (it.hasNext()) {
                it.next().mo1090a();
            }
        }
    }
}
