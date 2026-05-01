package com.github.houbb.heaven.reflect.handler;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.reflect.simple.SimpleField;
import com.github.houbb.heaven.support.handler.IHandler;
import java.lang.reflect.Field;
import java.util.Arrays;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class SimpleFieldHandler implements IHandler<Field, SimpleField> {
    @Override // com.github.houbb.heaven.support.handler.IHandler
    public SimpleField handle(Field field) {
        SimpleField simpleField = new SimpleField();
        simpleField.field(field);
        simpleField.name(field.getName());
        simpleField.fullName(field.getName());
        simpleField.type(field.getType());
        simpleField.annotations(Arrays.asList(field.getAnnotations()));
        simpleField.access(field.getModifiers());
        return simpleField;
    }
}
