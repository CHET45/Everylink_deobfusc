package com.tencent.beacon.base.net.adapter;

import com.tencent.beacon.base.net.BodyType;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.adapter.a */
/* JADX INFO: compiled from: HttpAdapter.java */
/* JADX INFO: loaded from: classes4.dex */
/* synthetic */ class C2645a {

    /* JADX INFO: renamed from: a */
    static final /* synthetic */ int[] f1126a;

    static {
        int[] iArr = new int[BodyType.values().length];
        f1126a = iArr;
        try {
            iArr[BodyType.DATA.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f1126a[BodyType.FORM.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f1126a[BodyType.JSON.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
    }
}
