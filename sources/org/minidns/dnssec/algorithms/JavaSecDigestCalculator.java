package org.minidns.dnssec.algorithms;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.minidns.dnssec.DigestCalculator;

/* JADX INFO: loaded from: classes4.dex */
public class JavaSecDigestCalculator implements DigestCalculator {

    /* JADX INFO: renamed from: md */
    private MessageDigest f2049md;

    public JavaSecDigestCalculator(String str) throws NoSuchAlgorithmException {
        this.f2049md = MessageDigest.getInstance(str);
    }

    @Override // org.minidns.dnssec.DigestCalculator
    public byte[] digest(byte[] bArr) {
        return this.f2049md.digest(bArr);
    }
}
