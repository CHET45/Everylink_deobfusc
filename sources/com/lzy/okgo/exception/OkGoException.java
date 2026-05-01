package com.lzy.okgo.exception;

/* JADX INFO: loaded from: classes4.dex */
public class OkGoException extends Exception {
    public static OkGoException INSTANCE(String str) {
        return new OkGoException(str);
    }

    public OkGoException() {
    }

    public OkGoException(String str) {
        super(str);
    }

    public OkGoException(String str, Throwable th) {
        super(str, th);
    }

    public OkGoException(Throwable th) {
        super(th);
    }
}
