package com.aivox.base.util;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
public class DataUtil {
    public static long getLongBy4Bytes(byte[] bArr, int i) {
        if (bArr == null || bArr.length < i + 4) {
            return 0L;
        }
        return (((long) bArr[i + 3]) & 255) | ((((long) bArr[i]) & 255) << 24) | ((((long) bArr[i + 1]) & 255) << 16) | ((((long) bArr[i + 2]) & 255) << 8);
    }

    public static int getIntBy2Bytes(byte[] bArr, int i) {
        if (bArr == null || bArr.length < i + 2) {
            return 0;
        }
        return (bArr[i + 1] & UByte.MAX_VALUE) | ((bArr[i] & UByte.MAX_VALUE) << 8);
    }

    public static long getIntBy2Bytes(byte[] bArr, int i, int i2) {
        int i3;
        if (bArr == null || bArr.length < i2 + i) {
            i3 = 0;
        } else {
            i3 = (bArr[i + 1] & UByte.MAX_VALUE) | ((bArr[i] & UByte.MAX_VALUE) << 8);
        }
        return i3;
    }

    public static byte[] intToByteLittle(int i) {
        return new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 24) & 255)};
    }

    public static byte[] intToByteArray(int i) {
        return new byte[]{(byte) ((i >> 24) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 8) & 255), (byte) (i & 255)};
    }

    public static byte[] intTo2ByteArray(int i) {
        return new byte[]{(byte) ((i >> 8) & 255), (byte) (i & 255), 0, 0};
    }

    public static byte[] intTo1ByteArray(int i) {
        return new byte[]{(byte) (i & 255)};
    }

    public static int byteArrayToInt(byte[] bArr) {
        int i = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            i += (bArr[i2] & UByte.MAX_VALUE) << ((3 - i2) * 8);
        }
        return i;
    }

    public static String bytes216String(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(Integer.toHexString(b)).append(":");
        }
        LogUtil.m338i(sb.toString());
        return sb.toString();
    }

    public static String bytes22String(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(Integer.toBinaryString(b)).append(":");
        }
        LogUtil.m338i(sb.toString());
        return sb.toString();
    }

    public static String bytes2Hex(byte[] bArr) {
        Formatter formatter = new Formatter();
        for (byte b : bArr) {
            formatter.format("%02x", Byte.valueOf(b));
        }
        String string = formatter.toString();
        formatter.close();
        return string;
    }

    public static String byte2Hex(byte b) {
        StringBuilder sb = new StringBuilder(Integer.toHexString(b));
        if (sb.length() > 2) {
            sb = new StringBuilder(sb.substring(sb.length() - 2));
        }
        while (sb.length() < 2) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }

    public static String byteToHex(byte b) {
        String hexString = Integer.toHexString(b & UByte.MAX_VALUE);
        return hexString.length() < 2 ? "0" + hexString : hexString;
    }

    public static byte hexToByte(String str) {
        return (byte) Integer.parseInt(str, 16);
    }

    public static byte[] hexToByteArray(String str) {
        byte[] bArr;
        int length = str.length();
        if (length % 2 == 1) {
            length++;
            bArr = new byte[length / 2];
            str = "0" + str;
        } else {
            bArr = new byte[length / 2];
        }
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = i + 2;
            bArr[i2] = hexToByte(str.substring(i, i3));
            i2++;
            i = i3;
        }
        return bArr;
    }

    private static int string2Asc(char c) {
        byte b = (byte) c;
        System.out.println((int) b);
        return b;
    }

    public static String asc2String(int i) {
        if (i == 0) {
            return "";
        }
        char c = (char) i;
        System.out.println(c);
        return String.valueOf(c);
    }

    public static List<List<String>> subListBySegment(List<String> list, int i) {
        List<String> listSubList;
        ArrayList arrayList = new ArrayList();
        int size = list.size();
        if (size > 0 && i > 0) {
            int i2 = size / i;
            for (int i3 = 0; i3 < i; i3++) {
                if (i3 == i - 1) {
                    listSubList = list.subList(i2 * i3, size);
                } else {
                    listSubList = list.subList(i2 * i3, (i3 + 1) * i2);
                }
                arrayList.add(listSubList);
            }
        } else {
            arrayList.add(list);
        }
        return arrayList;
    }

    public static List<List<String>> subListByCount(List<String> list, int i) {
        List<String> listSubList;
        ArrayList arrayList = new ArrayList();
        int size = list.size();
        if (size > 0 && i > 0) {
            int i2 = size / i;
            if (size % i != 0) {
                i2++;
            }
            for (int i3 = 0; i3 < i2; i3++) {
                if (i3 == i2 - 1) {
                    listSubList = list.subList(i * i3, size);
                } else {
                    listSubList = list.subList(i * i3, (i3 + 1) * i);
                }
                arrayList.add(listSubList);
            }
        } else {
            arrayList.add(list);
        }
        return arrayList;
    }

    public static byte[] byteMerger(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    public static byte[] getData(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, i2, bArr2, 0, i);
        return bArr2;
    }

    public static String getStrFromBytes(byte[] bArr, int i, int i2) {
        byte[] data = getData(bArr, i, i2);
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(asc2String(b & UByte.MAX_VALUE));
        }
        LogUtil.m338i("字符串:" + sb.toString());
        return sb.toString();
    }

    public static byte[] getBytesFromStr(String str, int i) {
        byte[] bArr = new byte[i];
        char[] charArray = str.toCharArray();
        for (int i2 = 0; i2 < charArray.length; i2++) {
            bArr[i2] = (byte) charArray[i2];
        }
        return bArr;
    }

    public static String getIntFromBytes(byte[] bArr, int i, int i2) {
        return String.valueOf(byteArrayToInt(getData(bArr, i, i2)));
    }
}
