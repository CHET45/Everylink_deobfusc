package com.tencent.qcloud.network.sonar;

/* JADX INFO: loaded from: classes4.dex */
public class SonarResult<T> {
    private Exception exception;
    private T result;
    private final boolean success = false;
    private final SonarType type;

    public SonarResult(SonarType sonarType, T t) {
        this.type = sonarType;
        this.result = t;
    }

    public SonarResult(SonarType sonarType, Exception exc) {
        this.type = sonarType;
        this.exception = exc;
    }

    public SonarType getType() {
        return this.type;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public Exception getException() {
        return this.exception;
    }

    public T getResult() {
        return this.result;
    }
}
