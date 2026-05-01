package com.aivox.base.httprx.exception;

/* JADX INFO: loaded from: classes.dex */
public class ResponseCodeException extends CustomException {
    public ResponseCodeException(String str, int i) {
        setCustomError("code:" + i + ", msg:" + str);
    }
}
