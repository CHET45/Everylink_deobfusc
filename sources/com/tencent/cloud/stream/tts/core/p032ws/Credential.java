package com.tencent.cloud.stream.tts.core.p032ws;

/* JADX INFO: loaded from: classes4.dex */
public class Credential {
    private String appid;
    private String secretId;
    private String secretKey;
    private String token;

    public Credential() {
    }

    public Credential(String appid, String secretId, String secretKey, String token) {
        this.appid = appid;
        this.secretId = secretId;
        this.secretKey = secretKey;
        this.token = token;
    }

    public String getAppid() {
        return this.appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecretId() {
        return this.secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
