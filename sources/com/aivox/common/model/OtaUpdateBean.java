package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class OtaUpdateBean implements Serializable {
    private boolean isUpdate;
    private String message;
    private String url;

    public boolean isUpdate() {
        return this.isUpdate;
    }

    public void setUpdate(boolean z) {
        this.isUpdate = z;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }
}
