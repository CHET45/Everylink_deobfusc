package com.aivox.besota.sdk.utils;

/* JADX INFO: loaded from: classes.dex */
public enum OTAStatus {
    STATUS_UNKNOWN(-1, "Status Unknown"),
    STATUS_STARTED(0, "Status Started"),
    STATUS_UPDATING(1, "Status Updating"),
    STATUS_PAUSED(2, "Status Paused"),
    STATUS_CANCELED(3, "Status Canceled"),
    STATUS_VERIFYING(4, "Status Verifying"),
    STATUS_VERIFIED(5, "Status Verifyed"),
    STATUS_FAILED(6, "Status Failed"),
    STATUS_SUCCEED(7, "Status Succeed"),
    STATUS_REBOOT(8, "Status Reboot");

    private String mName;
    private int mValue;

    OTAStatus(int i, String str) {
        this.mValue = i;
        this.mName = str;
    }

    public int getValue() {
        return this.mValue;
    }

    public String getName() {
        return this.mName;
    }
}
