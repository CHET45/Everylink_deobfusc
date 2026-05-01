package com.github.houbb.heaven.reflect.model;

import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.util.Optional;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes3.dex */
public class FieldBean {
    private Annotation annotation;
    private Field field;
    private String name;

    public String name() {
        return this.name;
    }

    public FieldBean name(String str) {
        this.name = str;
        return this;
    }

    public Field field() {
        return this.field;
    }

    public FieldBean field(Field field) {
        this.field = field;
        return this;
    }

    public Annotation annotation() {
        return this.annotation;
    }

    public FieldBean annotation(Annotation annotation) {
        this.annotation = annotation;
        return this;
    }

    public <T extends Annotation> T annotationByType(Class<T> cls) {
        if (ObjectUtil.isNull(this.annotation)) {
            return null;
        }
        return (T) this.annotation;
    }

    public <T extends Annotation> Optional<T> annotationOptByType(Class<T> cls) {
        return Optional.ofNullable(annotationByType(cls));
    }
}
