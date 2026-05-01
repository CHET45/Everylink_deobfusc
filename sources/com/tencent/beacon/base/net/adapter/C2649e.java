package com.tencent.beacon.base.net.adapter;

import com.tencent.beacon.base.net.BodyType;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.adapter.e */
/* JADX INFO: compiled from: OkHttpAdapter.java */
/* JADX INFO: loaded from: classes4.dex */
/* synthetic */ class C2649e {

    /* JADX INFO: renamed from: a */
    static final /* synthetic */ int[] f1133a;

    static {
        int[] iArr = new int[BodyType.values().length];
        f1133a = iArr;
        try {
            iArr[BodyType.FORM.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f1133a[BodyType.JSON.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f1133a[BodyType.DATA.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
    }
}
