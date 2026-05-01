package com.tencent.qcloud.track.cls;

/* JADX INFO: loaded from: classes4.dex */
public class ClsSessionCredentials {
    private final long expiredTime;
    private final String secretId;
    private final String secretKey;
    private final String token;

    public ClsSessionCredentials(String str, String str2, String str3, long j) {
        if (str == null) {
            throw new IllegalArgumentException("secretId cannot be null.");
        }
        if (str2 == null) {
            throw new IllegalArgumentException("secretKey cannot be null.");
        }
        if (str3 == null) {
            throw new IllegalArgumentException("token cannot be null.");
        }
        this.secretId = str;
        this.secretKey = str2;
        this.expiredTime = j;
        this.token = str3;
    }

    public boolean isValid() {
        return System.currentTimeMillis() / 1000 <= this.expiredTime - 60;
    }

    public String getToken() {
        return this.token;
    }

    public String getSecretId() {
        return this.secretId;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public long getExpiredTime() {
        return this.expiredTime;
    }
}
