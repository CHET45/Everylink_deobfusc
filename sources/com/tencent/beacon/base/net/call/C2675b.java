package com.tencent.beacon.base.net.call;

import com.tencent.beacon.base.net.BResponse;
import com.tencent.beacon.base.net.C2684d;
import com.tencent.beacon.base.net.NetException;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.call.b */
/* JADX INFO: compiled from: HttpCall.java */
/* JADX INFO: loaded from: classes4.dex */
class C2675b implements Callback<BResponse> {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ Callback f1351a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ C2676c f1352b;

    C2675b(C2676c c2676c, Callback callback) {
        this.f1352b = c2676c;
        this.f1351a = callback;
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onResponse(BResponse bResponse) throws NetException {
        Callback callback = this.f1351a;
        if (callback != null) {
            callback.onResponse(bResponse);
        }
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    public void onFailure(C2684d c2684d) {
        Callback callback = this.f1351a;
        if (callback != null) {
            callback.onFailure(c2684d);
        }
    }
}
