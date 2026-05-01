package com.github.houbb.heaven.reflect.meta.annotation.impl;

import com.github.houbb.heaven.reflect.meta.annotation.IAnnotationTypeMeta;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectAnnotationUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.MapUtil;
import com.github.houbb.heaven.util.util.Optional;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AbstractAnnotationTypeMeta implements IAnnotationTypeMeta {
    private Map<String, Annotation> annotationRefMap = new ConcurrentHashMap();

    protected abstract Annotation[] getAnnotations();

    protected AbstractAnnotationTypeMeta() {
    }

    @Override // com.github.houbb.heaven.reflect.meta.annotation.IAnnotationTypeMeta
    public boolean isAnnotated(String str) {
        return ObjectUtil.isNotNull(getAnnotation(str));
    }

    @Override // com.github.houbb.heaven.reflect.meta.annotation.IAnnotationTypeMeta
    public Annotation getAnnotation(String str) {
        ArgUtil.notEmpty(str, "annotationName");
        return getAnnotationOpt(getAnnotations(), str).orDefault(null);
    }

    @Override // com.github.houbb.heaven.reflect.meta.annotation.IAnnotationTypeMeta
    public boolean isAnnotatedOrRef(String str) {
        if (isAnnotated(str)) {
            return true;
        }
        return CollectionUtil.isNotEmpty(getAnnotationRefs(str));
    }

    @Override // com.github.houbb.heaven.reflect.meta.annotation.IAnnotationTypeMeta
    public boolean isAnnotatedOrRef(List<Class> list) {
        if (CollectionUtil.isEmpty(list)) {
            return false;
        }
        Iterator<Class> it = list.iterator();
        while (it.hasNext()) {
            if (isAnnotatedOrRef(it.next().getName())) {
                return true;
            }
        }
        return false;
    }

    @Override // com.github.houbb.heaven.reflect.meta.annotation.IAnnotationTypeMeta
    public boolean isAnnotationRef(Class cls) {
        return isAnnotatedOrRef(cls.getName()) && !isAnnotated(cls.getName());
    }

    @Override // com.github.houbb.heaven.reflect.meta.annotation.IAnnotationTypeMeta
    public List<Annotation> getAnnotationOrRefs(String str) {
        Set setNewHashSet = Guavas.newHashSet();
        Annotation annotation = getAnnotation(str);
        if (ObjectUtil.isNotNull(annotation)) {
            setNewHashSet.add(annotation);
        }
        setNewHashSet.addAll(getAnnotationRefs(str));
        return Guavas.newArrayList(setNewHashSet);
    }

    private Optional<Annotation> getAnnotationOpt(Annotation[] annotationArr, String str) {
        return getAnnotationOpt(ArrayUtil.toList(annotationArr), str);
    }

    private Optional<Annotation> getAnnotationOpt(List<Annotation> list, String str) {
        if (CollectionUtil.isEmpty(list)) {
            return Optional.empty();
        }
        for (Annotation annotation : list) {
            if (annotation.annotationType().getName().equals(str)) {
                return Optional.ofNullable(annotation);
            }
        }
        return Optional.empty();
    }

    @Override // com.github.houbb.heaven.reflect.meta.annotation.IAnnotationTypeMeta
    public List<Annotation> getAnnotationRefs(String str) {
        Set setNewHashSet = Guavas.newHashSet();
        if (ArrayUtil.isNotEmpty(getAnnotations())) {
            for (Annotation annotation : getAnnotations()) {
                Optional<Annotation> annotationOpt = getAnnotationOpt(annotation.annotationType().getAnnotations(), str);
                if (annotationOpt.isPresent()) {
                    this.annotationRefMap.put(str + annotation.annotationType().getName(), annotationOpt.get());
                    setNewHashSet.add(annotation);
                }
            }
        }
        return Guavas.newArrayList(setNewHashSet);
    }

    @Override // com.github.houbb.heaven.reflect.meta.annotation.IAnnotationTypeMeta
    public Annotation getAnnotationReferenced(String str, String str2) {
        ArgUtil.notEmpty(str, "annotationName");
        ArgUtil.notEmpty(str2, "annotationRefName");
        return this.annotationRefMap.get(str + str2);
    }

    @Override // com.github.houbb.heaven.reflect.meta.annotation.IAnnotationTypeMeta
    public Map<String, Object> getAnnotationAttributes(String str) {
        ArgUtil.notEmpty(str, "annotationName");
        Annotation annotation = getAnnotation(str);
        if (ObjectUtil.isNull(annotation)) {
            return null;
        }
        return ReflectAnnotationUtil.getAnnotationAttributes(annotation);
    }

    @Override // com.github.houbb.heaven.reflect.meta.annotation.IAnnotationTypeMeta
    public Map<String, Object> getAnnotationOrRefAttributes(String str) {
        ArgUtil.notEmpty(str, "annotationName");
        List<Annotation> annotationOrRefs = getAnnotationOrRefs(str);
        if (CollectionUtil.isEmpty(annotationOrRefs)) {
            return null;
        }
        return ReflectAnnotationUtil.getAnnotationAttributes(annotationOrRefs.get(0));
    }

    @Override // com.github.houbb.heaven.reflect.meta.annotation.IAnnotationTypeMeta
    public Object getAnnotationOrRefAttribute(String str, String str2) {
        ArgUtil.notEmpty(str, "annotationName");
        ArgUtil.notEmpty(str2, "attrMethodName");
        Map<String, Object> annotationOrRefAttributes = getAnnotationOrRefAttributes(str);
        if (MapUtil.isEmpty(annotationOrRefAttributes)) {
            return null;
        }
        return annotationOrRefAttributes.get(str2);
    }

    @Override // com.github.houbb.heaven.reflect.meta.annotation.IAnnotationTypeMeta
    public Object getAnnotationAttr(Annotation annotation, String str) {
        ArgUtil.notNull(annotation, "annotation");
        ArgUtil.notEmpty(str, "methodName");
        return ReflectAnnotationUtil.getAnnotationAttributes(annotation).get(str);
    }

    @Override // com.github.houbb.heaven.reflect.meta.annotation.IAnnotationTypeMeta
    public Object getAnnotatedAttr(Class<? extends Annotation> cls, String str) {
        ArgUtil.notNull(cls, "clazz");
        ArgUtil.notEmpty(str, "methodName");
        Annotation annotation = getAnnotation(cls.getName());
        if (ObjectUtil.isNotNull(annotation)) {
            return getAnnotationAttr(annotation, str);
        }
        return null;
    }

    @Override // com.github.houbb.heaven.reflect.meta.annotation.IAnnotationTypeMeta
    public Object getAnnotationReferencedAttr(Class<? extends Annotation> cls, String str) {
        ArgUtil.notNull(cls, "clazz");
        ArgUtil.notEmpty(str, "methodName");
        String name = cls.getName();
        if (!ArrayUtil.isNotEmpty(getAnnotations())) {
            return null;
        }
        for (Annotation annotation : getAnnotations()) {
            Annotation[] annotations = annotation.annotationType().getAnnotations();
            if (ArrayUtil.isNotEmpty(annotations)) {
                for (Annotation annotation2 : annotations) {
                    if (name.equals(annotation2.annotationType().getName())) {
                        return getAnnotationAttr(annotation2, str);
                    }
                }
            }
        }
        return null;
    }
}
