package org.minidns.dnssec.algorithms;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.minidns.constants.DnssecConstants;
import org.minidns.dnssec.DigestCalculator;
import org.minidns.dnssec.DnssecValidatorInitializationException;
import org.minidns.dnssec.SignatureVerifier;
import org.minidns.dnssec.algorithms.EcdsaSignatureVerifier;
import org.minidns.record.NSEC3;

/* JADX INFO: loaded from: classes4.dex */
public final class AlgorithmMap {
    public static final AlgorithmMap INSTANCE = new AlgorithmMap();
    private Logger LOGGER = Logger.getLogger(AlgorithmMap.class.getName());
    private final Map<DnssecConstants.DigestAlgorithm, DigestCalculator> dsDigestMap;
    private final Map<NSEC3.HashAlgorithm, DigestCalculator> nsecDigestMap;
    private final Map<DnssecConstants.SignatureAlgorithm, SignatureVerifier> signatureMap;

    private AlgorithmMap() {
        HashMap map = new HashMap();
        this.dsDigestMap = map;
        HashMap map2 = new HashMap();
        this.signatureMap = map2;
        HashMap map3 = new HashMap();
        this.nsecDigestMap = map3;
        try {
            map.put(DnssecConstants.DigestAlgorithm.SHA1, new JavaSecDigestCalculator("SHA-1"));
            map3.put(NSEC3.HashAlgorithm.SHA1, new JavaSecDigestCalculator("SHA-1"));
            try {
                map.put(DnssecConstants.DigestAlgorithm.SHA256, new JavaSecDigestCalculator("SHA-256"));
                try {
                    map2.put(DnssecConstants.SignatureAlgorithm.RSAMD5, new RsaSignatureVerifier("MD5withRSA"));
                } catch (NoSuchAlgorithmException e) {
                    this.LOGGER.log(Level.FINER, "Platform does not support RSA/MD5", (Throwable) e);
                }
                try {
                    DsaSignatureVerifier dsaSignatureVerifier = new DsaSignatureVerifier("SHA1withDSA");
                    this.signatureMap.put(DnssecConstants.SignatureAlgorithm.DSA, dsaSignatureVerifier);
                    this.signatureMap.put(DnssecConstants.SignatureAlgorithm.DSA_NSEC3_SHA1, dsaSignatureVerifier);
                } catch (NoSuchAlgorithmException e2) {
                    this.LOGGER.log(Level.FINE, "Platform does not support DSA/SHA-1", (Throwable) e2);
                }
                try {
                    RsaSignatureVerifier rsaSignatureVerifier = new RsaSignatureVerifier("SHA1withRSA");
                    this.signatureMap.put(DnssecConstants.SignatureAlgorithm.RSASHA1, rsaSignatureVerifier);
                    this.signatureMap.put(DnssecConstants.SignatureAlgorithm.RSASHA1_NSEC3_SHA1, rsaSignatureVerifier);
                    try {
                        this.signatureMap.put(DnssecConstants.SignatureAlgorithm.RSASHA256, new RsaSignatureVerifier("SHA256withRSA"));
                    } catch (NoSuchAlgorithmException e3) {
                        this.LOGGER.log(Level.INFO, "Platform does not support RSA/SHA-256", (Throwable) e3);
                    }
                    try {
                        this.signatureMap.put(DnssecConstants.SignatureAlgorithm.RSASHA512, new RsaSignatureVerifier("SHA512withRSA"));
                    } catch (NoSuchAlgorithmException e4) {
                        this.LOGGER.log(Level.INFO, "Platform does not support RSA/SHA-512", (Throwable) e4);
                    }
                    try {
                        this.signatureMap.put(DnssecConstants.SignatureAlgorithm.ECC_GOST, new EcgostSignatureVerifier());
                    } catch (NoSuchAlgorithmException e5) {
                        this.LOGGER.log(Level.FINE, "Platform does not support GOST R 34.10-2001", (Throwable) e5);
                    }
                    try {
                        this.signatureMap.put(DnssecConstants.SignatureAlgorithm.ECDSAP256SHA256, new EcdsaSignatureVerifier.P256SHA256());
                    } catch (NoSuchAlgorithmException e6) {
                        this.LOGGER.log(Level.INFO, "Platform does not support ECDSA/SHA-256", (Throwable) e6);
                    }
                    try {
                        this.signatureMap.put(DnssecConstants.SignatureAlgorithm.ECDSAP384SHA384, new EcdsaSignatureVerifier.P384SHA284());
                    } catch (NoSuchAlgorithmException e7) {
                        this.LOGGER.log(Level.INFO, "Platform does not support ECDSA/SHA-384", (Throwable) e7);
                    }
                } catch (NoSuchAlgorithmException e8) {
                    throw new DnssecValidatorInitializationException("Platform does not support RSA/SHA-1", e8);
                }
            } catch (NoSuchAlgorithmException e9) {
                throw new DnssecValidatorInitializationException("SHA-256 is mandatory", e9);
            }
        } catch (NoSuchAlgorithmException e10) {
            throw new DnssecValidatorInitializationException("SHA-1 is mandatory", e10);
        }
    }

    public DigestCalculator getDsDigestCalculator(DnssecConstants.DigestAlgorithm digestAlgorithm) {
        return this.dsDigestMap.get(digestAlgorithm);
    }

    public SignatureVerifier getSignatureVerifier(DnssecConstants.SignatureAlgorithm signatureAlgorithm) {
        return this.signatureMap.get(signatureAlgorithm);
    }

    public DigestCalculator getNsecDigestCalculator(NSEC3.HashAlgorithm hashAlgorithm) {
        return this.nsecDigestMap.get(hashAlgorithm);
    }
}
