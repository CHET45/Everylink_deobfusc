package com.tencent.qcloud.core.auth;

import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.http.HttpConfiguration;

/* JADX INFO: loaded from: classes4.dex */
public class ShortTimeCredentialProvider extends BasicLifecycleCredentialProvider {
    private long duration;
    private String secretId;
    private String secretKey;
    private long startTime;

    @Deprecated
    public ShortTimeCredentialProvider(String str, String str2, long j) {
        this.secretId = str;
        this.secretKey = str2;
        this.duration = j;
    }

    @Deprecated
    public ShortTimeCredentialProvider(String str, String str2, long j, long j2) {
        this(str, str2, j2);
        this.startTime = j;
    }

    @Override // com.tencent.qcloud.core.auth.BasicLifecycleCredentialProvider
    protected QCloudLifecycleCredentials fetchNewCredentials() throws QCloudClientException {
        long deviceTimeWithOffset = this.startTime;
        if (deviceTimeWithOffset <= 0) {
            deviceTimeWithOffset = HttpConfiguration.getDeviceTimeWithOffset();
        }
        String str = deviceTimeWithOffset + ";" + (this.duration + deviceTimeWithOffset);
        return new BasicQCloudCredentials(this.secretId, this.secretKey, secretKey2SignKey(this.secretKey, str), str);
    }

    private String secretKey2SignKey(String str, String str2) {
        byte[] bArrHmacSha1 = Utils.hmacSha1(str2, str);
        if (bArrHmacSha1 != null) {
            return new String(Utils.encodeHex(bArrHmacSha1));
        }
        return null;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public String getSecretId() {
        return this.secretId;
    }

    public long getDuration() {
        return this.duration;
    }

    public long getStartTime() {
        return this.startTime;
    }
}
