package com.github.houbb.heaven.util.lang.reflect;

import androidx.exifinterface.media.ExifInterface;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public final class PrimitiveUtil {
    private static final Map<Class, Object> PRIMITIVE_DEFAULT_MAP;
    private static final Map<Class, Class> PRIMITIVE_REFERENCE_MAP;
    private static final Map<Class<?>, Class<?>> TYPE_MAP;
    private static final Map primitiveTypeMap;

    private PrimitiveUtil() {
    }

    static {
        IdentityHashMap identityHashMap = new IdentityHashMap(8);
        TYPE_MAP = identityHashMap;
        HashMap map = new HashMap();
        PRIMITIVE_REFERENCE_MAP = map;
        HashMap map2 = new HashMap();
        PRIMITIVE_DEFAULT_MAP = map2;
        primitiveTypeMap = new HashMap<Class, String>(8) { // from class: com.github.houbb.heaven.util.lang.reflect.PrimitiveUtil.1
            {
                put(Boolean.TYPE, "Z");
                put(Character.TYPE, "C");
                put(Byte.TYPE, "B");
                put(Short.TYPE, ExifInterface.LATITUDE_SOUTH);
                put(Integer.TYPE, "I");
                put(Long.TYPE, "J");
                put(Float.TYPE, "F");
                put(Double.TYPE, "D");
            }
        };
        map.put(Integer.TYPE, Integer.class);
        map.put(Boolean.TYPE, Boolean.class);
        map.put(Byte.TYPE, Byte.class);
        map.put(Character.TYPE, Character.class);
        map.put(Short.TYPE, Short.class);
        map.put(Long.TYPE, Long.class);
        map.put(Float.TYPE, Float.class);
        map.put(Double.TYPE, Double.class);
        map.put(Void.TYPE, Void.class);
        identityHashMap.put(Boolean.class, Boolean.TYPE);
        identityHashMap.put(Byte.class, Byte.TYPE);
        identityHashMap.put(Character.class, Character.TYPE);
        identityHashMap.put(Double.class, Double.TYPE);
        identityHashMap.put(Float.class, Float.TYPE);
        identityHashMap.put(Integer.class, Integer.TYPE);
        identityHashMap.put(Long.class, Long.TYPE);
        identityHashMap.put(Short.class, Short.TYPE);
        map2.put(Integer.TYPE, 0);
        map2.put(Boolean.TYPE, false);
        map2.put(Byte.TYPE, (byte) 0);
        map2.put(Character.TYPE, (char) 0);
        map2.put(Short.TYPE, (short) 0);
        map2.put(Long.TYPE, 0L);
        map2.put(Float.TYPE, Float.valueOf(0.0f));
        map2.put(Double.TYPE, Double.valueOf(0.0d));
    }

    public static Class<?> getPrimitiveType(Class<?> cls) {
        return TYPE_MAP.get(cls);
    }

    public static Class getReferenceType(Class cls) {
        return cls.isPrimitive() ? PRIMITIVE_REFERENCE_MAP.get(cls) : cls;
    }

    public static Object getDefaultValue(Class cls) {
        return PRIMITIVE_DEFAULT_MAP.get(cls);
    }

    public static Type checkPrimitiveArray(GenericArrayType genericArrayType) {
        Type genericComponentType = genericArrayType.getGenericComponentType();
        String str = "[";
        while (genericComponentType instanceof GenericArrayType) {
            genericComponentType = ((GenericArrayType) genericComponentType).getGenericComponentType();
            str = str + str;
        }
        if (!(genericComponentType instanceof Class)) {
            return genericArrayType;
        }
        Class cls = (Class) genericComponentType;
        if (!cls.isPrimitive()) {
            return genericArrayType;
        }
        try {
            String str2 = (String) primitiveTypeMap.get(cls);
            return str2 != null ? Class.forName(str + str2) : genericArrayType;
        } catch (ClassNotFoundException unused) {
            return genericArrayType;
        }
    }
}
