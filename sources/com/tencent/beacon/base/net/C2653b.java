package com.tencent.beacon.base.net;

import com.tencent.beacon.base.net.call.Callback;
import com.tencent.beacon.base.net.call.HttpRequestEntity;
import com.tencent.beacon.base.util.C2695c;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.b */
/* JADX INFO: compiled from: BeaconNet.java */
/* JADX INFO: loaded from: classes4.dex */
class C2653b implements Callback<BResponse> {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ HttpRequestEntity f1138a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ Callback f1139b;

    /* JADX INFO: renamed from: c */
    final /* synthetic */ C2671c f1140c;

    C2653b(C2671c c2671c, HttpRequestEntity httpRequestEntity, Callback callback) {
        this.f1140c = c2671c;
        this.f1138a = httpRequestEntity;
        this.f1139b = callback;
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onResponse(BResponse bResponse) {
        try {
            C2695c.m1463a("[BeaconNet]", "httpRequest: " + this.f1138a.tag() + " request success!", new Object[0]);
            this.f1139b.onResponse(bResponse);
            this.f1140c.m1352h();
        } catch (Exception e) {
            onFailure(new C2684d(this.f1138a.tag(), "453", 200, e.getMessage(), e));
        }
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    public void onFailure(C2684d c2684d) {
        C2695c.m1463a("[BeaconNet]", "httpRequest: " + c2684d.toString(), new Object[0]);
        this.f1140c.m1360a(c2684d);
        this.f1139b.onFailure(c2684d);
        this.f1140c.m1352h();
    }
}
