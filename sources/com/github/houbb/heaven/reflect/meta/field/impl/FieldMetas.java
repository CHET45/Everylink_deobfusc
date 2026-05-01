package com.github.houbb.heaven.reflect.meta.field.impl;

import com.github.houbb.heaven.constant.MethodConst;
import com.github.houbb.heaven.reflect.meta.field.IFieldMeta;
import com.github.houbb.heaven.support.condition.ICondition;
import com.github.houbb.heaven.support.handler.IHandler;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectFieldUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectMethodUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public final class FieldMetas {
    private FieldMetas() {
    }

    public static List<IFieldMeta> buildFieldsMetaList(Class cls, Object obj) {
        ArgUtil.notNull(cls, "clazz");
        return buildFieldsMetaList(ClassUtil.getModifyableFieldList(cls), obj);
    }

    public static List<IFieldMeta> buildFieldsMetaList(Class cls) {
        return buildFieldsMetaList(cls, (Object) null);
    }

    private static List<IFieldMeta> buildFieldsMetaList(List<Field> list, final Object obj) {
        return CollectionUtil.toList(list, new IHandler<Field, IFieldMeta>() { // from class: com.github.houbb.heaven.reflect.meta.field.impl.FieldMetas.1
            @Override // com.github.houbb.heaven.support.handler.IHandler
            public IFieldMeta handle(Field field) {
                FieldMeta fieldMeta = new FieldMeta();
                fieldMeta.setName(field.getName());
                fieldMeta.setType(field.getType());
                fieldMeta.setField(field);
                fieldMeta.setComponentType(ReflectFieldUtil.getComponentType(field));
                if (ObjectUtil.isNotNull(obj)) {
                    fieldMeta.setValue(ReflectFieldUtil.getValue(field, obj));
                }
                return fieldMeta;
            }
        });
    }

    public static List<IFieldMeta> buildReadMethodsMetaList(Class cls) {
        return buildReadMethodsMetaList(cls, null);
    }

    public static List<IFieldMeta> buildReadMethodsMetaList(Class cls, Object obj) {
        ArgUtil.notNull(cls, "clazz");
        return buildMethodsMetaList(ClassUtil.getMethodList(cls), obj, new ICondition<Method>() { // from class: com.github.houbb.heaven.reflect.meta.field.impl.FieldMetas.2
            @Override // com.github.houbb.heaven.support.condition.ICondition
            public boolean condition(Method method) {
                String name = method.getName();
                Class<?> returnType = method.getReturnType();
                if (ArrayUtil.isNotEmpty(method.getParameterTypes())) {
                    return false;
                }
                if (Boolean.TYPE == returnType) {
                    return name.startsWith(MethodConst.IS_PREFIX);
                }
                return name.startsWith(MethodConst.GET_PREFIX) && !name.equals(MethodConst.GET_CLASS_METHOD);
            }
        });
    }

    public static List<IFieldMeta> buildWriteMethodsMetaList(Class cls) {
        return buildWriteMethodsMetaList(cls, null);
    }

    public static List<IFieldMeta> buildWriteMethodsMetaList(Class cls, Object obj) {
        ArgUtil.notNull(cls, "clazz");
        return buildMethodsMetaList(ClassUtil.getMethodList(cls), obj, new ICondition<Method>() { // from class: com.github.houbb.heaven.reflect.meta.field.impl.FieldMetas.3
            @Override // com.github.houbb.heaven.support.condition.ICondition
            public boolean condition(Method method) {
                return method.getName().startsWith(MethodConst.SET_PREFIX) && method.getParameterTypes().length == 1;
            }
        });
    }

    private static List<IFieldMeta> buildMethodsMetaList(List<Method> list, final Object obj, ICondition<Method> iCondition) {
        return CollectionUtil.toList(CollectionUtil.conditionList(list, iCondition), new IHandler<Method, IFieldMeta>() { // from class: com.github.houbb.heaven.reflect.meta.field.impl.FieldMetas.4
            @Override // com.github.houbb.heaven.support.handler.IHandler
            public IFieldMeta handle(Method method) {
                String strFirstToLowerCase;
                String name = method.getName();
                if (name.startsWith(MethodConst.IS_PREFIX)) {
                    strFirstToLowerCase = StringUtil.firstToLowerCase(name.substring(2));
                } else {
                    strFirstToLowerCase = StringUtil.firstToLowerCase(name.substring(3));
                }
                FieldMeta fieldMeta = new FieldMeta();
                fieldMeta.setName(strFirstToLowerCase);
                if (name.startsWith(MethodConst.IS_PREFIX) || name.startsWith(MethodConst.GET_PREFIX)) {
                    fieldMeta.setType(method.getReturnType());
                    fieldMeta.setComponentType(ReflectMethodUtil.getGenericReturnParamType(method, 0));
                } else {
                    fieldMeta.setType(method.getParameterTypes()[0]);
                    fieldMeta.setComponentType(ReflectMethodUtil.getParamGenericType(method, 0, 0));
                }
                if (ObjectUtil.isNotNull(obj)) {
                    fieldMeta.setValue(ReflectMethodUtil.invoke(obj, method, new Object[0]));
                }
                return fieldMeta;
            }
        });
    }
}
