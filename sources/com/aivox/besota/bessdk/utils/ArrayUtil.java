package com.aivox.besota.bessdk.utils;

import android.util.Log;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.github.houbb.heaven.constant.CharConst;
import com.github.houbb.heaven.constant.PunctuationConst;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.zip.CRC32;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
public class ArrayUtil {
    public static short getShort(byte b, byte b2) {
        return (short) ((b & UByte.MAX_VALUE) | (b2 << 8));
    }

    public static byte[] extractBytes(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bArr2;
    }

    public static boolean isEqual(byte[] bArr, byte[] bArr2) {
        if (bArr == null) {
            return bArr2 == null;
        }
        if (bArr2 == null) {
            return false;
        }
        if (bArr == bArr2) {
            return true;
        }
        if (bArr.length != bArr2.length) {
            return false;
        }
        for (int i = 0; i < bArr.length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean contains(byte[] bArr, byte[] bArr2) {
        if (bArr == null) {
            return bArr2 == null;
        }
        if (bArr2 == null || bArr2.length == 0 || bArr == bArr2) {
            return true;
        }
        return new String(bArr).contains(new String(bArr2));
    }

    public static long crc32(byte[] bArr, int i, int i2) {
        CRC32 crc32 = new CRC32();
        crc32.update(bArr, i, i2);
        return crc32.getValue();
    }

    public static byte checkSum(byte[] bArr, int i) {
        byte b = 0;
        for (int i2 = 0; i2 < i; i2++) {
            b = (byte) (b ^ bArr[i2]);
        }
        return b;
    }

    public static String toHex(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            stringBuffer.append(String.format("%02x", Byte.valueOf(b))).append(PunctuationConst.COMMA);
        }
        return stringBuffer.toString();
    }

    public static String toASCII(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            stringBuffer.append((char) b);
        }
        return stringBuffer.toString();
    }

    public static boolean startsWith(byte[] bArr, byte[] bArr2) {
        if (bArr == null) {
            return bArr2 == null;
        }
        if (bArr2 == null) {
            return true;
        }
        if (bArr.length < bArr2.length) {
            return false;
        }
        for (int i = 0; i < bArr2.length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean endWith(byte[] bArr, byte[] bArr2) {
        if (bArr == null) {
            return bArr2 == null;
        }
        if (bArr2 == null) {
            return true;
        }
        if (bArr.length < bArr2.length) {
            return false;
        }
        for (int i = 0; i < bArr2.length; i++) {
            if (bArr[(bArr.length - 1) - i] != bArr2[(bArr2.length - 1) - i]) {
                return false;
            }
        }
        return true;
    }

    public static byte[] toBytes(String str) {
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }
        String strReplace = str.replace(PunctuationConst.COMMA, "");
        if (strReplace == null || strReplace.trim().equals("")) {
            return new byte[0];
        }
        byte[] bArr = new byte[strReplace.length() / 2];
        for (int i = 0; i < strReplace.length() / 2; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) Integer.parseInt(strReplace.substring(i2, i2 + 2), 16);
        }
        return bArr;
    }

    public static int bytesToIntLittle(byte[] bArr) {
        return ((bArr[3] << 24) & ViewCompat.MEASURED_STATE_MASK) | (bArr[0] & UByte.MAX_VALUE) | ((bArr[1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ((bArr[2] << 16) & 16711680);
    }

    public static String bytesToVersion(byte[] bArr) {
        int[] iArr = new int[4];
        String str = "";
        if (bArr.length == 4) {
            for (int i = 0; i < bArr.length; i++) {
                iArr[i] = bArr[i] & UByte.MAX_VALUE;
                String str2 = str + String.valueOf(iArr[i]);
                if (i < 3) {
                    str2 = str2 + ".";
                }
                str = str2;
            }
        }
        return str;
    }

    public static byte[] intToBytesLittle(int i) {
        return new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 24) & 255)};
    }

    public static int bytesToShortLittle(byte[] bArr) {
        return (short) (((bArr[1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[0] & UByte.MAX_VALUE));
    }

    public static byte[] hexStringToByte(String str) {
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        char[] charArray = str.toCharArray();
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[(length - i) - 1] = (byte) (toByte(charArray[i2 + 1]) | (toByte(charArray[i2]) << 4));
        }
        return bArr;
    }

    private static int toByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String bytesToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder("");
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & UByte.MAX_VALUE);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    public static byte[] int2byte(long j) {
        return new byte[]{(byte) (j & 255), (byte) ((j >> 8) & 255), (byte) (255 & (j >> 16)), (byte) (j >>> 24)};
    }

