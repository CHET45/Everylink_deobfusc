package com.tencent.cloud.stream.tts.core.utils;

import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/* JADX INFO: loaded from: classes4.dex */
public class ByteUtils {
    public static byte[] subBytes(byte[] bs, int startIndex, int length) {
        byte[] bArr = new byte[length];
        System.arraycopy(bs, startIndex, bArr, 0, length);
        return bArr;
    }

    public static byte[] copy(byte[] bs) {
        return Arrays.copyOf(bs, bs.length);
    }

    public static byte[] concat(byte[] bytes1, byte[] bytes2) {
        byte[] bArr = new byte[bytes1.length + bytes2.length];
        System.arraycopy(bytes1, 0, bArr, 0, bytes1.length);
        System.arraycopy(bytes2, 0, bArr, bytes1.length, bytes2.length);
        return bArr;
    }

    public static List<byte[]> subToSmallBytes(byte[] bs, int minLength, int maxLength) {
        int length = bs.length;
        if (maxLength > length) {
            maxLength = length;
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < length) {
            int randomValue = getRandomValue(minLength, maxLength);
            if (i + randomValue > length) {
                randomValue = length - i;
            }
            arrayList.add(subBytes(bs, i, randomValue));
            i += randomValue;
        }
        return arrayList;
    }

    public static List<byte[]> subToSmallBytes(File file, int subLen) throws FileNotFoundException {
        return subToSmallBytes(new FileInputStream(file), subLen);
    }

    public static List<byte[]> subToSmallBytes(InputStream inputStream, int subLen) {
        StringBuilder sb;
        ArrayList arrayList = new ArrayList();
        try {
            try {
                int iAvailable = inputStream.available();
                while (iAvailable > 0) {
                    byte[] bArr = new byte[subLen];
                    int i = inputStream.read(bArr);
                    if (i == subLen) {
                        arrayList.add(bArr);
                    } else if (i > 0) {
                        arrayList.add(subBytes(bArr, 0, i));
                    }
                    iAvailable = inputStream.available();
                }
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e = e;
                    sb = new StringBuilder("subToSmallBytes IOException:");
                    AAILogger.m1854e("AAILogger", sb.append(Log.getStackTraceString(e)).toString());
                }
            } catch (IOException e2) {
                AAILogger.m1854e("AAILogger", "subToSmallBytes IOException:" + Log.getStackTraceString(e2));
                try {
                    inputStream.close();
                } catch (Exception e3) {
                    e = e3;
                    sb = new StringBuilder("subToSmallBytes IOException:");
                    AAILogger.m1854e("AAILogger", sb.append(Log.getStackTraceString(e)).toString());
                }
            }
            return arrayList;
        } catch (Throwable th) {
            try {
                inputStream.close();
            } catch (Exception e4) {
                AAILogger.m1854e("AAILogger", "subToSmallBytes IOException:" + Log.getStackTraceString(e4));
            }
            throw th;
        }
    }

    private static int getRandomValue(int minLength, int maxLength) {
        return new Random().nextInt(maxLength - minLength) + minLength;
    }

    public static byte[] inputStream2ByteArray(String filePath) {
        return inputStream2ByteArray(new File(filePath));
    }

    public static byte[] inputStream2ByteArray(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] byteArray = toByteArray(fileInputStream);
            fileInputStream.close();
            return byteArray;
        } catch (IOException e) {
            AAILogger.m1854e("AAILogger", "inputStream2ByteArray IOException:" + Log.getStackTraceString(e));
            return null;
        }
    }

    private static byte[] toByteArray(InputStream in) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        while (true) {
            try {
                int i = in.read(bArr);
                if (i != -1) {
                    byteArrayOutputStream.write(bArr, 0, i);
                } else {
                    return byteArrayOutputStream.toByteArray();
                }
            } catch (IOException e) {
                AAILogger.m1854e("AAILogger", "toByteArray IOException:" + Log.getStackTraceString(e));
                return null;
            }
        }
    }
}
