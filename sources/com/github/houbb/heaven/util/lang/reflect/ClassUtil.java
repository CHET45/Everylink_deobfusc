package com.github.houbb.heaven.util.lang.reflect;

import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import com.github.houbb.heaven.support.filter.IFilter;
import com.github.houbb.heaven.support.handler.IHandler;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.MapUtil;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public final class ClassUtil {
    public static final String SERIAL_VERSION_UID = "serialVersionUID";

    private ClassUtil() {
    }

    public static String getClassVar(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    public static List<Field> getAllFieldList(Class cls) {
        ArrayList<Field> arrayList = new ArrayList();
        while (cls != null) {
            arrayList.addAll(Guavas.newArrayList(cls.getDeclaredFields()));
            cls = cls.getSuperclass();
        }
        List<Field> listNewArrayList = Guavas.newArrayList(arrayList.size());
        for (Field field : arrayList) {
            if (!"serialVersionUID".equals(field.getName())) {
                field.setAccessible(true);
                listNewArrayList.add(field);
            }
        }
        return listNewArrayList;
    }

    public static List<Field> getModifyableFieldList(Class cls) {
        List<Field> allFieldList = getAllFieldList(cls);
        return CollectionUtil.isEmpty(allFieldList) ? allFieldList : CollectionUtil.filterList(allFieldList, new IFilter<Field>() { // from class: com.github.houbb.heaven.util.lang.reflect.ClassUtil.1
            @Override // com.github.houbb.heaven.support.filter.IFilter
            public boolean filter(Field field) {
                return Modifier.isFinal(field.getModifiers());
            }
        });
    }

    public static Map<String, Field> getAllFieldMap(Class cls) {
        return MapUtil.toMap(getAllFieldList(cls), new IHandler<Field, String>() { // from class: com.github.houbb.heaven.util.lang.reflect.ClassUtil.2
            @Override // com.github.houbb.heaven.support.handler.IHandler
            public String handle(Field field) {
                return field.getName();
            }
        });
    }

    public static Field getField(Class<?> cls, String str) {
        if (cls != null && !StringUtil.isEmpty(str)) {
            List<Field> allFieldList = getAllFieldList(cls);
            if (CollectionUtil.isEmpty(allFieldList)) {
                return null;
            }
            for (Field field : allFieldList) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
        }
        return null;
    }

    @Deprecated
    public static Map<String, Object> beanToMap(Object obj) {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Field field : getAllFieldList(obj.getClass())) {
                linkedHashMap.put(field.getName(), field.get(obj));
            }
            return linkedHashMap;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T newInstance(Class<T> cls) {
        try {
            return cls.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Method> getAllFieldsReadMethods(Class cls) throws IntrospectionException {
        List<Field> allFieldList = getAllFieldList(cls);
        if (CollectionUtil.isEmpty(allFieldList)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<Field> it = allFieldList.iterator();
        while (it.hasNext()) {
            arrayList.add(new PropertyDescriptor(it.next().getName(), cls).getReadMethod());
        }
        return arrayList;
    }

    public static ClassLoader currentClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class getClass(String str) {
        ArgUtil.notEmpty(str, "className");
        try {
            return currentClassLoader().loadClass(str);
        } catch (ClassNotFoundException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static Method getMethod(Class cls, String str, Class... clsArr) {
        ArgUtil.notNull(cls, "clazz");
        ArgUtil.notEmpty(str, "methodName");
        try {
            return cls.getMethod(str, clsArr);
        } catch (NoSuchMethodException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static Method getMethod(Class<?> cls, String str) {
        ArgUtil.notNull(cls, "clazz");
        ArgUtil.notEmpty(str, "methodName");
        for (Method method : cls.getMethods()) {
            if (method.getName().equals(str)) {
                return method;
            }
        }
        throw new CommonRuntimeException("对应方法不存在!");
    }

    public static Constructor getConstructor(Class cls, Class... clsArr) {
        ArgUtil.notNull(cls, "clazz");
        try {
            return cls.getConstructor(clsArr);
        } catch (NoSuchMethodException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static List<Method> getMethodList(Class cls) {
        ArgUtil.notNull(cls, "tClass");
        return ArrayUtil.toList(cls.getMethods());
    }

    public static List<Method> getDeclaredMethodList(Class cls) {
        ArgUtil.notNull(cls, "tClass");
        return ArrayUtil.toList(cls.getDeclaredMethods());
    }

    public static List<Class> getAllSuperClass(Class cls) {
        ArgUtil.notNull(cls, "clazz");
        Set setNewHashSet = Guavas.newHashSet();
        for (Class superclass = cls.getSuperclass(); superclass != null; superclass = superclass.getSuperclass()) {
            setNewHashSet.add(superclass);
        }
        return Guavas.newArrayList(setNewHashSet);
    }

    public static List<Class> getAllInterfaces(Class cls) {
        ArgUtil.notNull(cls, "clazz");
        Set setNewHashSet = Guavas.newHashSet();
        Class<?>[] interfaces = cls.getInterfaces();
        if (ArrayUtil.isNotEmpty(interfaces)) {
            setNewHashSet.addAll(ArrayUtil.toList(interfaces));
            for (Class<?> cls2 : interfaces) {
                List<Class> allInterfaces = getAllInterfaces(cls2);
                if (CollectionUtil.isNotEmpty(allInterfaces)) {
                    setNewHashSet.addAll(allInterfaces);
                }
            }
        }
        return Guavas.newArrayList(setNewHashSet);
    }

    public static List<Class> getAllInterfacesAndSuperClass(Class cls) {
        ArgUtil.notNull(cls, "clazz");
        Set setNewHashSet = Guavas.newHashSet();
        setNewHashSet.addAll(getAllInterfaces(cls));
        setNewHashSet.addAll(getAllSuperClass(cls));
        return Guavas.newArrayList(setNewHashSet);
    }

    public static boolean isAssignable(Class<?> cls, Class<?> cls2) {
        if (ObjectUtil.isNull(cls) || ObjectUtil.isNull(cls2)) {
            return false;
        }
        if (cls.isAssignableFrom(cls2)) {
            return true;
        }
        if (cls.isPrimitive()) {
            return cls == PrimitiveUtil.getPrimitiveType(cls2);
        }
        Class<?> primitiveType = PrimitiveUtil.getPrimitiveType(cls2);
        return primitiveType != null && cls.isAssignableFrom(primitiveType);
    }

    public static boolean instanceOf(Class<?> cls, String str) {
        ArgUtil.notNull(cls, "clazz");
        ArgUtil.notEmpty(str, "interfaceName");
        if (cls.getName().equals(str)) {
            return true;
        }
        List<Class> allInterfaces = getAllInterfaces(cls);
        if (CollectionUtil.isEmpty(allInterfaces)) {
            return false;
        }
        Iterator<Class> it = allInterfaces.iterator();
        while (it.hasNext()) {
            if (it.next().getName().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static String getClassSimpleName(String str) {
        if (StringUtil.isEmpty(str) || !str.contains(".")) {
            return str;
        }
        return str.split("\\.")[r1.length - 1];
    }
}
