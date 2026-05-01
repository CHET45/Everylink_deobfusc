package com.aivox.base.httprx.exception;

/* JADX INFO: loaded from: classes.dex */
public class CustomException extends Exception {
    private String customError;

    public String getCustomError() {
        return this.customError;
    }

    public void setCustomError(String str) {
        this.customError = str;
    }
}
