package com.github.houbb.heaven.reflect.util;

import com.github.houbb.heaven.reflect.api.IField;
import com.github.houbb.heaven.reflect.exception.ReflectRuntimeException;
import com.github.houbb.heaven.reflect.handler.SimpleFieldHandler;
import com.github.houbb.heaven.support.cache.impl.ClassFieldListCache;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.util.CollectionUtil;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public final class Classes {
    private Classes() {
    }

    public static List<IField> getFields(Class cls) {
        ArgUtil.notNull(cls, "clazz");
        List<Field> list = ClassFieldListCache.getInstance().get(cls);
        List<IField> listNewArrayList = Guavas.newArrayList(list.size());
        SimpleFieldHandler simpleFieldHandler = new SimpleFieldHandler();
        Iterator<Field> it = list.iterator();
        while (it.hasNext()) {
            listNewArrayList.add(simpleFieldHandler.handle(it.next()));
        }
        return listNewArrayList;
    }

    public static void initFieldValue(Object obj, List<IField> list) {
        if (CollectionUtil.isNotEmpty(list)) {
            try {
                for (IField iField : list) {
                    iField.value(iField.field().get(obj));
                }
            } catch (IllegalAccessException e) {
                throw new ReflectRuntimeException(e);
            }
        }
    }
}
