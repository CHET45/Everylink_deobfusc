package com.tencent.beacon.p015a.p017b;

import com.tencent.beacon.base.net.BResponse;
import com.tencent.beacon.base.net.C2684d;
import com.tencent.beacon.base.net.call.Callback;
import com.tencent.beacon.base.util.C2695c;

/* JADX INFO: renamed from: com.tencent.beacon.a.b.e */
/* JADX INFO: compiled from: AbstractAttaReport.java */
/* JADX INFO: loaded from: classes4.dex */
class C2619e implements Callback<BResponse> {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ AbstractC2622h f1037a;

    C2619e(AbstractC2622h abstractC2622h) {
        this.f1037a = abstractC2622h;
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onResponse(BResponse bResponse) {
        if (bResponse != null) {
            C2695c.m1463a("AttaReport", "oversea net ret: " + bResponse.toString(), new Object[0]);
        }
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    public void onFailure(C2684d c2684d) {
    }
}
