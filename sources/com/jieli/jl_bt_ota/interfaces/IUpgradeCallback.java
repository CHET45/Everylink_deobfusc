package com.jieli.jl_bt_ota.interfaces;

import com.jieli.jl_bt_ota.model.base.BaseError;

/* JADX INFO: loaded from: classes3.dex */
public interface IUpgradeCallback {
    void onCancelOTA();

    void onError(BaseError baseError);

    void onNeedReconnect(String str, boolean z);

    void onProgress(int i, float f);

    void onStartOTA();

    void onStopOTA();
}
