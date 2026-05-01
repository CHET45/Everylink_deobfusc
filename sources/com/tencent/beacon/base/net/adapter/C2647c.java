package com.tencent.beacon.base.net.adapter;

import okhttp3.Callback;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.adapter.c */
/* JADX INFO: compiled from: OkHttpAdapter.java */
/* JADX INFO: loaded from: classes4.dex */
class C2647c implements Callback {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ com.tencent.beacon.base.net.call.Callback f1127a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ String f1128b;

    /* JADX INFO: renamed from: c */
    final /* synthetic */ OkHttpAdapter f1129c;

    C2647c(OkHttpAdapter okHttpAdapter, com.tencent.beacon.base.net.call.Callback callback, String str) {
        this.f1129c = okHttpAdapter;
        this.f1127a = callback;
        this.f1128b = str;
    }
}
