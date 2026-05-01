package com.aivox.besota.sdk.utils;

import androidx.core.view.PointerIconCompat;
import com.aivox.base.common.Constant;
import com.google.android.gms.auth.api.proxy.AuthApiStatusCodes;
import com.google.android.gms.location.GeofenceStatusCodes;

/* JADX INFO: loaded from: classes.dex */
public enum MessageID {
    UNKNOWN(-1, "UnknownCommand"),
    DEVICE_LIST_INFO(1001, "DEVICE_LIST_INFO"),
    DEVICE_NAME(1002, "DEVICE_NAME"),
    ANC_STATUS(PointerIconCompat.TYPE_HELP, "ANC_STATUS"),
    AUTO_PAUSE(1004, "AUTO_PAUSE"),
    AUTO_POWER_OFF(GeofenceStatusCodes.GEOFENCE_REQUEST_TOO_FREQUENT, "AUTO_POWER_OFF"),
    MULTI_AI_STATUS(1006, "MULTI_AI_STATUS"),
    A2DP_STATUS(PointerIconCompat.TYPE_CROSSHAIR, "A2DP_STATUS"),
    FIND_ME_STATUS(1008, "FIND_ME_STATUS"),
    TWS_STATUS(PointerIconCompat.TYPE_VERTICAL_TEXT, "TWS_STATUS"),
    SEALING_STATUS(PointerIconCompat.TYPE_ALIAS, "SEALING_STATUS"),
    GESTURE_STATUS(PointerIconCompat.TYPE_COPY, "GESTURE_STATUS"),
    SYNC_CRC_STATUS(PointerIconCompat.TYPE_NO_DROP, "SYNC_CRC_STATUS"),
    DEVICE_INFO(PointerIconCompat.TYPE_ALL_SCROLL, "DEVICE_INFO"),
    BATTERY_STATUS(PointerIconCompat.TYPE_HORIZONTAL_DOUBLE_ARROW, "BATTERY_STATUS"),
    IN_EAR_STATUS(PointerIconCompat.TYPE_VERTICAL_DOUBLE_ARROW, "IN_EAR_STATUS"),
    SMART_SWITCH_STATUS(PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW, "SMART_SWITCH_STATUS"),
    OTA_STATUS(Constant.HANDLER_XUNFEI_SEND, "OTA_STATUS"),
    EQ_STATUS(AuthApiStatusCodes.AUTH_API_ACCESS_FORBIDDEN, "EQ_STATUS"),
    RUNNING_EQ_STATUS(AuthApiStatusCodes.AUTH_API_CLIENT_ERROR, "RUNNING_EQ_STATUS"),
    COMBINED_EQ_STATUS(3003, "COMBINED_EQ_STATUS");

    private String mName;
    private int mValue;

    MessageID(int i, String str) {
        this.mValue = i;
        this.mName = str;
    }

    public int getValue() {
        return this.mValue;
    }

    public String getCmdName() {
        return this.mName;
    }
}
