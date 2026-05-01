package com.github.houbb.heaven.reflect.handler;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.reflect.simple.SimpleAnnotation;
import com.github.houbb.heaven.support.handler.IHandler;
import java.lang.annotation.Annotation;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class SimpleAnnotationHandler implements IHandler<Annotation, SimpleAnnotation> {
    @Override // com.github.houbb.heaven.support.handler.IHandler
    public SimpleAnnotation handle(Annotation annotation) {
        SimpleAnnotation simpleAnnotation = new SimpleAnnotation();
        simpleAnnotation.type(annotation.annotationType());
        return simpleAnnotation;
    }
}
