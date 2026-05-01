package com.tencent.qcloud.core.auth;

/* JADX INFO: loaded from: classes4.dex */
public class StaticCredentialProvider implements QCloudCredentialProvider {
    private QCloudCredentials mCredentials;

    @Override // com.tencent.qcloud.core.auth.QCloudCredentialProvider
    public void refresh() {
    }

    public StaticCredentialProvider() {
    }

    public StaticCredentialProvider(QCloudCredentials qCloudCredentials) {
        this.mCredentials = qCloudCredentials;
    }

    public void update(QCloudCredentials qCloudCredentials) {
        this.mCredentials = qCloudCredentials;
    }

    @Override // com.tencent.qcloud.core.auth.QCloudCredentialProvider
    public QCloudCredentials getCredentials() {
        return this.mCredentials;
    }
}
