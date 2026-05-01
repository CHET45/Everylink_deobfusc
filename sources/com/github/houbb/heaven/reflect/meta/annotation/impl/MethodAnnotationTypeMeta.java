package com.github.houbb.heaven.reflect.meta.annotation.impl;

import com.azure.core.implementation.logging.LoggingKeys;
import com.github.houbb.heaven.util.common.ArgUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes3.dex */
public class MethodAnnotationTypeMeta extends AbstractAnnotationTypeMeta {
    private Annotation[] annotations;

    public MethodAnnotationTypeMeta(Method method) {
        ArgUtil.notNull(method, LoggingKeys.HTTP_METHOD_KEY);
        this.annotations = method.getAnnotations();
    }

    @Override // com.github.houbb.heaven.reflect.meta.annotation.impl.AbstractAnnotationTypeMeta
    protected Annotation[] getAnnotations() {
        return this.annotations;
    }
}
