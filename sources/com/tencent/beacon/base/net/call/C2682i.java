package com.tencent.beacon.base.net.call;

import com.tencent.beacon.base.net.C2671c;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.p015a.p017b.AbstractC2616b;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.call.i */
/* JADX INFO: compiled from: JceCall.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2682i implements InterfaceC2674a<byte[]> {

    /* JADX INFO: renamed from: a */
    private final JceRequestEntity f1361a;

    /* JADX INFO: renamed from: b */
    private long f1362b;

    public C2682i(JceRequestEntity jceRequestEntity) {
        this.f1361a = jceRequestEntity;
    }

    /* JADX INFO: renamed from: a */
    public void m1388a(Callback<byte[]> callback) {
        AbstractC2616b.m1001a().mo1011a(new RunnableC2679f(this, callback));
    }

    /* JADX INFO: renamed from: b */
    public void m1390b(Callback<byte[]> callback) {
        this.f1362b = C2694b.m1454c();
        C2671c.m1351d().m1359a(this.f1361a, new C2681h(this, callback));
    }

    /* JADX INFO: renamed from: a */
    public void m1389a(Callback<byte[]> callback, AbstractC2616b abstractC2616b) {
        abstractC2616b.mo1011a(new RunnableC2680g(this, callback));
    }
}
