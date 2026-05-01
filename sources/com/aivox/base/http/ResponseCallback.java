package com.aivox.base.http;

/* JADX INFO: loaded from: classes.dex */
public interface ResponseCallback {
    void onReqFail(int i);

    void onReqSuc(int i);

    void onTimeOut();

    void sendReq();
}
