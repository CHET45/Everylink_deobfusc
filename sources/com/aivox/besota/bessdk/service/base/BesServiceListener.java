package com.aivox.besota.bessdk.service.base;

import com.aivox.besota.sdk.device.HmDevice;

/* JADX INFO: loaded from: classes.dex */
public interface BesServiceListener {
    void onErrorMessage(int i, HmDevice hmDevice);

    void onStateChangedMessage(int i, String str, HmDevice hmDevice);

    void onSuccessMessage(int i, HmDevice hmDevice);

    void onTotaConnectState(boolean z, HmDevice hmDevice);
}
