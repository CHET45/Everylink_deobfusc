package com.github.houbb.opencc4j.exception;

/* JADX INFO: loaded from: classes3.dex */
public class Opencc4jRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -208874364243906193L;

    public Opencc4jRuntimeException() {
    }

    public Opencc4jRuntimeException(String str) {
        super(str);
    }

    public Opencc4jRuntimeException(String str, Throwable th) {
        super(str, th);
    }

    public Opencc4jRuntimeException(Throwable th) {
        super(th);
    }

    public Opencc4jRuntimeException(String str, Throwable th, boolean z, boolean z2) {
        super(str, th, z, z2);
    }
}
