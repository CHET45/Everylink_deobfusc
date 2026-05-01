package com.github.houbb.heaven.reflect.meta.annotation.impl;

import com.github.houbb.heaven.util.common.ArgUtil;
import java.lang.annotation.Annotation;

/* JADX INFO: loaded from: classes3.dex */
public class ClassAnnotationTypeMeta extends AbstractAnnotationTypeMeta {
    private Annotation[] annotations;

    public ClassAnnotationTypeMeta(Class cls) {
        ArgUtil.notNull(cls, "clazz");
        this.annotations = cls.getAnnotations();
    }

    @Override // com.github.houbb.heaven.reflect.meta.annotation.impl.AbstractAnnotationTypeMeta
    protected Annotation[] getAnnotations() {
        return this.annotations;
    }
}
