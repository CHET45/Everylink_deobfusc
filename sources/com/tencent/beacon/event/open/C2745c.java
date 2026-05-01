package com.tencent.beacon.event.open;

/* JADX INFO: renamed from: com.tencent.beacon.event.open.c */
/* JADX INFO: compiled from: SensitiveField.java */
/* JADX INFO: loaded from: classes4.dex */
/* synthetic */ class C2745c {

    /* JADX INFO: renamed from: a */
    static final /* synthetic */ int[] f1706a;

    static {
        int[] iArr = new int[SensitiveField.values().length];
        f1706a = iArr;
        try {
            iArr[SensitiveField.MAC.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f1706a[SensitiveField.OAID.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f1706a[SensitiveField.ANDROID_ID.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f1706a[SensitiveField.PROCESS_NAME.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            f1706a[SensitiveField.BRAND.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            f1706a[SensitiveField.IMEI.ordinal()] = 6;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            f1706a[SensitiveField.IMSI.ordinal()] = 7;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            f1706a[SensitiveField.WIFI_MAC.ordinal()] = 8;
        } catch (NoSuchFieldError unused8) {
        }
        try {
            f1706a[SensitiveField.WIFI_SSID.ordinal()] = 9;
        } catch (NoSuchFieldError unused9) {
        }
    }
}
