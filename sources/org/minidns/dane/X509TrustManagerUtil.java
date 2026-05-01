package org.minidns.dane;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* JADX INFO: loaded from: classes4.dex */
public class X509TrustManagerUtil {
    public static X509TrustManager getDefault() {
        return getDefault(null);
    }

    public static X509TrustManager getDefault(KeyStore keyStore) {
        String defaultAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(defaultAlgorithm);
            trustManagerFactory.init(keyStore);
            for (TrustManager trustManager : trustManagerFactory.getTrustManagers()) {
                if (trustManager instanceof X509TrustManager) {
                    return (X509TrustManager) trustManager;
                }
            }
            throw new AssertionError("No trust manager for the default algorithm " + defaultAlgorithm + " found");
        } catch (KeyStoreException | NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }
}
