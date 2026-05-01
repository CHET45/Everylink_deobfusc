package com.github.houbb.heaven.util.util;

import com.github.houbb.heaven.support.handler.IHandler;
import com.github.houbb.heaven.util.guava.Guavas;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public final class ArrayPrimitiveUtil {
    public static final int[] INT_EMPTY = new int[0];
    public static final short[] SHORT_EMPTY = new short[0];
    public static final long[] LONG_EMPTY = new long[0];
    public static final float[] FLOAT_EMPTY = new float[0];
    public static final double[] DOUBLE_EMPTY = new double[0];
    public static final char[] CHAR_EMPTY = new char[0];
    public static final byte[] BYTE_EMPTY = new byte[0];
    public static final boolean[] BOOLEAN_EMPTY = new boolean[0];

    private static char getPreChar(char c, char c2) {
        if ('\\' == c && '\\' == c2) {
            return ' ';
        }
        return c2;
    }

    public static byte[] newArray(byte... bArr) {
        return bArr;
    }

    public static char[] newArray(char... cArr) {
        return cArr;
    }

    public static double[] newArray(double... dArr) {
        return dArr;
    }

    public static float[] newArray(float... fArr) {
        return fArr;
    }

    public static int[] newArray(int... iArr) {
        return iArr;
    }

    public static long[] newArray(long... jArr) {
        return jArr;
    }

    public static short[] newArray(short... sArr) {
        return sArr;
    }

    public static boolean[] newArray(boolean... zArr) {
        return zArr;
    }

    private ArrayPrimitiveUtil() {
    }

    public static boolean isEmpty(int[] iArr) {
        return iArr == null || iArr.length <= 0;
    }

    public static boolean isNotEmpty(int[] iArr) {
        return !isEmpty(iArr);
    }

    public static boolean isEmpty(boolean[] zArr) {
        return zArr == null || zArr.length <= 0;
    }

    public static boolean isNotEmpty(boolean[] zArr) {
        return !isEmpty(zArr);
    }

    public static boolean isEmpty(char[] cArr) {
        return cArr == null || cArr.length <= 0;
    }

    public static boolean isNotEmpty(char[] cArr) {
        return !isEmpty(cArr);
    }

    public static boolean isEmpty(byte[] bArr) {
        return bArr == null || bArr.length <= 0;
    }

    public static boolean isNotEmpty(byte[] bArr) {
        return !isEmpty(bArr);
    }

    public static boolean isEmpty(long[] jArr) {
        return jArr == null || jArr.length <= 0;
    }

    public static boolean isNotEmpty(long[] jArr) {
        return !isEmpty(jArr);
    }

    public static boolean isEmpty(float[] fArr) {
        return fArr == null || fArr.length <= 0;
    }

    public static boolean isNotEmpty(float[] fArr) {
        return !isEmpty(fArr);
    }

    public static boolean isEmpty(double[] dArr) {
        return dArr == null || dArr.length <= 0;
    }

    public static boolean isNotEmpty(double[] dArr) {
        return !isEmpty(dArr);
    }

    public static boolean isEmpty(short[] sArr) {
        return sArr == null || sArr.length <= 0;
    }

    public static boolean isNotEmpty(short[] sArr) {
        return !isEmpty(sArr);
    }

    public static <K> List<K> toList(boolean[] zArr, IHandler<? super Boolean, K> iHandler) {
        if (isEmpty(zArr)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(zArr.length);
        for (boolean z : zArr) {
            arrayList.add(iHandler.handle(Boolean.valueOf(z)));
        }
        return arrayList;
    }

    public static <K> List<K> toList(char[] cArr, IHandler<? super Character, K> iHandler) {
        if (isEmpty(cArr)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(cArr.length);
        for (char c : cArr) {
            arrayList.add(iHandler.handle(Character.valueOf(c)));
        }
        return arrayList;
    }

    public static <K> List<K> toList(byte[] bArr, IHandler<? super Byte, K> iHandler) {
        if (isEmpty(bArr)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(bArr.length);
        for (byte b : bArr) {
            arrayList.add(iHandler.handle(Byte.valueOf(b)));
        }
        return arrayList;
    }

    public static <K> List<K> toList(short[] sArr, IHandler<? super Short, K> iHandler) {
        if (isEmpty(sArr)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(sArr.length);
        for (short s : sArr) {
            arrayList.add(iHandler.handle(Short.valueOf(s)));
        }
        return arrayList;
    }

    public static <K> List<K> toList(int[] iArr, IHandler<? super Integer, K> iHandler) {
        if (isEmpty(iArr)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int i : iArr) {
            arrayList.add(iHandler.handle(Integer.valueOf(i)));
        }
        return arrayList;
    }

    public static <K> List<K> toList(float[] fArr, IHandler<? super Float, K> iHandler) {
        if (isEmpty(fArr)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(fArr.length);
        for (float f : fArr) {
            arrayList.add(iHandler.handle(Float.valueOf(f)));
        }
        return arrayList;
    }

    public static <K> List<K> toList(double[] dArr, IHandler<? super Double, K> iHandler) {
        if (isEmpty(dArr)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(dArr.length);
        for (double d : dArr) {
            arrayList.add(iHandler.handle(Double.valueOf(d)));
        }
        return arrayList;
    }

    public static <K> List<K> toList(long[] jArr, IHandler<? super Long, K> iHandler) {
        if (isEmpty(jArr)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(jArr.length);
        for (long j : jArr) {
            arrayList.add(iHandler.handle(Long.valueOf(j)));
        }
        return arrayList;
    }

    public static int indexOf(char[] cArr, char c) {
        if (isEmpty(cArr)) {
            return -1;
        }
        for (int i = 0; i < cArr.length; i++) {
            if (cArr[i] == c) {
                return i;
            }
        }
        return -1;
    }

    public static boolean contains(boolean[] zArr, boolean z) {
        if (isEmpty(zArr)) {
            return false;
        }
        for (boolean z2 : zArr) {
            if (z2 == z) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(byte[] bArr, byte b) {
        if (isEmpty(bArr)) {
            return false;
        }
        for (byte b2 : bArr) {
            if (b2 == b) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(short[] sArr, short s) {
        if (isEmpty(sArr)) {
            return false;
        }
        for (short s2 : sArr) {
            if (s2 == s) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(int[] iArr, int i) {
        if (isEmpty(iArr)) {
            return false;
        }
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(long[] jArr, long j) {
        if (isEmpty(jArr)) {
            return false;
        }
        for (long j2 : jArr) {
            if (j2 == j) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(float[] fArr, float f) {
        if (isEmpty(fArr)) {
            return false;
        }
        for (float f2 : fArr) {
            if (f2 == f) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(double[] dArr, double d) {
        if (isEmpty(dArr)) {
            return false;
        }
        for (double d2 : dArr) {
            if (d2 == d) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(char[] cArr, char c) {
        if (isEmpty(cArr)) {
            return false;
        }
        for (char c2 : cArr) {
            if (c2 == c) {
                return true;
            }
        }
        return false;
    }

    public static int lastIndexOf(char[] cArr, char c) {
        int i = -1;
        if (isEmpty(cArr)) {
            return -1;
        }
        for (int i2 = 0; i2 < cArr.length; i2++) {
            if (cArr[i2] == c) {
                i = i2;
            }
        }
        return i;
    }

    public static List<Integer> allIndexOf(char[] cArr, char c) {
        if (isEmpty(cArr)) {
            return Collections.emptyList();
        }
        List<Integer> listNewArrayList = Guavas.newArrayList();
        for (int i = 0; i < cArr.length; i++) {
            if (cArr[i] == c) {
                listNewArrayList.add(Integer.valueOf(i));
            }
        }
        return listNewArrayList;
    }

    public static String getStringBeforeSymbol(char[] cArr, int i, char c) {
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        char preChar = ' ';
        while (i < cArr.length) {
            char c2 = cArr[i];
            preChar = getPreChar(preChar, c2);
            if ('\\' != preChar && '\"' == c2) {
                z = !z;
            }
            if (!z && c == c2) {
                return sb.toString();
            }
            sb.append(c2);
            i++;
        }
        return sb.toString();
    }

    public static <E> int[] toIntArray(List<E> list, IHandler<E, Integer> iHandler) {
        if (CollectionUtil.isEmpty(list)) {
            return INT_EMPTY;
        }
        int size = list.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = iHandler.handle(list.get(i)).intValue();
        }
        return iArr;
    }

    public static <E> boolean[] toBooleanArray(List<E> list, IHandler<E, Boolean> iHandler) {
        if (CollectionUtil.isEmpty(list)) {
            return BOOLEAN_EMPTY;
        }
        int size = list.size();
        boolean[] zArr = new boolean[size];
        for (int i = 0; i < size; i++) {
            zArr[i] = iHandler.handle(list.get(i)).booleanValue();
        }
        return zArr;
    }

    public static <E> char[] toCharArray(List<E> list, IHandler<E, Character> iHandler) {
        if (CollectionUtil.isEmpty(list)) {
            return CHAR_EMPTY;
        }
        int size = list.size();
        char[] cArr = new char[size];
        for (int i = 0; i < size; i++) {
            cArr[i] = iHandler.handle(list.get(i)).charValue();
        }
        return cArr;
    }

    public static <E> byte[] toByteArray(List<E> list, IHandler<E, Byte> iHandler) {
        if (CollectionUtil.isEmpty(list)) {
            return BYTE_EMPTY;
        }
        int size = list.size();
        byte[] bArr = new byte[size];
        for (int i = 0; i < size; i++) {
            bArr[i] = iHandler.handle(list.get(i)).byteValue();
        }
        return bArr;
    }

    public static <E> short[] toShortArray(List<E> list, IHandler<E, Short> iHandler) {
        if (CollectionUtil.isEmpty(list)) {
            return SHORT_EMPTY;
        }
        int size = list.size();
        short[] sArr = new short[size];
        for (int i = 0; i < size; i++) {
            sArr[i] = iHandler.handle(list.get(i)).shortValue();
        }
        return sArr;
    }

    public static <E> long[] toLongArray(List<E> list, IHandler<E, Long> iHandler) {
        if (CollectionUtil.isEmpty(list)) {
            return LONG_EMPTY;
        }
        int size = list.size();
        long[] jArr = new long[size];
        for (int i = 0; i < size; i++) {
            jArr[i] = iHandler.handle(list.get(i)).longValue();
        }
        return jArr;
    }

    public static <E> float[] toFloatArray(List<E> list, IHandler<E, Float> iHandler) {
        if (CollectionUtil.isEmpty(list)) {
            return FLOAT_EMPTY;
        }
        int size = list.size();
        float[] fArr = new float[size];
        for (int i = 0; i < size; i++) {
            fArr[i] = iHandler.handle(list.get(i)).floatValue();
        }
        return fArr;
    }

    public static <E> double[] toDoubleArray(List<E> list, IHandler<E, Double> iHandler) {
        if (CollectionUtil.isEmpty(list)) {
            return DOUBLE_EMPTY;
        }
        int size = list.size();
        double[] dArr = new double[size];
        for (int i = 0; i < size; i++) {
            dArr[i] = iHandler.handle(list.get(i)).doubleValue();
        }
        return dArr;
    }
}
