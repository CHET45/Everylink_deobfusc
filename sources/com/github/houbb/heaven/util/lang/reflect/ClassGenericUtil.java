package com.github.houbb.heaven.util.lang.reflect;

import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public final class ClassGenericUtil {
    private ClassGenericUtil() {
    }

    private static List<Type> getGenericInterfaces(Class cls) {
        HashSet hashSet = new HashSet();
        Type[] genericInterfaces = cls.getGenericInterfaces();
        if (ArrayUtil.isNotEmpty(genericInterfaces)) {
            hashSet.addAll(Guavas.newArrayList(genericInterfaces));
        }
        Type genericSuperclass = cls.getGenericSuperclass();
        if (ObjectUtil.isNotNull(genericSuperclass) && genericSuperclass.getClass().isInterface()) {
            hashSet.add(genericSuperclass);
        }
        return Guavas.newArrayList(hashSet);
    }

    public static Class getGenericClass(Class cls, Class cls2, int i) {
        for (Type type : getGenericInterfaces(cls)) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                if (cls2.equals(parameterizedType.getRawType())) {
                    return (Class) parameterizedType.getActualTypeArguments()[i];
                }
            }
        }
        return Object.class;
    }

    public static Class getGenericClass(Collection<?> collection) {
        if (CollectionUtil.isEmpty(collection)) {
            return null;
        }
        for (Object obj : collection) {
            if (ObjectUtil.isNotNull(obj)) {
                return obj.getClass();
            }
        }
        return null;
    }

    public static Class getGenericSupperClass(Class cls, int i) {
        Type genericSuperclass = cls.getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            return Object.class;
        }
        return (Class) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[i];
    }

    public static Class getGenericSupperClass(Class cls) {
        return getGenericSupperClass(cls, 0);
    }

    public static <T> Type getGenericRuntimeType(Wrapper<T> wrapper) {
        Type genericSuperclass = wrapper.getClass().getGenericSuperclass();
        if (genericSuperclass != null && (genericSuperclass instanceof ParameterizedType)) {
            return ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        }
        return null;
    }
}
