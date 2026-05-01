package org.minidns.dane;

import com.github.houbb.heaven.constant.PunctuationConst;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.minidns.dane.DaneCertificateException;
import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsname.DnsName;
import org.minidns.dnssec.DnssecClient;
import org.minidns.dnssec.DnssecQueryResult;
import org.minidns.dnssec.DnssecUnverifiedReason;
import org.minidns.record.Data;
import org.minidns.record.Record;
import org.minidns.record.TLSA;

/* JADX INFO: loaded from: classes4.dex */
public class DaneVerifier {
    private static final Logger LOGGER = Logger.getLogger(DaneVerifier.class.getName());
    private final DnssecClient client;

    public DaneVerifier() {
        this(new DnssecClient());
    }

    public DaneVerifier(DnssecClient dnssecClient) {
        this.client = dnssecClient;
    }

    public boolean verify(SSLSocket sSLSocket) throws CertificateException {
        if (!sSLSocket.isConnected()) {
            throw new IllegalStateException("Socket not yet connected.");
        }
        return verify(sSLSocket.getSession());
    }

    public boolean verify(SSLSession sSLSession) throws CertificateException {
        try {
            return verifyCertificateChain(convert(sSLSession.getPeerCertificates()), sSLSession.getPeerHost(), sSLSession.getPeerPort());
        } catch (SSLPeerUnverifiedException e) {
            throw new CertificateException("Peer not verified", e);
        }
    }

