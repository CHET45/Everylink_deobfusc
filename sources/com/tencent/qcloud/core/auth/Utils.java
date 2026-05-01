package com.tencent.qcloud.core.auth;

import com.tencent.qcloud.core.util.QCloudStringUtils;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes4.dex */
public class Utils {
    private static final char[] DIGITS_LOWER;
    private static final char[] DIGITS_UPPER;
    private static Calendar calendar;

    static {
        Calendar calendar2 = Calendar.getInstance();
        calendar = calendar2;
        calendar2.set(2010, 0, 1);
        DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        DIGITS_UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    }

    static long handleTimeAccuracy(long j) {
        return j > calendar.getTime().getTime() ? j / 1000 : j;
    }

    public static byte[] decodeHex(String str) {
        return decodeHex(str.toCharArray());
    }

    public static byte[] decodeHex(char[] cArr) {
        int length = cArr.length;
        if ((length & 1) != 0) {
            throw new IllegalArgumentException("Odd number of characters.");
        }
        byte[] bArr = new byte[length >> 1];
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = i + 1;
            int digit = (toDigit(cArr[i], i) << 4) | toDigit(cArr[i3], i3);
            i += 2;
            bArr[i2] = (byte) (digit & 255);
            i2++;
        }
        return bArr;
    }

    public static char[] encodeHex(byte[] bArr) {
        return encodeHex(bArr, true);
    }

    public static char[] encodeHex(byte[] bArr, boolean z) {
        return encodeHex(bArr, z ? DIGITS_LOWER : DIGITS_UPPER);
    }

    public static String encodeHexString(byte[] bArr) {
        return new String(encodeHex(bArr));
    }

    public static String encodeHexString(byte[] bArr, boolean z) {
        return new String(encodeHex(bArr, z));
    }

    public static byte[] sha1(String str) {
        try {
            return MessageDigest.getInstance("SHA-1").digest(QCloudStringUtils.getBytesUTF8(str));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static byte[] hmacSha1(String str, String str2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(QCloudStringUtils.getBytesUTF8(str2), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKeySpec);
            return mac.doFinal(QCloudStringUtils.getBytesUTF8(str));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    static char[] encodeHex(byte[] bArr, char[] cArr) {
        char[] cArr2 = new char[bArr.length << 1];
        int i = 0;
        for (byte b : bArr) {
            int i2 = i + 1;
            cArr2[i] = cArr[(b & 240) >>> 4];
            i += 2;
            cArr2[i2] = cArr[b & 15];
        }
        return cArr2;
    }

    static int toDigit(char c, int i) {
        int iDigit = Character.digit(c, 16);
        if (iDigit != -1) {
            return iDigit;
        }
        throw new IllegalArgumentException("Illegal hexadecimal character " + c + " at index " + i);
    }

    static long[] parseKeyTimes(String str) {
        String[] strArrSplit = str.split(";");
        return new long[]{Long.parseLong(strArrSplit[0]), Long.parseLong(strArrSplit[1])};
    }
}
