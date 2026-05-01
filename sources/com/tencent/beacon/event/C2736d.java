package com.tencent.beacon.event;

import com.tencent.beacon.base.net.C2684d;
import com.tencent.beacon.base.net.call.Callback;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.p015a.p017b.C2624j;

/* JADX INFO: renamed from: com.tencent.beacon.event.d */
/* JADX INFO: compiled from: EventManager.java */
/* JADX INFO: loaded from: classes4.dex */
class C2736d implements Callback<byte[]> {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ EventBean f1574a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ String f1575b;

    /* JADX INFO: renamed from: c */
    final /* synthetic */ C2737e f1576c;

    C2736d(C2737e c2737e, EventBean eventBean, String str) {
        this.f1576c = c2737e;
        this.f1574a = eventBean;
        this.f1575b = str;
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onResponse(byte[] bArr) {
        C2695c.m1463a("[EventManager]", "convert to report by beacon socket success, eventCode = %s, logId = %s", this.f1574a.getEventCode(), this.f1575b);
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    public void onFailure(C2684d c2684d) {
        C2695c.m1476e("convert to report by beacon socket also fail, failure = %s", c2684d.toString());
        C2624j.m1031e().m1023a("464", c2684d.toString());
        this.f1576c.mo1680c(this.f1575b, this.f1574a);
    }
}
