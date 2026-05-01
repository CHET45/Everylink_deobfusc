package com.aivox.besota.sdk.control;

import com.aivox.besota.sdk.device.HmDevice;
import com.aivox.besota.sdk.message.BaseMessage;
import com.aivox.besota.sdk.utils.StatusCode;

/* JADX INFO: loaded from: classes.dex */
public interface DeviceListener {
    void onChanged(HmDevice hmDevice, StatusCode statusCode, BaseMessage baseMessage);

    void onRead(HmDevice hmDevice, StatusCode statusCode, BaseMessage baseMessage);
}
