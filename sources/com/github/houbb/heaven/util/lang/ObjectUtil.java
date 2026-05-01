package com.github.houbb.heaven.util.lang;

import com.github.houbb.heaven.support.handler.IHandler;
import com.github.houbb.heaven.util.lang.reflect.ClassTypeUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectFieldUtil;
import com.github.houbb.heaven.util.util.ArrayPrimitiveUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.MapUtil;
import com.github.houbb.heaven.util.util.regex.RegexUtil;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public final class ObjectUtil {
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    private ObjectUtil() {
    }

    public static boolean isSameType(Object obj, Object obj2) {
        if (isNull(obj) || isNull(obj2)) {
            return false;
        }
        return obj.getClass().isInstance(obj2);
    }

    public static boolean isNotSameType(Object obj, Object obj2) {
        return !isSameType(obj, obj2);
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public static boolean isEmpty(Object obj) {
        if (isNull(obj)) {
            return true;
        }
        if (obj instanceof String) {
            return StringUtil.isEmpty((String) obj);
        }
        if (obj instanceof Collection) {
            return CollectionUtil.isEmpty((Collection) obj);
        }
        if (obj instanceof Map) {
            return MapUtil.isEmpty((Map) obj);
        }
        return obj.getClass().isArray() && Array.getLength(obj) == 0;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static boolean isEquals(Object obj, Object obj2) {
        if (isNotSameType(obj, obj2)) {
            return false;
        }
        Class<?> cls = obj.getClass();
        Class<?> cls2 = obj.getClass();
        if (cls.isPrimitive() && cls2.isPrimitive() && obj != obj2) {
            return false;
        }
        if (ClassTypeUtil.isArray(cls) && ClassTypeUtil.isArray(cls2)) {
            return Arrays.equals((Object[]) obj, (Object[]) obj2);
        }
        if (ClassTypeUtil.isMap(cls) && ClassTypeUtil.isMap(cls2)) {
            return ((Map) obj).equals((Map) obj2);
        }
        return obj.equals(obj2);
    }

    public static boolean isNotEquals(Object obj, Object obj2) {
        return !isEquals(obj, obj2);
    }

    public static String objectToString(Object obj) {
        return objectToString(obj, null);
    }

    public static String objectToString(Object obj, String str) {
        return isNull(obj) ? str : obj.toString();
    }

    public static boolean isNull(Object obj, Object... objArr) {
        if (!isNull(obj)) {
            return false;
        }
        if (ArrayUtil.isNotEmpty(objArr)) {
            for (Object obj2 : objArr) {
                if (isNotNull(obj2)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isEqualsOrNull(Object obj, Object obj2) {
        if (isNull(obj, obj2)) {
            return true;
        }
        if (isNull(obj) || isNull(obj2)) {
            return false;
        }
        return isEquals(obj, obj2);
    }

    public static <R> List<R> toList(Object obj, IHandler<Object, R> iHandler) {
        if (isNull(obj)) {
            return Collections.emptyList();
        }
        Class<?> cls = obj.getClass();
        if (ClassTypeUtil.isCollection(cls)) {
            return CollectionUtil.toList((Collection) obj, iHandler);
        }
        if (cls.isArray()) {
            return ArrayUtil.toList(obj, iHandler);
        }
        throw new UnsupportedOperationException("Not support foreach() for class: " + cls.getName());
    }

    public static Class getClass(Object obj) {
        if (isNull(obj)) {
            return null;
        }
        return obj.getClass();
    }

    public static void emptyToNull(Object obj) {
        if (obj == null) {
            return;
        }
        for (Field field : ClassUtil.getAllFieldList(obj.getClass())) {
            if (isEmpty(ReflectFieldUtil.getValue(field, obj))) {
                ReflectFieldUtil.setValue(field, obj, (Object) null);
            }
        }
    }

    public static void copyProperties(Object obj, Object obj2) {
        if (obj == null || obj2 == null) {
            return;
        }
        Map<String, Field> allFieldMap = ClassUtil.getAllFieldMap(obj.getClass());
        Map<String, Field> allFieldMap2 = ClassUtil.getAllFieldMap(obj2.getClass());
        for (Map.Entry<String, Field> entry : allFieldMap.entrySet()) {
            String key = entry.getKey();
            Field value = entry.getValue();
            Field field = allFieldMap2.get(key);
            if (field != null && ClassUtil.isAssignable(value.getType(), field.getType())) {
                ReflectFieldUtil.setValue(field, obj2, ReflectFieldUtil.getValue(value, obj));
            }
        }
    }

    public static boolean isSameValue(Object obj, Object obj2) {
        if (obj == null && obj2 == null) {
            return true;
        }
        if (obj == null || obj2 == null) {
            return false;
        }
        return obj.equals(obj2);
    }

    public static int getMaxFieldSize(Object obj) {
        if (obj == null) {
            return 0;
        }
        Class<?> cls = obj.getClass();
        int iMax = 1;
        if (!ClassTypeUtil.isJavaBean(cls)) {
            return 1;
        }
        Iterator<Field> it = ClassUtil.getAllFieldList(cls).iterator();
        while (it.hasNext()) {
            try {
                iMax = Math.max(iMax, getObjectCollectionSize(it.next().get(obj)));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return iMax;
    }

    public static int getObjectCollectionSize(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).size();
        }
        if (obj instanceof Object[]) {
            return ((Object[]) obj).length;
        }
        if (obj instanceof boolean[]) {
            return ((boolean[]) obj).length;
        }
        if (obj instanceof byte[]) {
            return ((byte[]) obj).length;
        }
        if (obj instanceof short[]) {
            return ((short[]) obj).length;
        }
        if (obj instanceof int[]) {
            return ((int[]) obj).length;
        }
        if (obj instanceof long[]) {
            return ((long[]) obj).length;
        }
        if (obj instanceof float[]) {
            return ((float[]) obj).length;
        }
        if (obj instanceof double[]) {
            return ((double[]) obj).length;
        }
        if (obj instanceof char[]) {
            return ((char[]) obj).length;
        }
        return 1;
    }

    public static Object getValueByPath(Object obj, String str) {
        if (obj == null || StringUtil.isEmpty(str)) {
            return null;
        }
        for (String str2 : str.split("\\.")) {
            if (obj == null) {
                return null;
            }
            obj = ReflectFieldUtil.getValue(str2, obj);
        }
        return obj;
    }

    public static Object getValueByPath(Map<String, ?> map, String str) {
        if (MapUtil.isEmpty(map) || StringUtil.isEmpty(str)) {
            return null;
        }
        String[] strArrSplit = str.split("\\.");
        int length = strArrSplit.length;
        int i = 0;
        Object obj = map;
        while (i < length) {
            String str2 = strArrSplit[i];
            if (obj == null || !(obj instanceof Map)) {
                return null;
            }
            i++;
            obj = ((Map) obj).get(str2);
        }
        return obj;
    }

    /* JADX INFO: renamed from: gt */
    public static boolean m534gt(Object obj, Object obj2) {
        return (obj instanceof Comparable) && (obj2 instanceof Comparable) && ((Comparable) obj).compareTo((Comparable) obj2) > 0;
    }

    public static boolean gte(Object obj, Object obj2) {
        return (obj instanceof Comparable) && (obj2 instanceof Comparable) && ((Comparable) obj).compareTo((Comparable) obj2) >= 0;
    }

    /* JADX INFO: renamed from: lt */
    public static boolean m535lt(Object obj, Object obj2) {
        return (obj instanceof Comparable) && (obj2 instanceof Comparable) && ((Comparable) obj).compareTo((Comparable) obj2) < 0;
    }

    public static boolean lte(Object obj, Object obj2) {
        return (obj instanceof Comparable) && (obj2 instanceof Comparable) && ((Comparable) obj).compareTo((Comparable) obj2) <= 0;
    }

    public static boolean contains(Object obj, Object obj2) {
        if (obj == null) {
            return false;
        }
        if ((obj instanceof String) && (obj2 instanceof String)) {
            String str = (String) obj;
            return str.contains(str);
        }
        if (obj instanceof Collection) {
            return CollectionUtil.contains((Collection) obj, obj2);
        }
        if (obj instanceof Object[]) {
            return ArrayUtil.contains((Object[]) obj, obj2);
        }
        if ((obj instanceof boolean[]) && (obj2 instanceof Boolean)) {
            return ArrayPrimitiveUtil.contains((boolean[]) obj, ((Boolean) obj2).booleanValue());
        }
        if ((obj instanceof byte[]) && (obj2 instanceof Byte)) {
            return ArrayPrimitiveUtil.contains((byte[]) obj, ((Byte) obj2).byteValue());
        }
        if ((obj instanceof short[]) && (obj2 instanceof Short)) {
            return ArrayPrimitiveUtil.contains((short[]) obj, ((Short) obj2).shortValue());
        }
        if ((obj instanceof int[]) && (obj2 instanceof Integer)) {
            return ArrayPrimitiveUtil.contains((int[]) obj, ((Integer) obj2).intValue());
        }
        if ((obj instanceof long[]) && (obj2 instanceof Long)) {
            return ArrayPrimitiveUtil.contains((long[]) obj, ((Long) obj2).longValue());
        }
        if ((obj instanceof float[]) && (obj2 instanceof Float)) {
            return ArrayPrimitiveUtil.contains((float[]) obj, ((Float) obj2).floatValue());
        }
        if ((obj instanceof double[]) && (obj2 instanceof Double)) {
            return ArrayPrimitiveUtil.contains((double[]) obj, ((Double) obj2).doubleValue());
        }
        if ((obj instanceof char[]) && (obj2 instanceof Character)) {
            return ArrayPrimitiveUtil.contains((char[]) obj, ((Character) obj2).charValue());
        }
        return false;
    }

    public static boolean startWith(Object obj, Object obj2) {
        if (obj == null || obj2 == null) {
            return false;
        }
        if ((obj instanceof String) && (obj2 instanceof String)) {
            return ((String) obj).startsWith((String) obj2);
        }
        return obj.toString().startsWith(obj2.toString());
    }

    public static boolean endWith(Object obj, Object obj2) {
        if (obj == null || obj2 == null) {
            return false;
        }
        if ((obj instanceof String) && (obj2 instanceof String)) {
            return ((String) obj).endsWith((String) obj2);
        }
        return obj.toString().endsWith(obj2.toString());
    }

    public static boolean matchRegex(Object obj, String str) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            return RegexUtil.match(str, (String) obj);
        }
        return RegexUtil.match(obj.toString(), str);
    }
}
