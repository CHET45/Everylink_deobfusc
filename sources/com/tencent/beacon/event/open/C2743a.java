package com.tencent.beacon.event.open;

/* JADX INFO: renamed from: com.tencent.beacon.event.open.a */
/* JADX INFO: compiled from: BeaconEvent.java */
/* JADX INFO: loaded from: classes4.dex */
/* synthetic */ class C2743a {

    /* JADX INFO: renamed from: a */
    static final /* synthetic */ int[] f1703a;

    static {
        int[] iArr = new int[EventType.values().length];
        f1703a = iArr;
        try {
            iArr[EventType.NORMAL.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f1703a[EventType.DT_NORMAL.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f1703a[EventType.IMMEDIATE_MSF.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f1703a[EventType.IMMEDIATE.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            f1703a[EventType.REALTIME.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            f1703a[EventType.DT_REALTIME.ordinal()] = 6;
        } catch (NoSuchFieldError unused6) {
        }
    }
}
