package com.fasterxml.jackson.core;

import java.io.IOException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JacksonException extends IOException {
    private static final long serialVersionUID = 123;

    public abstract JsonLocation getLocation();

    public abstract String getOriginalMessage();

    public abstract Object getProcessor();

    protected JacksonException(String str) {
        super(str);
    }

    protected JacksonException(Throwable th) {
        super(th);
    }

    protected JacksonException(String str, Throwable th) {
        super(str, th);
    }
}
