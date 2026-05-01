package com.github.houbb.heaven.reflect.meta.field;

import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes3.dex */
public interface IFieldMeta {
    Class getComponentType();

    Field getField();

    String getName();

    Class getType();

    Object getValue();

    void setComponentType(Class cls);

    void setField(Field field);

    void setName(String str);

    void setType(Class cls);

    void setValue(Object obj);
}
