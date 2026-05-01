package com.tencent.beacon.base.net;

import com.tencent.beacon.base.net.call.Callback;
import com.tencent.beacon.base.net.call.JceRequestEntity;
import com.tencent.beacon.base.util.C2695c;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.a */
/* JADX INFO: compiled from: BeaconNet.java */
/* JADX INFO: loaded from: classes4.dex */
class C2639a implements Callback<byte[]> {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ JceRequestEntity f1118a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ boolean f1119b;

    /* JADX INFO: renamed from: c */
    final /* synthetic */ Callback f1120c;

    /* JADX INFO: renamed from: d */
    final /* synthetic */ C2671c f1121d;

    C2639a(C2671c c2671c, JceRequestEntity jceRequestEntity, boolean z, Callback callback) {
        this.f1121d = c2671c;
        this.f1118a = jceRequestEntity;
        this.f1119b = z;
        this.f1120c = callback;
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onResponse(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            onFailure(new C2684d(this.f1118a.getType().name(), this.f1119b ? "402" : "452", 200, "raw response == null", null));
            return;
        }
        try {
            C2695c.m1463a("[BeaconNet]", "jceRequest: " + this.f1118a.getType() + " request success!", new Object[0]);
            C2695c.m1463a("[BeaconNet]", "mode: ".concat(this.f1119b ? "socket" : "http"), new Object[0]);
            this.f1120c.onResponse(bArr);
            this.f1121d.m1352h();
        } catch (Exception e) {
            onFailure(new C2684d(this.f1118a.getType().name(), this.f1119b ? "403" : "453", 200, e.getMessage(), e));
        }
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    public void onFailure(C2684d c2684d) {
        C2695c.m1463a("[BeaconNet]", "jceRequest: " + c2684d.toString(), new Object[0]);
        this.f1121d.m1360a(c2684d);
        this.f1120c.onFailure(c2684d);
        this.f1121d.m1352h();
    }
}
