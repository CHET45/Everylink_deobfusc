package com.tencent.beacon.event.open;

/* JADX INFO: loaded from: classes4.dex */
public enum SensitiveField {
    MAC,
    ANDROID_ID,
    OAID,
    PROCESS_NAME,
    BRAND,
    IMEI,
    IMSI,
    WIFI_MAC,
    WIFI_SSID;

    public String toStringValue() {
        switch (C2745c.f1706a[ordinal()]) {
            case 1:
                return "A6";
            case 2:
                return "A144";
            case 3:
                return "A7";
            case 4:
                return "A67";
            case 5:
                return "A9";
            case 6:
                return "A2";
            case 7:
                return "A4";
            case 8:
                return "A20";
            case 9:
                return "A69";
            default:
                return "";
        }
    }
}
