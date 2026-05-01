package com.github.houbb.heaven.util.lang.reflect;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.Optional;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public final class ReflectAnnotationUtil {
    private ReflectAnnotationUtil() {
    }

    public static void updateValue(Annotation annotation, String str, Object obj) {
        getAnnotationAttributes(annotation).put(str, obj);
    }

    public static Object getValue(Annotation annotation, String str) {
        return getAnnotationAttributes(annotation).get(str);
    }

    public static String getValueStr(Annotation annotation, String str) {
        return ObjectUtil.objectToString(getAnnotationAttributes(annotation).get(str));
    }

    public static Map<String, Object> getAnnotationAttributes(Annotation annotation) {
        try {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
            Field declaredField = invocationHandler.getClass().getDeclaredField("memberValues");
            declaredField.setAccessible(true);
            return (Map) declaredField.get(invocationHandler);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Optional<Annotation> getAnnotation(Annotation annotation, Class<? extends Annotation> cls) {
        if (ObjectUtil.isNull(annotation) || ObjectUtil.isNull(cls)) {
            return Optional.empty();
        }
        Annotation annotation2 = annotation.annotationType().getAnnotation(cls);
        if (ObjectUtil.isNotNull(annotation2)) {
            return Optional.m536of(annotation2);
        }
        return Optional.empty();
    }

    public static Optional<Annotation> getAnnotation(Class cls, Class<? extends Annotation> cls2) {
        ArgUtil.notNull(cls, "clazz");
        ArgUtil.notNull(cls2, "annotationClass");
        if (cls.isAnnotationPresent(cls2)) {
            return Optional.m536of(cls.getAnnotation(cls2));
        }
        return Optional.empty();
    }

    public static List<Annotation> getAnnotationRefs(Class cls, Class<? extends Annotation> cls2) {
        ArgUtil.notNull(cls, "clazz");
        ArgUtil.notNull(cls2, "annotationClass");
        Set setNewHashSet = Guavas.newHashSet();
        Annotation[] annotations = cls.getAnnotations();
        if (ArrayUtil.isEmpty(annotations)) {
            return Guavas.newArrayList();
        }
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(cls2)) {
                setNewHashSet.add(annotation);
            } else if (annotation.annotationType().isAnnotationPresent(cls2)) {
                setNewHashSet.add(annotation);
            }
        }
        return Guavas.newArrayList(setNewHashSet);
    }
}
