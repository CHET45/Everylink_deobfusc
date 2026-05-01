package com.tencent.cloud.stream.tts.core.exception;

/* JADX INFO: loaded from: classes4.dex */
public class SynthesizerException extends Exception {
    private int code;
    private String message;

    public int getCode() {
        return this.code;
    }

    private SynthesizerException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public SynthesizerException(SynthesizerExceptionType type) {
        this(type.code, type.message);
    }

    public SynthesizerException(SynthesizerExceptionType type, String message) {
        this(type.code, message);
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.message;
    }

    @Override // java.lang.Throwable
    public String toString() {
        return "code: " + this.code + ", message: " + this.message;
    }
}
