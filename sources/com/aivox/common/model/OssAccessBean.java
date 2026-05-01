package com.aivox.common.model;

/* JADX INFO: loaded from: classes.dex */
public class OssAccessBean {
    private String accessKeyId;
    private String accessKeySecret;
    private String buckName;
    private String expiration;
    private long expiredTime;
    private String region;
    private String securityToken;
    private String sessionToken;
    private long startTime;
    private String tmpSecretId;
    private String tmpSecretKey;

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long j) {
        this.startTime = j;
    }

    public long getExpiredTime() {
        return this.expiredTime;
    }

    public void setExpiredTime(long j) {
        this.expiredTime = j;
    }

    public String getSessionToken() {
        return this.sessionToken;
    }

    public void setSessionToken(String str) {
        this.sessionToken = str;
    }

    public String getTmpSecretId() {
        return this.tmpSecretId;
    }

    public void setTmpSecretId(String str) {
        this.tmpSecretId = str;
    }

    public String getTmpSecretKey() {
        return this.tmpSecretKey;
    }

    public void setTmpSecretKey(String str) {
        this.tmpSecretKey = str;
    }

    public String getAccessKeyId() {
        return this.accessKeyId;
    }

    public void setAccessKeyId(String str) {
        this.accessKeyId = str;
    }

    public String getAccessKeySecret() {
        return this.accessKeySecret;
    }

    public void setAccessKeySecret(String str) {
        this.accessKeySecret = str;
    }

    public String getExpiration() {
        return this.expiration;
    }

    public void setExpiration(String str) {
        this.expiration = str;
    }

    public String getSecurityToken() {
        return this.securityToken;
    }

    public void setSecurityToken(String str) {
        this.securityToken = str;
    }

    public String getBuckName() {
        return this.buckName;
    }

    public void setBuckName(String str) {
        this.buckName = str;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String str) {
        this.region = str;
    }
}
