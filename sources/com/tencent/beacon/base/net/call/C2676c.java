package com.tencent.beacon.base.net.call;

import com.tencent.beacon.base.net.BResponse;
import com.tencent.beacon.base.net.C2671c;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.call.c */
/* JADX INFO: compiled from: HttpCall.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2676c implements InterfaceC2674a<BResponse> {

    /* JADX INFO: renamed from: a */
    private HttpRequestEntity f1353a;

    public C2676c(HttpRequestEntity httpRequestEntity) {
        this.f1353a = httpRequestEntity;
    }

    /* JADX INFO: renamed from: a */
    public void m1384a(Callback<BResponse> callback) {
        C2671c.m1351d().m1358a(this.f1353a, new C2675b(this, callback));
    }
}
