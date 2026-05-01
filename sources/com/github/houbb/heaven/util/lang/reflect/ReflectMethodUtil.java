package com.github.houbb.heaven.util.lang.reflect;

import com.azure.core.implementation.logging.LoggingKeys;
import com.github.houbb.heaven.annotation.reflect.Param;
import com.github.houbb.heaven.constant.MethodConst;
import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import com.github.houbb.heaven.support.handler.IHandler;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.Optional;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public final class ReflectMethodUtil {
    private static final List<String> IGNORE_METHOD_LIST;

    private ReflectMethodUtil() {
    }

    static {
        HashSet hashSet = new HashSet(64);
        for (Method method : Object.class.getMethods()) {
            hashSet.add(method.getName());
        }
        for (Method method2 : Class.class.getMethods()) {
            hashSet.add(method2.getName());
        }
        IGNORE_METHOD_LIST = new ArrayList(hashSet);
    }

    public static List<String> getIgnoreMethodList() {
        return IGNORE_METHOD_LIST;
    }

    public static boolean isIgnoreMethod(String str) {
        return getIgnoreMethodList().contains(str);
    }

    public static List<String> getParamTypeNames(Method method) {
        ArgUtil.notNull(method, LoggingKeys.HTTP_METHOD_KEY);
        return ArrayUtil.toList((Object[]) method.getParameterTypes(), (IHandler) new IHandler<Class<?>, String>() { // from class: com.github.houbb.heaven.util.lang.reflect.ReflectMethodUtil.1
            @Override // com.github.houbb.heaven.support.handler.IHandler
            public String handle(Class<?> cls) {
                return cls.getName();
            }
        });
    }

    public static List<String> getParamNames(Method method) {
        ArgUtil.notNull(method, LoggingKeys.HTTP_METHOD_KEY);
        return getParamNames(method.getParameterAnnotations());
    }

    public static List<String> getParamNames(Annotation[][] annotationArr) {
        if (ArrayUtil.isEmpty(annotationArr)) {
            return Collections.emptyList();
        }
        int length = annotationArr.length;
        List<String> listNewArrayList = Guavas.newArrayList(length);
        for (int i = 0; i < length; i++) {
            listNewArrayList.add(getParamName(i, annotationArr[i]));
        }
        return listNewArrayList;
    }

    private static String getParamName(int i, Annotation[] annotationArr) {
        String str = "arg" + i;
        if (ArrayUtil.isEmpty(annotationArr)) {
            return str;
        }
        for (Annotation annotation : annotationArr) {
            if (annotation.annotationType().equals(Param.class)) {
                return ((Param) annotation).value();
            }
        }
        return str;
    }

    public static Class getReturnGenericType(Method method, int i) {
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType instanceof ParameterizedType) {
            return (Class) ((ParameterizedType) genericReturnType).getActualTypeArguments()[i];
        }
        return null;
    }

    public static Class getParamGenericType(Method method, int i, int i2) {
        Type type = method.getGenericParameterTypes()[i];
        if (type instanceof ParameterizedType) {
            return (Class) ((ParameterizedType) type).getActualTypeArguments()[i2];
        }
        return null;
    }

    public static Optional<Method> getMethodOptional(Class cls, Class<? extends Annotation> cls2) {
        Method[] methods = cls.getMethods();
        if (ArrayUtil.isEmpty(methods)) {
            return Optional.empty();
        }
        for (Method method : methods) {
            if (method.isAnnotationPresent(cls2)) {
                return Optional.m536of(method);
            }
        }
        return Optional.empty();
    }

    public static Object invoke(Object obj, Method method, Object... objArr) {
        ArgUtil.notNull(method, LoggingKeys.HTTP_METHOD_KEY);
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static Object invoke(Object obj, String str, Object... objArr) {
        ArgUtil.notEmpty(str, "methodName");
        try {
            if (ArrayUtil.isEmpty(objArr)) {
                return invokeNoArgsMethod(obj, str);
            }
            Class<?> cls = obj.getClass();
            Class[] clsArr = new Class[objArr.length];
            for (int i = 0; i < objArr.length; i++) {
                clsArr[i] = objArr[i].getClass();
            }
            return ClassUtil.getMethod(cls, str, clsArr).invoke(obj, objArr);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static Object invokeNoArgsMethod(Object obj, Method method) {
        if (ObjectUtil.isNull(method)) {
            return null;
        }
        String name = method.getName();
        if (ArrayUtil.isNotEmpty(method.getParameterTypes())) {
            throw new CommonRuntimeException(name + " must be has no params.");
        }
        return invoke(obj, method, new Object[0]);
    }

    public static Object invokeNoArgsMethod(Object obj, String str) {
        ArgUtil.notNull(obj, "instance");
        return invokeNoArgsMethod(obj, ClassUtil.getMethod(obj.getClass(), str));
    }

    public static Object invokeFactoryMethod(Class cls, Method method) {
        ArgUtil.notNull(cls, "clazz");
        ArgUtil.notNull(method, "factoryMethod");
        String name = method.getName();
        if (ArrayUtil.isNotEmpty(method.getParameterTypes())) {
            throw new CommonRuntimeException(name + " must be has no params.");
        }
        if (!Modifier.isStatic(method.getModifiers())) {
            throw new CommonRuntimeException(name + " must be static.");
        }
        Class<?> returnType = method.getReturnType();
        if (!returnType.isAssignableFrom(cls)) {
            throw new CommonRuntimeException(name + " must be return " + returnType.getName());
        }
        return invoke((Object) null, method, new Object[0]);
    }

    public static Class getGenericReturnParamType(Method method, int i) {
        ArgUtil.notNull(method, LoggingKeys.HTTP_METHOD_KEY);
        ArgUtil.notNegative(i, "paramIndex");
        Type genericReturnType = method.getGenericReturnType();
        if (ObjectUtil.isNull(genericReturnType)) {
            return null;
        }
        return TypeUtil.getGenericParamType(genericReturnType, i);
    }

    public static void invokeSetterMethod(Object obj, String str, Object obj2) {
        ArgUtil.notNull(obj, "instance");
        ArgUtil.notNull(str, "propertyName");
        if (ObjectUtil.isNull(obj2)) {
            return;
        }
        try {
            obj.getClass().getMethod(buildSetMethodName(str), obj2.getClass()).invoke(obj, obj2);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static Object invokeGetterMethod(Object obj, String str, Class cls) {
        ArgUtil.notNull(obj, "instance");
        ArgUtil.notNull(cls, "fieldType");
        ArgUtil.notEmpty(str, "fieldName");
        try {
            return obj.getClass().getMethod(buildGetMethodName(cls, str), new Class[0]).invoke(obj, new Object[0]);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static Object invokeGetterMethod(Object obj, String str) {
        return invokeGetterMethod(obj, str, String.class);
    }

    public static Object invokeGetterMethod(Object obj, Field field) {
        return invokeGetterMethod(obj, field.getName(), field.getType());
    }

    public static String buildSetMethodName(String str) {
        ArgUtil.notEmpty(str, "propertyName");
        return MethodConst.SET_PREFIX + StringUtil.firstToUpperCase(str);
    }

    public static String buildGetMethodName(Class cls, String str) {
        ArgUtil.notNull(cls, "fieldType");
        ArgUtil.notEmpty(str, "propertyName");
        if (Boolean.TYPE.equals(cls)) {
            return MethodConst.IS_PREFIX + StringUtil.firstToUpperCase(str);
        }
        return MethodConst.GET_PREFIX + StringUtil.firstToUpperCase(str);
    }

    public static String buildGetMethodName(String str) {
        return buildGetMethodName(String.class, str);
    }

    public static String getMethodFullName(Method method) {
        if (method == null) {
            return "null";
        }
        String name = method.getDeclaringClass().getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        StringBuilder sb = new StringBuilder(name + "." + method.getName());
        if (ObjectUtil.isNotEmpty(parameterTypes)) {
            for (Class<?> cls : parameterTypes) {
                sb.append(":").append(cls.getName());
            }
        }
        return sb.toString();
    }
}
