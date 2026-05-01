package com.github.houbb.heaven.util.secrect;

import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.util.internal.Base64;
import com.lzy.okgo.cache.CacheHelper;
import com.tencent.cos.xml.crypto.JceEncryptionConstants;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes3.dex */
public final class AesUtil {
    private AesUtil() {
    }

    public static String encrypt(String str, String str2) {
        ArgUtil.notEmpty(str, "sourceText");
        ArgUtil.notEmpty(str2, CacheHelper.KEY);
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), JceEncryptionConstants.SYMMETRIC_KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(JceEncryptionConstants.SYMMETRIC_CIPHER_METHOD);
            cipher.init(1, secretKeySpec, new IvParameterSpec("0102030405060708".getBytes()));
            return Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes()));
        } catch (Exception e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static String decrypt(String str, String str2) {
        ArgUtil.notEmpty(str, "sourceText");
        ArgUtil.notEmpty(str2, CacheHelper.KEY);
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(StandardCharsets.UTF_8), JceEncryptionConstants.SYMMETRIC_KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(JceEncryptionConstants.SYMMETRIC_CIPHER_METHOD);
            cipher.init(2, secretKeySpec, new IvParameterSpec("0102030405060708".getBytes()));
            return new String(cipher.doFinal(Base64.getDecoder().decode(str)));
        } catch (Exception e) {
            throw new CommonRuntimeException(e);
        }
    }
}
