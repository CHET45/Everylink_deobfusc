package com.clj.fastble.callback;

import com.clj.fastble.exception.BleException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BleWriteCallback extends BleBaseCallback {
    public abstract void onWriteFailure(BleException bleException);

    public abstract void onWriteSuccess(int i, int i2, byte[] bArr);
}
