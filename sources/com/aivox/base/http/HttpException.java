package com.aivox.base.http;

/* JADX INFO: loaded from: classes.dex */
public class HttpException extends RuntimeException {
    private final int errorCode;

    public HttpException(int i, String str) {
        super(str);
        this.errorCode = i;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
