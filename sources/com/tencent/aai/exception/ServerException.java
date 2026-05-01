package com.tencent.aai.exception;

/* JADX INFO: loaded from: classes4.dex */
public class ServerException extends Exception {
    private final int code;
    private final String message;

    public ServerException(int i, String str) {
        this.code = i;
        this.message = str;
    }

    @Override // java.lang.Throwable
    public String toString() {
        return "code=" + this.code + ", message=" + this.message;
    }
}
