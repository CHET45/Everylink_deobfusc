package com.tencent.qcloud.core.auth;

import com.tencent.qcloud.core.http.HttpConfiguration;

/* JADX INFO: loaded from: classes4.dex */
public class SessionQCloudCredentials implements QCloudLifecycleCredentials, QCloudRawCredentials {
    private final long expiredTime;
    private final String secretId;
    private final String secretKey;
    private final long startTime;
    private final String token;

    public SessionQCloudCredentials(String str, String str2, String str3, long j) {
        this(str, str2, str3, HttpConfiguration.getDeviceTimeWithOffset(), j);
    }

    public SessionQCloudCredentials(String str, String str2, String str3, long j, long j2) {
        if (str == null) {
            throw new IllegalArgumentException("secretId cannot be null.");
        }
        if (str2 == null) {
            throw new IllegalArgumentException("secretKey cannot be null.");
        }
        if (str3 == null) {
            throw new IllegalArgumentException("token cannot be null.");
        }
        if (j >= j2) {
            throw new IllegalArgumentException("beginTime must be less than expiredTime.");
        }
        this.secretId = str;
        this.secretKey = str2;
        this.startTime = j;
        this.expiredTime = j2;
        this.token = str3;
    }

    public SessionQCloudCredentials(String str, String str2, String str3, String str4) {
        if (str == null) {
            throw new IllegalArgumentException("secretId cannot be null.");
        }
        if (str2 == null) {
            throw new IllegalArgumentException("secretKey cannot be null.");
        }
        if (str3 == null) {
            throw new IllegalArgumentException("token cannot be null.");
        }
        if (str4 == null) {
            throw new IllegalArgumentException("keyTime cannot be null.");
        }
        this.secretId = str;
        this.secretKey = str2;
        this.token = str3;
        long[] keyTimes = Utils.parseKeyTimes(str4);
        this.startTime = keyTimes[0];
        this.expiredTime = keyTimes[1];
    }

    private String getKeyTime(long j, long j2) {
        return Utils.handleTimeAccuracy(j) + ";" + Utils.handleTimeAccuracy(j2);
    }

    private String getSignKey(String str, String str2) {
        byte[] bArrHmacSha1 = Utils.hmacSha1(str2, str);
        if (bArrHmacSha1 != null) {
            return new String(Utils.encodeHex(bArrHmacSha1));
        }
        return null;
    }

    @Override // com.tencent.qcloud.core.auth.QCloudLifecycleCredentials
    public boolean isValid() {
        return HttpConfiguration.getDeviceTimeWithOffset() <= this.expiredTime - 60;
    }

    public String getToken() {
        return this.token;
    }

    @Override // com.tencent.qcloud.core.auth.QCloudLifecycleCredentials
    public String getKeyTime() {
        return Utils.handleTimeAccuracy(this.startTime) + ";" + Utils.handleTimeAccuracy(this.expiredTime);
    }

    @Override // com.tencent.qcloud.core.auth.QCloudCredentials
    public String getSecretId() {
        return this.secretId;
    }

    @Override // com.tencent.qcloud.core.auth.QCloudRawCredentials
    public String getSecretKey() {
        return this.secretKey;
    }

    @Override // com.tencent.qcloud.core.auth.QCloudLifecycleCredentials
    public String getSignKey() {
        return getSignKey(this.secretKey, getKeyTime());
    }

    public long getStartTime() {
        return this.startTime;
    }

    public long getExpiredTime() {
        return this.expiredTime;
    }
}
