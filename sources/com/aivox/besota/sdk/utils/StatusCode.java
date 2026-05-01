package com.aivox.besota.sdk.utils;

/* JADX INFO: loaded from: classes.dex */
public enum StatusCode {
    STATUS_UNKNOWN(-1, "Status Unknown"),
    STATUS_SUCCESS(0, "Status Success"),
    STATUS_TIMEOUT(1, "Status Timeout"),
    STATUS_CANCEL(2, "Status Canceled"),
    STATUS_FAIL(3, "Status Failed");

    private String mDescription;
    private int mValue;

    StatusCode(int i, String str) {
        this.mValue = i;
        this.mDescription = str;
    }

    public int getValue() {
        return this.mValue;
    }

    public String getDescription() {
        return this.mDescription;
    }
}
