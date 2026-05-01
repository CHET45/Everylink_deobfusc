package com.github.houbb.heaven.util.lang.reflect;

import com.github.houbb.heaven.constant.MethodConst;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public final class ClassTypeUtil {
    private static final Class[] BASE_TYPE_CLASS = {String.class, Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Void.class, Object.class, Class.class};

    private ClassTypeUtil() {
    }

    public static boolean isMap(Class<?> cls) {
        return Map.class.isAssignableFrom(cls);
    }

    public static boolean isArray(Class<?> cls) {
        return cls.isArray();
    }

    public static boolean isCollection(Class<?> cls) {
        return Collection.class.isAssignableFrom(cls);
    }

    public static boolean isIterable(Class<?> cls) {
        return Iterable.class.isAssignableFrom(cls);
    }

    public static boolean isBase(Class<?> cls) {
        if (cls.isPrimitive()) {
            return true;
        }
        for (Class cls2 : BASE_TYPE_CLASS) {
            if (cls2.equals(cls)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEnum(Class<?> cls) {
        ArgUtil.notNull(cls, "clazz");
        return cls.isEnum();
    }

    public static boolean isAbstract(Class<?> cls) {
        return Modifier.isAbstract(cls.getModifiers());
    }

    public static boolean isAbstractOrInterface(Class<?> cls) {
        return isAbstract(cls) || cls.isInterface();
    }

    public static boolean isJavaBean(Class<?> cls) {
        return (cls == null || cls.isInterface() || isAbstract(cls) || cls.isEnum() || cls.isArray() || cls.isAnnotation() || cls.isSynthetic() || cls.isPrimitive() || isIterable(cls) || isMap(cls)) ? false : true;
    }

    public static boolean isJdk(Class<?> cls) {
        return cls != null && cls.getClassLoader() == null;
    }

    public static boolean isBean(Class<?> cls) {
        if (isJavaBean(cls)) {
            for (Method method : cls.getMethods()) {
                if (method.getParameterTypes().length == 1 && method.getName().startsWith(MethodConst.SET_PREFIX)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Class getListType(Field field) {
        return (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
    }

    public static boolean isWildcardGenericType(Type type) {
        return "sun.reflect.generics.reflectiveObjects.WildcardTypeImpl".equals(type.getClass().getName());
    }

    public static boolean isList(Class cls) {
        return List.class.isAssignableFrom(cls);
    }

    public static boolean isSet(Class cls) {
        return Set.class.isAssignableFrom(cls);
    }

    public static boolean isPrimitive(Class cls) {
        return cls.isPrimitive();
    }

    public static boolean isPrimitive(Object obj) {
        if (ObjectUtil.isNull(obj)) {
            return false;
        }
        return isPrimitive((Class) obj.getClass());
    }
}
