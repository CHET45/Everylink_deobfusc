package com.aivox.besota.bessdk.utils.sha;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public class Sha256 {
    public static byte[] getSHA256(byte[] bArr) {
        byte[] bArr2 = new byte[0];
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bArr);
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return bArr2;
        }
    }
}
