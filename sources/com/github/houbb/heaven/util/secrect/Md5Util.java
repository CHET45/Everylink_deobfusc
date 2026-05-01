package com.github.houbb.heaven.util.secrect;

import com.github.houbb.heaven.util.codec.Base64;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.ArrayPrimitiveUtil;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes3.dex */
public final class Md5Util {
    private Md5Util() {
    }

    public static String md5(String str, Charset charset) {
        return StringUtil.isEmpty(str) ? str : md5(str.getBytes(charset));
    }

    public static String md5(byte[] bArr) {
        if (ArrayPrimitiveUtil.isEmpty(bArr)) {
            return null;
        }
        try {
            return Base64.encodeToString(MessageDigest.getInstance("MD5").digest(bArr));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String md5(String str) {
        return md5(str, StandardCharsets.UTF_8);
    }
}