    public static long getFloat(byte[] bArr) {
        return ((bArr[3] & UByte.MAX_VALUE) << 24) | (bArr[0] & UByte.MAX_VALUE) | ((bArr[1] & UByte.MAX_VALUE) << 8) | ((bArr[2] & UByte.MAX_VALUE) << 16);
    }

    public static byte[] bytesSplic(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
        byte[] bArr5 = new byte[bArr.length + bArr2.length + bArr3.length + bArr4.length];
        System.arraycopy(bArr, 0, bArr5, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr5, bArr.length, bArr2.length);
        System.arraycopy(bArr3, 0, bArr5, bArr.length + bArr2.length, bArr3.length);
        System.arraycopy(bArr4, 0, bArr5, bArr.length + bArr2.length + bArr3.length, bArr4.length);
        return bArr5;
    }

    public static byte[] byteMerger(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    public static String str2HexStr(String str) {
        char[] charArray = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bytes = str.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(charArray[(bytes[i] & 240) >> 4]);
            sb.append(charArray[bytes[i] & 15]);
            sb.append(CharConst.COMMA);
        }
        return sb.toString().trim();
    }

    public static int getRssiValue(byte b) {
        return Integer.valueOf(b).intValue();
    }

    public static float byte2float(byte[] bArr) {
        return Float.intBitsToFloat((int) (((long) (((int) (((long) (((int) (((long) (bArr[0] & UByte.MAX_VALUE)) | (((long) bArr[1]) << 8))) & 65535)) | (((long) bArr[2]) << 16))) & ViewCompat.MEASURED_SIZE_MASK)) | (((long) bArr[3]) << 24)));
    }

    public static byte[] float2byte(float f) {
        int iFloatToIntBits = Float.floatToIntBits(f);
        byte[] bArr = new byte[4];
        for (int i = 0; i < 4; i++) {
            bArr[i] = (byte) (iFloatToIntBits >> (24 - (i * 8)));
        }
        byte[] bArr2 = new byte[4];
        System.arraycopy(bArr, 0, bArr2, 0, 4);
        for (int i2 = 0; i2 < 2; i2++) {
            byte b = bArr2[i2];
            int i3 = 3 - i2;
            bArr2[i2] = bArr2[i3];
            bArr2[i3] = b;
        }
        return bArr2;
    }

    public static String div(double d, double d2, int i) {
        if (i < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        double dDoubleValue = new BigDecimal(Double.toString(d)).divide(new BigDecimal(Double.toString(d2)), i, 4).doubleValue();
        Log.i("TAG", "sendPacketData: -----div--" + dDoubleValue);
        return getTwoDecimal(dDoubleValue).replace(PunctuationConst.COMMA, ".");
    }

    public static String getTwoDecimal(double d) {
        try {
            return new DecimalFormat("0.00").format(d);
        } catch (Exception unused) {
            return "";
        }
    }

    public static int byte2int(byte[] bArr) {
        return ((bArr[1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[0] & UByte.MAX_VALUE);
    }

    public static String byteToBit(byte b) {
        return "" + ((int) ((byte) ((b >> 7) & 1))) + ((int) ((byte) ((b >> 6) & 1))) + ((int) ((byte) ((b >> 5) & 1))) + ((int) ((byte) ((b >> 4) & 1))) + ((int) ((byte) ((b >> 3) & 1))) + ((int) ((byte) ((b >> 2) & 1))) + ((int) ((byte) ((b >> 1) & 1))) + ((int) ((byte) (b & 1)));
    }

    public static byte bitToByte(String str) {
        int i;
        if (str == null) {
            return (byte) 0;
        }
        int length = str.length();
        if (length != 4 && length != 8) {
            return (byte) 0;
        }
        if (length != 8 || str.charAt(0) == '0') {
            i = Integer.parseInt(str, 2);
        } else {
            i = Integer.parseInt(str, 2) + InputDeviceCompat.SOURCE_ANY;
        }
        return (byte) i;
    }

    public static void addObjectIndex(List list, Object obj, int i) {
        if (list.size() > i) {
            list.remove(i);
            list.add(i, obj);
        }
    }

    public static void resetObjectAtIndex(List list, int i) {
        if (list.size() > i) {
            list.remove(i);
            list.add(i, null);
        }
    }
}
