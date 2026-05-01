package com.aivox.besota.sdk.control;

/* JADX INFO: loaded from: classes.dex */
public interface BaseControl {
    void registerGlobalListener(DeviceListener deviceListener);

    void unregisterGlobalListener(DeviceListener deviceListener);
}