    public boolean verifyCertificateChain(X509Certificate[] x509CertificateArr, String str, int i) throws CertificateException {
        DnsName dnsNameFrom = DnsName.from(PunctuationConst.UNDERLINE + i + "._tcp." + str);
        try {
            DnssecQueryResult dnssecQueryResultQueryDnssec = this.client.queryDnssec(dnsNameFrom, Record.TYPE.TLSA);
            DnsMessage dnsMessage = dnssecQueryResultQueryDnssec.dnsQueryResult.response;
            if (!dnssecQueryResultQueryDnssec.isAuthenticData()) {
                Iterator<DnssecUnverifiedReason> it = dnssecQueryResultQueryDnssec.getUnverifiedReasons().iterator();
                String str2 = "Got TLSA response from DNS server, but was not signed properly. Reasons:";
                while (it.hasNext()) {
                    str2 = str2 + " " + it.next();
                }
                LOGGER.info(str2);
                return false;
            }
            LinkedList linkedList = new LinkedList();
            boolean zCheckCertificateMatches = false;
            for (Record<? extends Data> record : dnsMessage.answerSection) {
                if (record.type == Record.TYPE.TLSA && record.name.equals(dnsNameFrom)) {
                    try {
                        zCheckCertificateMatches |= checkCertificateMatches(x509CertificateArr[0], (TLSA) record.payloadData, str);
                    } catch (DaneCertificateException.CertificateMismatch e) {
                        linkedList.add(e);
                    }
                    if (zCheckCertificateMatches) {
                        break;
                    }
                }
            }
            if (zCheckCertificateMatches || linkedList.isEmpty()) {
                return zCheckCertificateMatches;
            }
            throw new DaneCertificateException.MultipleCertificateMismatchExceptions(linkedList);
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    private static boolean checkCertificateMatches(X509Certificate x509Certificate, TLSA tlsa, String str) throws CertificateException {
        byte[] encoded;
        if (tlsa.certUsage == null) {
            LOGGER.warning("TLSA certificate usage byte " + ((int) tlsa.certUsageByte) + " is not supported while verifying " + str);
            return false;
        }
        int i = C50741.$SwitchMap$org$minidns$record$TLSA$CertUsage[tlsa.certUsage.ordinal()];
        if (i != 1 && i != 2) {
            LOGGER.warning("TLSA certificate usage " + tlsa.certUsage + " (" + ((int) tlsa.certUsageByte) + ") not supported while verifying " + str);
            return false;
        }
        if (tlsa.selector == null) {
            LOGGER.warning("TLSA selector byte " + ((int) tlsa.selectorByte) + " is not supported while verifying " + str);
            return false;
        }
        int i2 = C50741.$SwitchMap$org$minidns$record$TLSA$Selector[tlsa.selector.ordinal()];
        if (i2 == 1) {
            encoded = x509Certificate.getEncoded();
        } else if (i2 == 2) {
            encoded = x509Certificate.getPublicKey().getEncoded();
        } else {
            LOGGER.warning("TLSA selector " + tlsa.selector + " (" + ((int) tlsa.selectorByte) + ") not supported while verifying " + str);
            return false;
        }
        if (tlsa.matchingType == null) {
            LOGGER.warning("TLSA matching type byte " + ((int) tlsa.matchingTypeByte) + " is not supported while verifying " + str);
            return false;
        }
        int i3 = C50741.$SwitchMap$org$minidns$record$TLSA$MatchingType[tlsa.matchingType.ordinal()];
        if (i3 != 1) {
            if (i3 == 2) {
                try {
                    encoded = MessageDigest.getInstance("SHA-256").digest(encoded);
                } catch (NoSuchAlgorithmException e) {
                    throw new CertificateException("Verification using TLSA failed: could not SHA-256 for matching", e);
                }
            } else if (i3 == 3) {
                try {
                    encoded = MessageDigest.getInstance("SHA-512").digest(encoded);
                } catch (NoSuchAlgorithmException e2) {
                    throw new CertificateException("Verification using TLSA failed: could not SHA-512 for matching", e2);
                }
            } else {
                LOGGER.warning("TLSA matching type " + tlsa.matchingType + " not supported while verifying " + str);
                return false;
            }
        }
        if (tlsa.certificateAssociationEquals(encoded)) {
            return tlsa.certUsage == TLSA.CertUsage.domainIssuedCertificate;
        }
        throw new DaneCertificateException.CertificateMismatch(tlsa, encoded);
    }

    /* JADX INFO: renamed from: org.minidns.dane.DaneVerifier$1 */
    static /* synthetic */ class C50741 {
        static final /* synthetic */ int[] $SwitchMap$org$minidns$record$TLSA$CertUsage;
        static final /* synthetic */ int[] $SwitchMap$org$minidns$record$TLSA$MatchingType;
        static final /* synthetic */ int[] $SwitchMap$org$minidns$record$TLSA$Selector;

        static {
            int[] iArr = new int[TLSA.MatchingType.values().length];
            $SwitchMap$org$minidns$record$TLSA$MatchingType = iArr;
            try {
                iArr[TLSA.MatchingType.noHash.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$minidns$record$TLSA$MatchingType[TLSA.MatchingType.sha256.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$minidns$record$TLSA$MatchingType[TLSA.MatchingType.sha512.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[TLSA.Selector.values().length];
            $SwitchMap$org$minidns$record$TLSA$Selector = iArr2;
            try {
                iArr2[TLSA.Selector.fullCertificate.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$minidns$record$TLSA$Selector[TLSA.Selector.subjectPublicKeyInfo.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            int[] iArr3 = new int[TLSA.CertUsage.values().length];
            $SwitchMap$org$minidns$record$TLSA$CertUsage = iArr3;
            try {
                iArr3[TLSA.CertUsage.serviceCertificateConstraint.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$minidns$record$TLSA$CertUsage[TLSA.CertUsage.domainIssuedCertificate.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$minidns$record$TLSA$CertUsage[TLSA.CertUsage.caConstraint.ordinal()] = 3;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$minidns$record$TLSA$CertUsage[TLSA.CertUsage.trustAnchorAssertion.ordinal()] = 4;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    public HttpsURLConnection verifiedConnect(HttpsURLConnection httpsURLConnection) throws IOException, CertificateException {
        return verifiedConnect(httpsURLConnection, null);
    }

    public HttpsURLConnection verifiedConnect(HttpsURLConnection httpsURLConnection, X509TrustManager x509TrustManager) throws IOException, CertificateException {
        try {
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            ExpectingTrustManager expectingTrustManager = new ExpectingTrustManager(x509TrustManager);
            sSLContext.init(null, new TrustManager[]{expectingTrustManager}, null);
            httpsURLConnection.setSSLSocketFactory(sSLContext.getSocketFactory());
            httpsURLConnection.connect();
            if (!verifyCertificateChain(convert(httpsURLConnection.getServerCertificates()), httpsURLConnection.getURL().getHost(), httpsURLConnection.getURL().getPort() < 0 ? httpsURLConnection.getURL().getDefaultPort() : httpsURLConnection.getURL().getPort()) && expectingTrustManager.hasException()) {
                throw new IOException("Peer verification failed using PKIX", expectingTrustManager.getException());
            }
            return httpsURLConnection;
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static X509Certificate[] convert(Certificate[] certificateArr) {
        ArrayList arrayList = new ArrayList();
        for (Certificate certificate : certificateArr) {
            if (certificate instanceof X509Certificate) {
                arrayList.add((X509Certificate) certificate);
            }
        }
        return (X509Certificate[]) arrayList.toArray(new X509Certificate[arrayList.size()]);
    }
}
