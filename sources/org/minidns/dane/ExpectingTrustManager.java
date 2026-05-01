package org.minidns.dane;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

/* JADX INFO: loaded from: classes4.dex */
public class ExpectingTrustManager implements X509TrustManager {
    private CertificateException exception;
    private final X509TrustManager trustManager;

    public ExpectingTrustManager(X509TrustManager x509TrustManager) {
        this.trustManager = x509TrustManager == null ? X509TrustManagerUtil.getDefault() : x509TrustManager;
    }

    public boolean hasException() {
        return this.exception != null;
    }

    public CertificateException getException() {
        CertificateException certificateException = this.exception;
        this.exception = null;
        return certificateException;
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        try {
            this.trustManager.checkClientTrusted(x509CertificateArr, str);
        } catch (CertificateException e) {
            this.exception = e;
        }
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        try {
            this.trustManager.checkServerTrusted(x509CertificateArr, str);
        } catch (CertificateException e) {
            this.exception = e;
        }
    }

    @Override // javax.net.ssl.X509TrustManager
    public X509Certificate[] getAcceptedIssuers() {
        return this.trustManager.getAcceptedIssuers();
    }
}
