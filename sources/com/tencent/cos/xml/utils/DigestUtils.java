package com.tencent.cos.xml.utils;

import android.text.TextUtils;
import android.util.Base64;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.exception.CosXmlClientException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes4.dex */
public class DigestUtils {
    public static String getMD5(String str) throws Throwable {
        Throwable th;
        NoSuchAlgorithmException e;
        IOException e2;
        FileNotFoundException e3;
        MessageDigest messageDigest;
        FileInputStream fileInputStream;
        if (str == null) {
            throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "file Path is null");
        }
        File file = new File(str);
        if (!file.exists()) {
            throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "file Path is not exist");
        }
        try {
            try {
                messageDigest = MessageDigest.getInstance("MD5");
                fileInputStream = new FileInputStream(file);
            } catch (FileNotFoundException e4) {
                e3 = e4;
            } catch (IOException e5) {
                e2 = e5;
            } catch (NoSuchAlgorithmException e6) {
                e = e6;
            } catch (Throwable th2) {
                th = th2;
                CloseUtil.closeQuietly(null);
                throw th;
            }
            try {
                byte[] bArr = new byte[32768];
                while (true) {
                    int i = fileInputStream.read(bArr);
                    if (i != -1) {
                        messageDigest.update(bArr, 0, i);
                    } else {
                        String hexString = StringUtils.toHexString(messageDigest.digest());
                        CloseUtil.closeQuietly(fileInputStream);
                        return hexString;
                    }
                }
            } catch (FileNotFoundException e7) {
                e3 = e7;
                throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), e3);
            } catch (IOException e8) {
                e2 = e8;
                throw new CosXmlClientException(ClientErrorCode.IO_ERROR.getCode(), e2);
            } catch (NoSuchAlgorithmException e9) {
                e = e9;
                throw new CosXmlClientException(ClientErrorCode.INTERNAL_ERROR.getCode(), e);
            }
        } catch (Throwable th3) {
            th = th3;
            CloseUtil.closeQuietly(null);
            throw th;
        }
    }

    public static long getBigIntFromString(String str) {
        return new BigInteger(str).longValue();
    }

    public static String getBigIntToString(long j) {
        BigInteger bigIntegerValueOf = BigInteger.valueOf((j >> 1) & 4611686018427387904L);
        return bigIntegerValueOf.add(bigIntegerValueOf).add(BigInteger.valueOf(j & Long.MAX_VALUE)).toString();
    }

    public static String getCRC64String(InputStream inputStream) {
        return getBigIntToString(getCRC64(inputStream));
    }

    public static String getCRC64String(File file) throws Throwable {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (Exception unused) {
        } catch (Throwable th) {
            th = th;
        }
        try {
            String cRC64String = getCRC64String(fileInputStream);
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return cRC64String;
        } catch (Exception unused2) {
            fileInputStream2 = fileInputStream;
            try {
                fileInputStream2.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return "";
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            try {
                fileInputStream2.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            throw th;
        }
    }

    public static long getCRC64(InputStream inputStream) {
        try {
            CRC64 crc64 = new CRC64();
            byte[] bArr = new byte[8192];
            while (true) {
                int i = inputStream.read(bArr);
                if (i != -1) {
                    crc64.update(bArr, i);
                } else {
                    return crc64.getValue();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public static long getCRC64(InputStream inputStream, long j, long j2) {
        int i;
        try {
            if (inputStream.skip(j) != j) {
                return -1L;
            }
            CRC64 crc64 = new CRC64();
            byte[] bArr = new byte[8192];
            if (j2 < 0) {
                j2 = Long.MAX_VALUE;
            }
            int iMin = (int) Math.min(j2, 8192);
            while (j2 > 0 && (i = inputStream.read(bArr, 0, iMin)) != -1) {
                crc64.update(bArr, i);
                j2 -= (long) i;
            }
            return crc64.getValue();
        } catch (IOException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public static String getCOSMd5(InputStream inputStream, long j, long j2) throws IOException {
        long j3 = 0;
        while (j3 < j) {
            try {
                long jSkip = inputStream.skip(j - j3);
                if (jSkip == 0) {
                    throw new IOException("Failed to skip requested bytes");
                }
                j3 += jSkip;
            } catch (NoSuchAlgorithmException e) {
                throw new IOException("Unsupported MD5 algorithm", e);
            }
        }
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] bArr = new byte[8192];
        if (j2 < 0) {
            j2 = Long.MAX_VALUE;
        }
        while (j2 > 0) {
            int iMin = (int) Math.min(j2, 8192);
            int i = 0;
            while (i < iMin) {
                int i2 = inputStream.read(bArr, i, iMin - i);
                if (i2 == -1) {
                    throw new IOException("Unexpected end of stream");
                }
                i += i2;
            }
            messageDigest.update(bArr, 0, i);
            j2 -= (long) i;
        }
        return PunctuationConst.DOUBLE_QUOTES + StringUtils.toHexString(messageDigest.digest()) + PunctuationConst.DOUBLE_QUOTES;
    }

    public static COSMd5AndReadData getCOSMd5AndReadData(InputStream inputStream, int i) throws IOException {
        try {
            byte[] bArr = new byte[i];
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            int i2 = inputStream.read(bArr, 0, i);
            if (i2 != -1) {
                if (i2 < i) {
                    return new COSMd5AndReadData("", subByte(bArr, 0, i2));
                }
                messageDigest.update(bArr, 0, i2);
            }
            return new COSMd5AndReadData(PunctuationConst.DOUBLE_QUOTES + StringUtils.toHexString(messageDigest.digest()) + PunctuationConst.DOUBLE_QUOTES, subByte(bArr, 0, i2));
        } catch (IOException e) {
            throw e;
        } catch (NoSuchAlgorithmException e2) {
            throw new IOException("unSupport Md5 algorithm", e2);
        }
    }

    private static byte[] subByte(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bArr2;
    }

    public static class COSMd5AndReadData {
        public String md5;
        public byte[] readData;

        public COSMd5AndReadData(String str, byte[] bArr) {
            this.md5 = str;
            this.readData = bArr;
        }
    }

    public static String getSha1(String str) throws CosXmlClientException {
        try {
            return StringUtils.toHexString(MessageDigest.getInstance("SHA-1").digest(str.getBytes(Charset.forName("UTF-8"))));
        } catch (NoSuchAlgorithmException e) {
            throw new CosXmlClientException(ClientErrorCode.INTERNAL_ERROR.getCode(), e);
        }
    }

    public static String getSHA1FromPath(String str) throws Throwable {
        FileInputStream fileInputStream = null;
        try {
            try {
                FileInputStream fileInputStream2 = new FileInputStream(str);
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
                    byte[] bArr = new byte[65536];
                    while (true) {
                        int i = fileInputStream2.read(bArr, 0, 65536);
                        if (i != -1) {
                            messageDigest.update(bArr, 0, i);
                        } else {
                            String hexString = StringUtils.toHexString(messageDigest.digest());
                            CloseUtil.closeQuietly(fileInputStream2);
                            return hexString;
                        }
                    }
                } catch (FileNotFoundException e) {
                    e = e;
                    throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), e);
                } catch (IOException e2) {
                    e = e2;
                    throw new CosXmlClientException(ClientErrorCode.IO_ERROR.getCode(), e);
                } catch (NoSuchAlgorithmException e3) {
                    e = e3;
                    throw new CosXmlClientException(ClientErrorCode.INTERNAL_ERROR.getCode(), e);
                } catch (Throwable th) {
                    th = th;
                    fileInputStream = fileInputStream2;
                    CloseUtil.closeQuietly(fileInputStream);
                    throw th;
                }
            } catch (FileNotFoundException e4) {
                e = e4;
            } catch (IOException e5) {
                e = e5;
            } catch (NoSuchAlgorithmException e6) {
                e = e6;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static String getSHA1FromBytes(byte[] bArr, int i, int i2) throws CosXmlClientException {
        if (bArr == null || i2 <= 0 || i < 0) {
            throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "data == null | len <= 0 |offset < 0 |offset >= len");
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(bArr, i, i2);
            return StringUtils.toHexString(messageDigest.digest());
        } catch (OutOfMemoryError e) {
            throw new CosXmlClientException(ClientErrorCode.INTERNAL_ERROR.getCode(), e);
        } catch (NoSuchAlgorithmException e2) {
            throw new CosXmlClientException(ClientErrorCode.INTERNAL_ERROR.getCode(), e2);
        }
    }

    public static String getHmacSha1(String str, String str2) throws CosXmlClientException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(Charset.forName("UTF-8")), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKeySpec);
            return StringUtils.toHexString(mac.doFinal(str.getBytes(Charset.forName("UTF-8"))));
        } catch (InvalidKeyException e) {
            throw new CosXmlClientException(ClientErrorCode.INTERNAL_ERROR.getCode(), e);
        } catch (NoSuchAlgorithmException e2) {
            throw new CosXmlClientException(ClientErrorCode.INTERNAL_ERROR.getCode(), e2);
        }
    }

    public static String decodeBase64(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            return new String(Base64.decode(str, 0), "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return str;
        }
    }

    public static String getBase64(String str) throws CosXmlClientException {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            return Base64.encodeToString(str.getBytes("utf-8"), 2);
        } catch (UnsupportedEncodingException e) {
            throw new CosXmlClientException(ClientErrorCode.INTERNAL_ERROR.getCode(), e);
        }
    }

    public static String getSecurityBase64(String str) throws CosXmlClientException {
        String base64 = getBase64(str);
        return TextUtils.isEmpty(base64) ? base64 : base64.replace(Marker.ANY_NON_NULL_MARKER, "-").replace("/", PunctuationConst.UNDERLINE);
    }
}
