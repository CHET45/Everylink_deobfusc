package com.jieli.jl_bt_ota.util;

import com.jieli.jl_bt_ota.constant.BluetoothConstant;
import com.jieli.jl_bt_ota.model.BleScanMessage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import kotlin.UByte;

/* JADX INFO: loaded from: classes3.dex */
public class ParseDataUtil {

    /* JADX INFO: renamed from: a */
    private static final String f800a = "ParseDataUtil";

    /* JADX INFO: renamed from: b */
    private static final char[] f801b = "0123456789ABCDEF".toCharArray();

    static {
        System.loadLibrary(BluetoothConstant.JL_AUTH);
    }

    /* JADX INFO: renamed from: a */
    private static byte[] m857a(byte[] bArr) {
        if (bArr == null || bArr.length <= 1) {
            return bArr;
        }
        byte[] bArr2 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr2[i] = bArr[(bArr.length - i) - 1];
        }
        return bArr2;
    }

    public static int filterFile(byte[] bArr, int i, int i2) {
        return nativeFilterFile(bArr, i, i2, 0);
    }

    public static String hexDataCovetToAddress(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        if (bArr != null && bArr.length == 6) {
            for (int i = 0; i < bArr.length; i++) {
                char[] cArr = f801b;
                sb.append(cArr[(bArr[i] & UByte.MAX_VALUE) >> 4]);
                sb.append(cArr[bArr[i] & 15]);
                if (i != bArr.length - 1) {
                    sb.append(":");
                }
            }
        }
        return sb.toString();
    }

    private static native int nativeFilterFile(byte[] bArr, int i, int i2, int i3);

    public static BleScanMessage parseOTAFlagFilterWithBroad(byte[] bArr, String str) {
        BleScanMessage withOTAFlagFilter = null;
        if (bArr != null && bArr.length > 2) {
            int i = 0;
            while (true) {
                int i2 = i + 2;
                if (i2 <= bArr.length) {
                    int iByteToInt = CHexConver.byteToInt(bArr[i]);
                    if (iByteToInt != 0) {
                        if (iByteToInt < 1) {
                            break;
                        }
                        int i3 = i + 1;
                        if (i3 + iByteToInt >= bArr.length) {
                            break;
                        }
                        if (CHexConver.byteToInt(bArr[i3]) == 255) {
                            int i4 = iByteToInt - 1;
                            byte[] bArr2 = new byte[i4];
                            System.arraycopy(bArr, i2, bArr2, 0, i4);
                            withOTAFlagFilter = parseWithOTAFlagFilter(bArr2, str);
                            if (withOTAFlagFilter != null) {
                                return withOTAFlagFilter;
                            }
                        }
                        i += iByteToInt + 1;
                    } else {
                        i++;
                    }
                } else {
                    break;
                }
            }
        }
        return withOTAFlagFilter;
    }

    public static BleScanMessage parseWithOTAFlagFilter(byte[] bArr, String str) {
        if (bArr == null || str == null || str.length() == 0 || bArr.length <= str.getBytes().length + 2) {
            return null;
        }
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        byteBufferWrap.order(ByteOrder.LITTLE_ENDIAN);
        short s = byteBufferWrap.getShort();
        byte[] bArr2 = new byte[str.getBytes().length];
        ByteBuffer byteBuffer = byteBufferWrap.get(bArr2);
        byte[] bArrM857a = m857a(bArr2);
        if (!Arrays.equals(bArrM857a, str.getBytes())) {
            JL_Log.m848i(f800a, CommonUtil.formatString("parseWithOTAFlagFilter :: flag not match. adv flag : %s, flag : %s", CHexConver.byte2HexStr(bArrM857a), CHexConver.byte2HexStr(str.getBytes())));
            return null;
        }
        int iByteToInt = CHexConver.byteToInt(byteBuffer.get());
        if (iByteToInt != 1) {
            byte[] bArr3 = new byte[6];
            ByteBuffer byteBuffer2 = byteBuffer.get(bArr3);
            String strHexDataCovetToAddress = hexDataCovetToAddress(m857a(bArr3));
            byteBuffer2.get(new byte[byteBuffer2.remaining()]);
            return new BleScanMessage().setVid(s).setOTA(true).setIdentify(str).setVersion(iByteToInt).setOldBleAddress(strHexDataCovetToAddress);
        }
        byte[] bArr4 = new byte[6];
        ByteBuffer byteBuffer3 = byteBuffer.get(bArr4);
        String strHexDataCovetToAddress2 = hexDataCovetToAddress(m857a(bArr4));
        short s2 = byteBuffer3.getShort();
        short s3 = byteBuffer3.getShort();
        byte b = byteBuffer3.get();
        int iByteToInt2 = CHexConver.byteToInt(byteBuffer3.get());
        byteBuffer3.get(new byte[byteBuffer3.remaining()]);
        return new BleScanMessage().setOTA(true).setIdentify(str).setVersion(iByteToInt).setOldBleAddress(strHexDataCovetToAddress2).setVid(s).setUid(s2).setPid(s3).setDeviceType((b >> 4) & 255).setDeviceVersion(b & 15).setBattery(iByteToInt2);
    }
}
