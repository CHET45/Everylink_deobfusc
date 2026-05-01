package com.tencent.cloud.stream.tts.core.p032ws;

/* JADX INFO: loaded from: classes4.dex */
public class ConnectionProfile {
    private String host;
    private String sign;
    private String token;
    private String url;

    public ConnectionProfile(String sign, String url, String host, String token) {
        this.sign = sign;
        this.url = url;
        this.host = host;
        this.token = token;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
