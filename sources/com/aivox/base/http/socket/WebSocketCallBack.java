package com.aivox.base.http.socket;

/* JADX INFO: loaded from: classes.dex */
public interface WebSocketCallBack {
    void onClosing();

    void onConnectError(Throwable th);

    void onMessage(int i);

    void onMessage(int i, String str);

    void onMessage(String str);

    void onMessage(byte[] bArr);

    void onOpen();
}
