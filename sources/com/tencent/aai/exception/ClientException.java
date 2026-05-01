package com.tencent.aai.exception;

/* JADX INFO: loaded from: classes4.dex */
public class ClientException extends Exception {
    private int code;
    private String message;

    public ClientException(int i, String str) {
        this.code = i;
        this.message = str;
    }

    public ClientException(ClientExceptionType clientExceptionType) {
        this(clientExceptionType.code, clientExceptionType.message);
    }

    public ClientException(ClientExceptionType clientExceptionType, String str) {
        this(clientExceptionType.code, str);
    }

    public int getCode() {
        return this.code;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.message;
    }

    @Override // java.lang.Throwable
    public String toString() {
        return "code=" + this.code + ", message=" + this.message;
    }
}
