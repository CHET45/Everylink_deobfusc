package com.clj.fastble.callback;

import com.clj.fastble.exception.BleException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BleNotifyCallback extends BleBaseCallback {
    public abstract void onCharacteristicChanged(byte[] bArr);

    public abstract void onNotifyFailure(BleException bleException);

    public abstract void onNotifySuccess();
}
