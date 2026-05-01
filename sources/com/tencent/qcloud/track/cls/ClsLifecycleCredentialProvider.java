package com.tencent.qcloud.track.cls;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes4.dex */
public abstract class ClsLifecycleCredentialProvider {
    private volatile ClsSessionCredentials credentials;
    private ReentrantLock lock = new ReentrantLock();

    protected abstract ClsSessionCredentials fetchNewCredentials() throws ClsAuthenticationException;

    public ClsSessionCredentials getCredentials() throws ClsAuthenticationException {
        ClsSessionCredentials clsSessionCredentialsSafeGetCredentials = safeGetCredentials();
        if (clsSessionCredentialsSafeGetCredentials != null && clsSessionCredentialsSafeGetCredentials.isValid()) {
            return clsSessionCredentialsSafeGetCredentials;
        }
        refresh();
        return safeGetCredentials();
    }

    public void refresh() throws ClsAuthenticationException {
        try {
            try {
                boolean zTryLock = this.lock.tryLock(20L, TimeUnit.SECONDS);
                if (!zTryLock) {
                    throw new ClsAuthenticationException("lock timeout, no credential for sign");
                }
                ClsSessionCredentials clsSessionCredentialsSafeGetCredentials = safeGetCredentials();
                if (clsSessionCredentialsSafeGetCredentials == null || !clsSessionCredentialsSafeGetCredentials.isValid()) {
                    safeSetCredentials(null);
                    try {
                        safeSetCredentials(fetchNewCredentials());
                    } catch (Exception e) {
                        if (e instanceof ClsAuthenticationException) {
                            throw e;
                        }
                        throw new ClsAuthenticationException("fetch credentials error happens: " + e.getMessage());
                    }
                }
                if (zTryLock) {
                    this.lock.unlock();
                }
            } catch (InterruptedException e2) {
                throw new ClsAuthenticationException("interrupt when try to get credential: " + e2.getMessage());
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

    private synchronized void safeSetCredentials(ClsSessionCredentials clsSessionCredentials) {
        this.credentials = clsSessionCredentials;
    }

    private synchronized ClsSessionCredentials safeGetCredentials() {
        return this.credentials;
    }
}
