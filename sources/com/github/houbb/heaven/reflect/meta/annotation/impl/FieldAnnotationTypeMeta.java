package com.github.houbb.heaven.reflect.meta.annotation.impl;

import com.github.houbb.heaven.util.common.ArgUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes3.dex */
public class FieldAnnotationTypeMeta extends AbstractAnnotationTypeMeta {
    private Annotation[] annotations;

    public FieldAnnotationTypeMeta(Field field) {
        ArgUtil.notNull(field, "field");
        this.annotations = field.getAnnotations();
    }

    @Override // com.github.houbb.heaven.reflect.meta.annotation.impl.AbstractAnnotationTypeMeta
    protected Annotation[] getAnnotations() {
        return this.annotations;
    }
}
