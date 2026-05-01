package com.tencent.qcloud.core.common;

/* JADX INFO: loaded from: classes4.dex */
public class QCloudClientException extends Exception {
    private static final long serialVersionUID = 1;

    public boolean isRetryable() {
        return true;
    }

    public QCloudClientException(String str, Throwable th) {
        super(str, th);
    }

    public QCloudClientException(String str) {
        super(str);
    }

    public QCloudClientException(Throwable th) {
        super(th);
    }
}
