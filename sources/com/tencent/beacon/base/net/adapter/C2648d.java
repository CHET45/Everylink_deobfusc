package com.tencent.beacon.base.net.adapter;

import okhttp3.Callback;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.adapter.d */
/* JADX INFO: compiled from: OkHttpAdapter.java */
/* JADX INFO: loaded from: classes4.dex */
class C2648d implements Callback {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ com.tencent.beacon.base.net.call.Callback f1130a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ String f1131b;

    /* JADX INFO: renamed from: c */
    final /* synthetic */ OkHttpAdapter f1132c;

    C2648d(OkHttpAdapter okHttpAdapter, com.tencent.beacon.base.net.call.Callback callback, String str) {
        this.f1132c = okHttpAdapter;
        this.f1130a = callback;
        this.f1131b = str;
    }
}
