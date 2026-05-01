package com.clj.fastble.callback;

import com.clj.fastble.data.BleDevice;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BleScanAndConnectCallback extends BleGattCallback implements BleScanPresenterImp {
    public void onLeScan(BleDevice bleDevice) {
    }

    public abstract void onScanFinished(BleDevice bleDevice);
}
