package com.aivox.besota.sdk.control;

import com.aivox.besota.sdk.device.HmDevice;
import com.aivox.besota.sdk.message.EQPayload;

/* JADX INFO: loaded from: classes.dex */
public interface EQControl extends BaseControl {
    void getAllEQSettings(HmDevice hmDevice, DeviceListener deviceListener);

    void getCombinedEQIndex(HmDevice hmDevice, DeviceListener deviceListener);

    void getRunningEQSetting(HmDevice hmDevice, DeviceListener deviceListener);

    void setCombinedEQ(HmDevice hmDevice, int[] iArr, DeviceListener deviceListener);

    void setEQSetting(HmDevice hmDevice, int i, EQPayload eQPayload, boolean z, DeviceListener deviceListener);
}
