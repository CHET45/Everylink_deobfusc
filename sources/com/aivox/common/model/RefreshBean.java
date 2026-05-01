package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class RefreshBean implements Serializable {
    private String newRefreshToken;
    private String newToken;
    private long newTokenExpire;

    public String getNewToken() {
        return this.newToken;
    }

    public void setNewToken(String str) {
        this.newToken = str;
    }

    public String getNewRefreshToken() {
        return this.newRefreshToken;
    }

    public void setNewRefreshToken(String str) {
        this.newRefreshToken = str;
    }

    public long getNewTokenExpire() {
        return this.newTokenExpire;
    }

    public void setNewTokenExpire(long j) {
        this.newTokenExpire = j;
    }
}
