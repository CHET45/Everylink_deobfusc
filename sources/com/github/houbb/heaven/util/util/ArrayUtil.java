package com.github.houbb.heaven.util.util;

import com.github.houbb.heaven.support.handler.IHandler;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassGenericUtil;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public final class ArrayUtil {
    public static final String[] STRING_EMPTY = new String[0];

    public static Object[] newArray(Object... objArr) {
        return objArr;
    }

    private ArrayUtil() {
    }

    public static boolean isEmpty(Object[] objArr) {
        return objArr == null || objArr.length <= 0;
    }

    public static boolean isNotEmpty(Object[] objArr) {
        return !isEmpty(objArr);
    }

    public static <T> List<T> toList(T[] tArr) {
        if (isEmpty(tArr)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(tArr.length);
        arrayList.addAll(Guavas.newArrayList(tArr));
        return arrayList;
    }

    public static Object[] toArray(List<?> list) {
        if (CollectionUtil.isEmpty(list)) {
            return new Object[0];
        }
        int size = list.size();
        Object[] objArr = new Object[size];
        for (int i = 0; i < size; i++) {
            objArr[i] = list.get(i);
        }
        return objArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <K, V> K[] toArray(V[] vArr, IHandler<? super V, K> iHandler) {
        if (isEmpty(vArr)) {
            return (K[]) new Object[0];
        }
        K[] kArr = (K[]) new Object[vArr.length];
        for (int i = 0; i < vArr.length; i++) {
            kArr[i] = iHandler.handle(vArr[i]);
        }
        return kArr;
    }

    public static <K> K[] union(K[] kArr, K... kArr2) {
        if (isEmpty(kArr)) {
            return kArr2;
        }
        if (isEmpty(kArr2)) {
            return kArr;
        }
        K[] kArr3 = (K[]) new Object[kArr2.length];
        System.arraycopy(kArr2, 0, kArr3, kArr.length, kArr2.length);
        return kArr3;
    }

    public static boolean contains(Object[] objArr, Object obj) {
        return indexOf(objArr, obj) != -1;
    }

    public static boolean notContains(Object[] objArr, Object obj) {
        return !contains(objArr, obj);
    }

    public static int indexOf(Object[] objArr, Object obj) {
        return indexOf(objArr, obj, 0);
    }

    public static int indexOf(Object[] objArr, Object obj, int i) {
        if (objArr == null) {
            return -1;
        }
        if (i < 0) {
            i = 0;
        }
        if (obj == null) {
            while (i < objArr.length) {
                if (objArr[i] == null) {
                    return i;
                }
                i++;
            }
        } else if (objArr.getClass().getComponentType().isInstance(obj)) {
            while (i < objArr.length) {
                if (obj.equals(objArr[i])) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    public static <R> R[] listToArray(List<R> list) {
        R[] rArr = (R[]) ((Object[]) Array.newInstance((Class<?>) ClassGenericUtil.getGenericClass(list), list.size()));
        for (int i = 0; i < list.size(); i++) {
            Array.set(rArr, i, list.get(i));
        }
        return rArr;
    }

    public static <E> List<E> arrayToList(E... eArr) {
        if (isEmpty(eArr)) {
            return Guavas.newArrayList();
        }
        return Guavas.newArrayList(eArr);
    }

    public static int getStartIndex(int i, Object[] objArr) {
        if (!isEmpty(objArr) && i >= 0 && i <= objArr.length - 1) {
            return i;
        }
        return 0;
    }

    public static int getEndIndex(int i, Object[] objArr) {
        if (isEmpty(objArr)) {
            return 0;
        }
        int length = objArr.length - 1;
        return (i < 0 || i > length) ? length : i;
    }

    public static Optional<Object> firstNotNullElem(Object[] objArr) {
        if (isEmpty(objArr)) {
            return Optional.empty();
        }
        for (Object obj : objArr) {
            if (ObjectUtil.isNotNull(obj)) {
                return Optional.m536of(obj);
            }
        }
        return Optional.empty();
    }

    public static <K, V> List<K> toList(V[] vArr, IHandler<? super V, K> iHandler) {
        if (ObjectUtil.isNull(vArr)) {
            return Collections.emptyList();
        }
        List<K> listNewArrayList = Guavas.newArrayList(vArr.length);
        for (V v : vArr) {
            listNewArrayList.add(iHandler.handle(v));
        }
        return listNewArrayList;
    }

    public static List toList(Object obj, IHandler iHandler) {
        if (ObjectUtil.isNull(obj)) {
            return Collections.emptyList();
        }
        Class<?> cls = obj.getClass();
        if (boolean[].class == cls) {
            return ArrayPrimitiveUtil.toList((boolean[]) obj, iHandler);
        }
        if (short[].class == cls) {
            return ArrayPrimitiveUtil.toList((short[]) obj, iHandler);
        }
        if (byte[].class == cls) {
            return ArrayPrimitiveUtil.toList((byte[]) obj, iHandler);
        }
        if (int[].class == cls) {
            return ArrayPrimitiveUtil.toList((int[]) obj, iHandler);
        }
        if (float[].class == cls) {
            return ArrayPrimitiveUtil.toList((float[]) obj, iHandler);
        }
        if (double[].class == cls) {
            return ArrayPrimitiveUtil.toList((double[]) obj, iHandler);
        }
        if (char[].class == cls) {
            return ArrayPrimitiveUtil.toList((char[]) obj, iHandler);
        }
        if (long[].class == cls) {
            return ArrayPrimitiveUtil.toList((long[]) obj, iHandler);
        }
        return toList((Object[]) obj, iHandler);
    }

    public static Object[] shift(Object[] objArr, int i) {
        if (isEmpty(objArr)) {
            return objArr;
        }
        int length = objArr.length;
        if (i < 0) {
            i += length;
        }
        Object[] objArr2 = new Object[length];
        for (int i2 = 0; i2 < length; i2++) {
            objArr2[i2] = objArr[(i2 + i) % length];
        }
        return objArr2;
    }

    public static int lastIndexOf(char[] cArr, char c, int i, int i2) {
        if (cArr != null && cArr.length != 0) {
            while (i2 >= i) {
                if (cArr[i2] == c) {
                    return i2;
                }
                i2--;
            }
        }
        return -1;
    }

    public static int lastIndexOf(char[] cArr, char c) {
        if (ArrayPrimitiveUtil.isEmpty(cArr)) {
            return -1;
        }
        return lastIndexOf(cArr, c, 0, cArr.length - 1);
    }
}
