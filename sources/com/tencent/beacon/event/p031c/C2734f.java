package com.tencent.beacon.event.p031c;

import com.tencent.beacon.event.open.EventType;

/* JADX INFO: renamed from: com.tencent.beacon.event.c.f */
/* JADX INFO: compiled from: LogIDGenerator.java */
/* JADX INFO: loaded from: classes4.dex */
/* synthetic */ class C2734f {

    /* JADX INFO: renamed from: a */
    static final /* synthetic */ int[] f1546a;

    static {
        int[] iArr = new int[EventType.values().length];
        f1546a = iArr;
        try {
            iArr[EventType.NORMAL.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f1546a[EventType.DT_NORMAL.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f1546a[EventType.REALTIME.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f1546a[EventType.DT_REALTIME.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            f1546a[EventType.IMMEDIATE_MSF.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            f1546a[EventType.IMMEDIATE.ordinal()] = 6;
        } catch (NoSuchFieldError unused6) {
        }
    }
}
