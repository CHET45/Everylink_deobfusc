package com.tencent.beacon.base.net.call;

import com.tencent.beacon.base.net.BodyType;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.call.d */
/* JADX INFO: compiled from: HttpRequestEntity.java */
/* JADX INFO: loaded from: classes4.dex */
/* synthetic */ class C2677d {

    /* JADX INFO: renamed from: a */
    static final /* synthetic */ int[] f1354a;

    static {
        int[] iArr = new int[BodyType.values().length];
        f1354a = iArr;
        try {
            iArr[BodyType.JSON.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f1354a[BodyType.FORM.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f1354a[BodyType.DATA.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
    }
}
