package com.github.houbb.heaven.reflect.meta.annotation;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public interface IAnnotationTypeMeta {
    Object getAnnotatedAttr(Class<? extends Annotation> cls, String str);

    Annotation getAnnotation(String str);

    Object getAnnotationAttr(Annotation annotation, String str);

    Map<String, Object> getAnnotationAttributes(String str);

    @Deprecated
    Object getAnnotationOrRefAttribute(String str, String str2);

    @Deprecated
    Map<String, Object> getAnnotationOrRefAttributes(String str);

    List<Annotation> getAnnotationOrRefs(String str);

    Annotation getAnnotationReferenced(String str, String str2);

    Object getAnnotationReferencedAttr(Class<? extends Annotation> cls, String str);

    List<Annotation> getAnnotationRefs(String str);

    boolean isAnnotated(String str);

    boolean isAnnotatedOrRef(String str);

    boolean isAnnotatedOrRef(List<Class> list);

    boolean isAnnotationRef(Class<? extends Annotation> cls);
}
