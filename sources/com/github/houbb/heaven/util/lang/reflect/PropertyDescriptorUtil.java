package com.github.houbb.heaven.util.lang.reflect;

import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import com.github.houbb.heaven.support.handler.IHandler;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public final class PropertyDescriptorUtil {
    private PropertyDescriptorUtil() {
    }

    public static PropertyDescriptor getPropertyDescriptor(Class cls, String str) {
        ArgUtil.notNull(cls, "beanClass");
        ArgUtil.notEmpty(str, "propertyName");
        try {
            return new PropertyDescriptor(str, cls);
        } catch (IntrospectionException e) {
            throw new CommonRuntimeException((Throwable) e);
        }
    }

    public static Method getReadMethod(Class<?> cls, String str) {
        return getPropertyDescriptor(cls, str).getReadMethod();
    }

    public static Method getWriteMethod(Class cls, String str) {
        return getPropertyDescriptor(cls, str).getWriteMethod();
    }

    public static Class<?> getPropertyType(Class cls, String str) {
        return getPropertyDescriptor(cls, str).getPropertyType();
    }

    public static List<PropertyDescriptor> getAllPropertyDescriptorList(final Class cls) {
        ArgUtil.notNull(cls, "beanClass");
        return CollectionUtil.toList(ClassUtil.getAllFieldList(cls), new IHandler<Field, PropertyDescriptor>() { // from class: com.github.houbb.heaven.util.lang.reflect.PropertyDescriptorUtil.1
            @Override // com.github.houbb.heaven.support.handler.IHandler
            public PropertyDescriptor handle(Field field) {
                return PropertyDescriptorUtil.getPropertyDescriptor(cls, field.getName());
            }
        });
    }

    public static boolean setPropertyValue(Object obj, PropertyDescriptor propertyDescriptor, Object obj2) {
        try {
            Method writeMethod = propertyDescriptor.getWriteMethod();
            if (writeMethod == null) {
                return false;
            }
            writeMethod.invoke(obj, obj2);
            return true;
        } catch (Exception e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static boolean setPropertyValue(Object obj, String str, Object obj2) {
        return setPropertyValue(obj, getPropertyDescriptor(obj.getClass(), str), obj2);
    }

    public static Object getPropertyValue(Object obj, PropertyDescriptor propertyDescriptor) {
        try {
            Method readMethod = propertyDescriptor.getReadMethod();
            if (readMethod == null) {
                return null;
            }
            return readMethod.invoke(obj, new Object[0]);
        } catch (Exception e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static Object getPropertyValue(Object obj, String str) {
        return getPropertyValue(obj, getPropertyDescriptor(obj.getClass(), str));
    }
}
