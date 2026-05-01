package com.aivox.besota.sdk.utils;

/* JADX INFO: loaded from: classes.dex */
public enum DeviceProtocol {
    PROTOCOL_UNKNOWN(0, "Unknown"),
    PROTOCOL_BLE(1, "Bluetooth Low Energy"),
    PROTOCOL_SPP(16, "Serial Port Profile"),
    PROTOCOL_WIFI(256, "Wi-Fi"),
    PROTOCOL_CABLE(4096, "Cable"),
    PROTOCOL_USB(65536, "USB"),
    PROTOCOL_GATT_BR_EDR(1048576, "GATT_BR_EDR");

    private String mName;
    private int mValue;

    DeviceProtocol(int i, String str) {
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
