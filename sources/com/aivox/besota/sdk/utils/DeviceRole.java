package com.aivox.besota.sdk.utils;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public enum DeviceRole {
    UNKNOWN(-1, "Unknown"),
    NORMAL(0, "Normal"),
    MASTER(1, "Master"),
    SLAVE(2, "Slave"),
    TWS_MASTER(101, "TWS_MASTER"),
    TWS_SLAVE(102, "TWS_SLAVE");

    private String mName;
    private int mValue;

    DeviceRole(int i, String str) {
        this.mValue = i;
        this.mName = str;
    }

    public static DeviceRole getRole(int i) {
        if (i == 0) {
            return NORMAL;
        }
        if (i == 1) {
            return MASTER;
        }
        if (i == 2) {
            return SLAVE;
        }
        if (i == 101) {
            return TWS_MASTER;
        }
        if (i == 102) {
            return TWS_SLAVE;
        }
        return UNKNOWN;
    }

    public static DeviceRole getRole(String str) {
        if (TextUtils.isEmpty(str)) {
            return UNKNOWN;
        }
        str.hashCode();
        switch (str) {
            case "0":
            case "00":
                return NORMAL;
            case "1":
            case "01":
                return MASTER;
            case "2":
            case "02":
            case "10":
                return SLAVE;
            default:
                return NORMAL;
        }
    }

    public int getValue() {
        return this.mValue;
    }

    public String getName() {
        return this.mName;
    }
}
