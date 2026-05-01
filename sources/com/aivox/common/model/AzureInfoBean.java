package com.aivox.common.model;

/* JADX INFO: loaded from: classes.dex */
public class AzureInfoBean {
    private String accountName;
    private String containerName;
    private String expiredTime;
    private String sasToken;

    public String getContainerName() {
        return this.containerName;
    }

    public void setContainerName(String str) {
        this.containerName = str;
    }

    public String getSasToken() {
        return this.sasToken;
    }

    public void setSasToken(String str) {
        this.sasToken = str;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String str) {
        this.accountName = str;
    }

    public String getExpiredTime() {
        return this.expiredTime;
    }

    public void setExpiredTime(String str) {
        this.expiredTime = str;
    }
}
