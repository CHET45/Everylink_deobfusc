package com.tencent.beacon.p015a.p017b;

import com.tencent.beacon.base.net.BResponse;
import com.tencent.beacon.base.net.C2684d;
import com.tencent.beacon.base.net.call.Callback;
import com.tencent.beacon.base.util.C2695c;

/* JADX INFO: renamed from: com.tencent.beacon.a.b.c */
/* JADX INFO: compiled from: AbstractAttaReport.java */
/* JADX INFO: loaded from: classes4.dex */
class C2617c implements Callback<BResponse> {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ String f1027a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ String f1028b;

    /* JADX INFO: renamed from: c */
    final /* synthetic */ Throwable f1029c;

    /* JADX INFO: renamed from: d */
    final /* synthetic */ AbstractC2622h f1030d;

    C2617c(AbstractC2622h abstractC2622h, String str, String str2, Throwable th) {
        this.f1030d = abstractC2622h;
        this.f1027a = str;
        this.f1028b = str2;
        this.f1029c = th;
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onResponse(BResponse bResponse) {
        C2695c.m1463a("AttaReport", "net ret: " + bResponse.toString(), new Object[0]);
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    public void onFailure(C2684d c2684d) {
        this.f1030d.m1020b(this.f1027a, this.f1028b, this.f1029c);
    }
}
