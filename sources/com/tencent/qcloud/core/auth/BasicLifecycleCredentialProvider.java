package com.tencent.qcloud.core.auth;

import com.tencent.qcloud.core.common.QCloudAuthenticationException;
import com.tencent.qcloud.core.common.QCloudClientException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BasicLifecycleCredentialProvider implements QCloudCredentialProvider {
    private volatile QCloudLifecycleCredentials credentials;
    private ReentrantLock lock = new ReentrantLock();

    protected abstract QCloudLifecycleCredentials fetchNewCredentials() throws QCloudClientException;

    @Override // com.tencent.qcloud.core.auth.QCloudCredentialProvider
    public QCloudCredentials getCredentials() throws QCloudClientException {
        QCloudLifecycleCredentials qCloudLifecycleCredentialsSafeGetCredentials = safeGetCredentials();
        if (qCloudLifecycleCredentialsSafeGetCredentials != null && qCloudLifecycleCredentialsSafeGetCredentials.isValid()) {
            return qCloudLifecycleCredentialsSafeGetCredentials;
        }
        refresh();
        return safeGetCredentials();
    }

    @Override // com.tencent.qcloud.core.auth.QCloudCredentialProvider
    public void refresh() throws QCloudClientException {
        try {
            try {
                boolean zTryLock = this.lock.tryLock(20L, TimeUnit.SECONDS);
                if (!zTryLock) {
                    throw new QCloudClientException(new QCloudAuthenticationException("lock timeout, no credential for sign"));
                }
                QCloudLifecycleCredentials qCloudLifecycleCredentialsSafeGetCredentials = safeGetCredentials();
                if (qCloudLifecycleCredentialsSafeGetCredentials == null || !qCloudLifecycleCredentialsSafeGetCredentials.isValid()) {
                    safeSetCredentials(null);
                    try {
                        safeSetCredentials(fetchNewCredentials());
                    } catch (Exception e) {
                        if (e instanceof QCloudClientException) {
                            throw e;
                        }
                        throw new QCloudClientException("fetch credentials error happens: " + e.getMessage(), new QCloudAuthenticationException(e.getMessage()));
                    }
                }
                if (zTryLock) {
                    this.lock.unlock();
                }
            } catch (InterruptedException e2) {
                throw new QCloudClientException("interrupt when try to get credential", new QCloudAuthenticationException(e2.getMessage()));
            }
        } catch (Throwable th) {
            if (0 != 0) {
                this.lock.unlock();
            }
            throw th;
        }
    }

    public synchronized void forceInvalidationCredential() {
        safeSetCredentials(null);
    }

    private synchronized void safeSetCredentials(QCloudLifecycleCredentials qCloudLifecycleCredentials) {
        this.credentials = qCloudLifecycleCredentials;
    }

    private synchronized QCloudLifecycleCredentials safeGetCredentials() {
        return this.credentials;
    }
}
