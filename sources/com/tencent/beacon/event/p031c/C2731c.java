package com.tencent.beacon.event.p031c;

import com.tencent.beacon.event.open.EventType;

/* JADX INFO: renamed from: com.tencent.beacon.event.c.c */
/* JADX INFO: compiled from: EventUtils.java */
/* JADX INFO: loaded from: classes4.dex */
/* synthetic */ class C2731c {

    /* JADX INFO: renamed from: a */
    static final /* synthetic */ int[] f1538a;

    static {
        int[] iArr = new int[EventType.values().length];
        f1538a = iArr;
        try {
            iArr[EventType.NORMAL.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f1538a[EventType.DT_NORMAL.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f1538a[EventType.REALTIME.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f1538a[EventType.DT_REALTIME.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            f1538a[EventType.IMMEDIATE_MSF.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            f1538a[EventType.IMMEDIATE.ordinal()] = 6;
        } catch (NoSuchFieldError unused6) {
        }
    }
}
