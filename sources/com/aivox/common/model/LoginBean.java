package com.aivox.common.model;

/* JADX INFO: loaded from: classes.dex */
public class LoginBean {
    String refreshToken;
    String token;
    long tokenExpire;
    UserInfo userinfo;

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public Long getTokenExpire() {
        return Long.valueOf(this.tokenExpire);
    }

    public void setTokenExpire(long j) {
        this.tokenExpire = j;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(String str) {
        this.refreshToken = str;
    }

    public UserInfo getUserinfo() {
        return this.userinfo;
    }

    public void setUserinfo(UserInfo userInfo) {
        this.userinfo = userInfo;
    }
}
