package com.github.houbb.heaven.util.lang.reflect;

import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public final class ReflectFieldUtil {
    private ReflectFieldUtil() {
    }

    public static boolean isAssignable(Field field, Field field2) {
        if (ObjectUtil.isNull(field) || ObjectUtil.isNull(field2) || Modifier.isFinal(field2.getModifiers())) {
            return false;
        }
        return ClassUtil.isAssignable(field.getType(), field2.getType());
    }

    public static Boolean isString(Field field) {
        return Boolean.valueOf(field.getType() == String.class);
    }

    public static Boolean isNotString(Field field) {
        return Boolean.valueOf(!isString(field).booleanValue());
    }

    public static boolean isAnnotationPresent(Field field, Class<? extends Annotation> cls) {
        return field.isAnnotationPresent(cls);
    }

    public static boolean isNotAnnotationPresent(Field field, Class<? extends Annotation> cls) {
        return !isAnnotationPresent(field, cls);
    }

    public static Class getGenericParamType(Field field, int i) {
        if (ObjectUtil.isNull(field)) {
            return null;
        }
        field.setAccessible(true);
        return TypeUtil.getGenericParamType(field.getGenericType(), i);
    }

    public static boolean containsAnnotationField(Class cls, Class<? extends Annotation> cls2) {
        ArgUtil.notNull(cls, "Clazz");
        ArgUtil.notNull(cls2, "Annotation class");
        List<Field> allFieldList = ClassUtil.getAllFieldList(cls);
        if (CollectionUtil.isEmpty(allFieldList)) {
            return false;
        }
        Iterator<Field> it = allFieldList.iterator();
        while (it.hasNext()) {
            if (it.next().isAnnotationPresent(cls2)) {
                return true;
            }
        }
        return false;
    }

    public static Class getComponentType(Field field, int i) {
        Class<?> type = field.getType();
        if (ClassTypeUtil.isArray(type)) {
            return type.getComponentType();
        }
        if (ClassTypeUtil.isCollection(type)) {
            return getGenericParamType(field, 0);
        }
        return ClassTypeUtil.isMap(type) ? getGenericParamType(field, i) : type;
    }

    public static Class getComponentType(Field field) {
        return getComponentType(field, 0);
    }

    public static void setValue(Field field, Object obj, Object obj2) {
        try {
            field.setAccessible(true);
            field.set(obj, obj2);
        } catch (IllegalAccessException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static void setValue(Object obj, String str, Object obj2) {
        ArgUtil.notNull(obj, "instance");
        try {
            Field field = ClassUtil.getAllFieldMap(obj.getClass()).get(str);
            field.setAccessible(true);
            field.set(obj, obj2);
        } catch (IllegalAccessException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static Object getValue(Field field, Object obj) {
        try {
            field.setAccessible(true);
            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static Object getValue(String str, Object obj) {
        return getValue(getField(obj, str), obj);
    }

    public static Field getField(Object obj, String str) {
        ArgUtil.notNull(obj, "object");
        return getField((Class) obj.getClass(), str);
    }

    public static Field getField(Class cls, String str) {
        ArgUtil.notNull(cls, "clazz");
        ArgUtil.notEmpty(str, "fieldName");
        for (Field field : ClassUtil.getAllFieldList(cls)) {
            if (field.getName().equals(str)) {
                return field;
            }
        }
        throw new CommonRuntimeException("Field not found for fieldName: " + str);
    }
}
