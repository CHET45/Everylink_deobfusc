package com.tencent.beacon.base.net;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.beacon.base.net.adapter.AbstractNetAdapter;
import com.tencent.beacon.base.net.adapter.C2646b;
import com.tencent.beacon.base.net.adapter.C2652h;
import com.tencent.beacon.base.net.call.C2676c;
import com.tencent.beacon.base.net.call.C2682i;
import com.tencent.beacon.base.net.call.Callback;
import com.tencent.beacon.base.net.call.HttpRequestEntity;
import com.tencent.beacon.base.net.call.JceRequestEntity;
import com.tencent.beacon.base.net.p020a.C2640a;
import com.tencent.beacon.base.net.p020a.C2641b;
import com.tencent.beacon.base.net.p021b.RunnableC2670e;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.p015a.p017b.C2624j;
import com.tencent.beacon.p028d.C2710b;
import java.io.Closeable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.c */
/* JADX INFO: compiled from: BeaconNet.java */
/* JADX INFO: loaded from: classes4.dex */
public final class C2671c implements InterfaceC2685e, RunnableC2670e.a, Closeable {

    /* JADX INFO: renamed from: a */
    private static volatile C2671c f1323a;

    /* JADX INFO: renamed from: b */
    private final AtomicBoolean f1324b = new AtomicBoolean(false);

    /* JADX INFO: renamed from: c */
    private final AtomicInteger f1325c = new AtomicInteger();

    /* JADX INFO: renamed from: d */
    private final AtomicInteger f1326d = new AtomicInteger();

    /* JADX INFO: renamed from: e */
    public C2640a f1327e;

    /* JADX INFO: renamed from: f */
    public C2641b f1328f;

    /* JADX INFO: renamed from: g */
    private Context f1329g;

    /* JADX INFO: renamed from: h */
    private AbstractNetAdapter f1330h;

    /* JADX INFO: renamed from: i */
    private AbstractNetAdapter f1331i;

    private C2671c() {
    }

    /* JADX INFO: renamed from: d */
    public static synchronized C2671c m1351d() {
        if (f1323a == null) {
            f1323a = new C2671c();
        }
        return f1323a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: h */
    public void m1352h() {
        C2695c.m1463a("[BeaconNet]", "current net connected num: %d", Integer.valueOf(this.f1325c.decrementAndGet()));
    }

    /* JADX INFO: renamed from: i */
    private void m1353i() {
        C2695c.m1463a("[BeaconNet]", "current net connected num: %d", Integer.valueOf(this.f1325c.incrementAndGet()));
    }

    /* JADX INFO: renamed from: b */
    public C2682i m1361b(JceRequestEntity jceRequestEntity) {
        return new C2682i(jceRequestEntity);
    }

    /* JADX INFO: renamed from: c */
    public int m1362c() {
        C2695c.m1463a("[BeaconNet]", "get current net failure num: %d", Integer.valueOf(this.f1326d.get()));
        return this.f1326d.get();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.f1324b.set(true);
        C2695c.m1463a("[BeaconNet]", "network can't use. close BeaconNet.", new Object[0]);
    }

    /* JADX INFO: renamed from: e */
    public void m1363e() {
        C2695c.m1463a("[BeaconNet]", "increase current net failure num: %d", Integer.valueOf(this.f1326d.incrementAndGet()));
    }

    /* JADX INFO: renamed from: f */
    public boolean m1364f() {
        return this.f1325c.get() >= 5;
    }

    /* JADX INFO: renamed from: g */
    public void m1365g() {
        this.f1324b.set(false);
    }

    /* JADX INFO: renamed from: a */
    public void m1357a(Context context, AbstractNetAdapter abstractNetAdapter) {
        this.f1329g = context;
        if (abstractNetAdapter == null) {
            abstractNetAdapter = new C2646b();
        }
        this.f1330h = C2652h.m1176a();
        this.f1331i = abstractNetAdapter;
        this.f1327e = C2640a.m1156a();
        this.f1328f = C2641b.m1164a();
        RunnableC2670e.m1348a(context, this);
    }

    @Override // com.tencent.beacon.base.net.p021b.RunnableC2670e.a
    /* JADX INFO: renamed from: b */
    public void mo1091b() {
        this.f1324b.set(true);
        C2695c.m1463a("[BeaconNet]", "network can't use. close BeaconNet.", new Object[0]);
    }

    /* JADX INFO: renamed from: a */
    public void m1359a(JceRequestEntity jceRequestEntity, Callback<byte[]> callback) {
        if (this.f1324b.get()) {
            callback.onFailure(new C2684d(jceRequestEntity.getType().name(), null, 0, "BeaconNet close."));
            return;
        }
        AbstractNetAdapter abstractNetAdapterM1354a = m1354a(jceRequestEntity);
        m1353i();
        abstractNetAdapterM1354a.request(jceRequestEntity, new C2639a(this, jceRequestEntity, abstractNetAdapterM1354a == this.f1330h, callback));
    }

    /* JADX INFO: renamed from: a */
    public void m1356a(int i) {
        this.f1326d.set(i);
    }

    /* JADX INFO: renamed from: a */
    public void m1358a(HttpRequestEntity httpRequestEntity, Callback<BResponse> callback) {
        if (this.f1324b.get()) {
            callback.onFailure(new C2684d(httpRequestEntity.tag(), null, 0, "BeaconNet close."));
        } else {
            m1353i();
            this.f1331i.request(httpRequestEntity, new C2653b(this, httpRequestEntity, callback));
        }
    }

    /* JADX INFO: renamed from: a */
    public C2676c m1355a(HttpRequestEntity httpRequestEntity) {
        return new C2676c(httpRequestEntity);
    }

    /* JADX INFO: renamed from: a */
    public AbstractNetAdapter m1354a(JceRequestEntity jceRequestEntity) {
        if (jceRequestEntity.getType() == RequestType.EVENT) {
            return C2710b.m1518d().m1520A() ? this.f1330h : this.f1331i;
        }
        return this.f1330h;
    }

    /* JADX INFO: renamed from: a */
    public void m1360a(C2684d c2684d) {
        if (c2684d.f1363a.equals("atta") || TextUtils.isEmpty(c2684d.f1364b)) {
            return;
        }
        C2624j.m1031e().m1024a(c2684d.f1364b, c2684d.toString(), c2684d.f1367e);
    }

    @Override // com.tencent.beacon.base.net.p021b.RunnableC2670e.a
    /* JADX INFO: renamed from: a */
    public void mo1090a() {
        this.f1324b.set(false);
        C2695c.m1463a("[BeaconNet]", "network recovery. open BeaconNet.", new Object[0]);
    }
}
