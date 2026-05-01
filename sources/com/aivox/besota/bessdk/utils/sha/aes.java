package com.aivox.besota.bessdk.utils.sha;

import com.tencent.beacon.pack.AbstractJceStruct;
import com.tencent.cos.xml.crypto.JceEncryptionConstants;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class aes {
    public static byte[] decrypt(byte[] bArr, byte[] bArr2) {
        Cipher cipher;
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, JceEncryptionConstants.SYMMETRIC_KEY_ALGORITHM);
        try {
            cipher = Cipher.getInstance("AES/CBC/NoPadding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            cipher = null;
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
            cipher = null;
        }
        try {
            cipher.init(2, secretKeySpec, new IvParameterSpec(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, AbstractJceStruct.STRUCT_END, AbstractJceStruct.ZERO_TAG, AbstractJceStruct.SIMPLE_LIST, 14, 15}));
        } catch (InvalidAlgorithmParameterException e3) {
            e3.printStackTrace();
        } catch (InvalidKeyException e4) {
            e4.printStackTrace();
        }
        byte[] bArr3 = new byte[bArr.length];
        try {
            return cipher.doFinal(bArr);
        } catch (BadPaddingException e5) {
            e5.printStackTrace();
            return bArr3;
        } catch (IllegalBlockSizeException e6) {
            e6.printStackTrace();
            return bArr3;
        }
    }

    public static byte[] encrypt(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        int i = length % 16;
        int i2 = i != 0 ? (length + 16) - i : length;
        byte[] bArr3 = new byte[i2];
        System.arraycopy(bArr, 0, bArr3, 0, length);
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, JceEncryptionConstants.SYMMETRIC_KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            try {
                cipher.init(1, secretKeySpec, new IvParameterSpec(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, AbstractJceStruct.STRUCT_END, AbstractJceStruct.ZERO_TAG, AbstractJceStruct.SIMPLE_LIST, 14, 15}));
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
            byte[] bArr4 = new byte[i2];
            try {
                cipher.doFinal(bArr3, 0, i2, bArr4);
            } catch (ShortBufferException e2) {
                e2.printStackTrace();
            }
            return bArr4;
        } catch (InvalidKeyException e3) {
            e3.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e4) {
            e4.printStackTrace();
            return null;
        } catch (BadPaddingException e5) {
            e5.printStackTrace();
            return null;
        } catch (IllegalBlockSizeException e6) {
            e6.printStackTrace();
            return null;
        } catch (NoSuchPaddingException e7) {
            e7.printStackTrace();
            return null;
        }
    }

    public static void demo() {
        byte[] bArr = {17, 34, 51, 17, 34, 51, 17, 34, 51, 17, 34, 51, 51, 17, 34, 51};
        decrypt(encrypt(new byte[]{17, 34, 51, 17, 34, 51, 17, 17, 34, 51, 17, 34, 51, 17, 17, 34, 51, 17, 34, 51, 17}, bArr), bArr);
    }
}
