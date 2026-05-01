package com.github.houbb.heaven.reflect.meta.field.impl;

import com.github.houbb.heaven.reflect.meta.field.IFieldMeta;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes3.dex */
public class FieldMeta implements IFieldMeta {
    private Class componentType;
    private Field field;
    private String name;
    private Class type;
    private Object value;

    @Override // com.github.houbb.heaven.reflect.meta.field.IFieldMeta
    public String getName() {
        return this.name;
    }

    @Override // com.github.houbb.heaven.reflect.meta.field.IFieldMeta
    public void setName(String str) {
        this.name = str;
    }

    @Override // com.github.houbb.heaven.reflect.meta.field.IFieldMeta
    public Class getType() {
        return this.type;
    }

    @Override // com.github.houbb.heaven.reflect.meta.field.IFieldMeta
    public void setType(Class cls) {
        this.type = cls;
    }

    @Override // com.github.houbb.heaven.reflect.meta.field.IFieldMeta
    public Object getValue() {
        return this.value;
    }

    @Override // com.github.houbb.heaven.reflect.meta.field.IFieldMeta
    public void setValue(Object obj) {
        this.value = obj;
    }

    @Override // com.github.houbb.heaven.reflect.meta.field.IFieldMeta
    public Class getComponentType() {
        return this.componentType;
    }

    @Override // com.github.houbb.heaven.reflect.meta.field.IFieldMeta
    public void setComponentType(Class cls) {
        this.componentType = cls;
    }

    @Override // com.github.houbb.heaven.reflect.meta.field.IFieldMeta
    public Field getField() {
        return this.field;
    }

    @Override // com.github.houbb.heaven.reflect.meta.field.IFieldMeta
    public void setField(Field field) {
        this.field = field;
    }
}
