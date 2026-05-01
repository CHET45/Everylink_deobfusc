package com.github.houbb.heaven.reflect.simple;

import com.github.houbb.heaven.reflect.api.IField;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.Optional;
import com.microsoft.azure.storage.table.TableConstants;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class SimpleField implements IField {
    private int access;
    private List<Annotation> annotations;
    private Field field;
    private String fullName;
    private String name;
    private Class type;
    private Object value;

    @Override // com.github.houbb.heaven.reflect.api.IField
    public List<Annotation> annotations() {
        return this.annotations;
    }

    @Override // com.github.houbb.heaven.reflect.api.IField
    public Optional<Annotation> annotationOpt(Class cls) {
        ArgUtil.notNull(cls, TableConstants.ErrorConstants.ERROR_EXCEPTION_TYPE);
        if (CollectionUtil.isEmpty(this.annotations)) {
            return Optional.empty();
        }
        for (Annotation annotation : this.annotations) {
            if (cls.equals(annotation.annotationType())) {
                return Optional.m536of(annotation);
            }
        }
        return Optional.empty();
    }

    @Override // com.github.houbb.heaven.reflect.api.IField
    public Annotation annotation(Class cls) {
        ArgUtil.notNull(cls, TableConstants.ErrorConstants.ERROR_EXCEPTION_TYPE);
        return annotationOpt(cls).orElseNull();
    }

    public SimpleField annotations(List<Annotation> list) {
        this.annotations = list;
        return this;
    }

    @Override // com.github.houbb.heaven.reflect.api.IMember
    public String name() {
        return this.name;
    }

    public SimpleField name(String str) {
        this.name = str;
        return this;
    }

    @Override // com.github.houbb.heaven.reflect.api.IMember
    public String fullName() {
        return this.fullName;
    }

    public SimpleField fullName(String str) {
        this.fullName = str;
        return this;
    }

    @Override // com.github.houbb.heaven.reflect.api.IMember
    public Class type() {
        return this.type;
    }

    public SimpleField type(Class cls) {
        this.type = cls;
        return this;
    }

    @Override // com.github.houbb.heaven.reflect.api.IMember
    public int access() {
        return this.access;
    }

    public SimpleField access(int i) {
        this.access = i;
        return this;
    }

    @Override // com.github.houbb.heaven.reflect.api.IField
    public Object value() {
        return this.value;
    }

    @Override // com.github.houbb.heaven.reflect.api.IField
    public SimpleField value(Object obj) {
        this.value = obj;
        return this;
    }

    @Override // com.github.houbb.heaven.reflect.api.IField
    public Field field() {
        return this.field;
    }

    public SimpleField field(Field field) {
        this.field = field;
        return this;
    }

    @Override // java.lang.Comparable
    public int compareTo(IField iField) {
        return this.name.compareTo(iField.name());
    }
}
