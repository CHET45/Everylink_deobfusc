package com.tencent.beacon.p015a.p017b;

import android.util.Log;
import com.tencent.beacon.base.net.BResponse;
import com.tencent.beacon.base.net.C2684d;
import com.tencent.beacon.base.net.call.Callback;

/* JADX INFO: renamed from: com.tencent.beacon.a.b.f */
/* JADX INFO: compiled from: AbstractAttaReport.java */
/* JADX INFO: loaded from: classes4.dex */
class C2620f implements Callback<BResponse> {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ RunnableC2621g f1038a;

    C2620f(RunnableC2621g runnableC2621g) {
        this.f1038a = runnableC2621g;
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onResponse(BResponse bResponse) {
        Log.e("AttaReport", "report success attaCode: " + this.f1038a.f1040b);
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    public void onFailure(C2684d c2684d) {
        Log.e("AttaReport", "report failure attaCode: " + this.f1038a.f1040b + " errorMsg: " + c2684d.f1366d);
    }
}
