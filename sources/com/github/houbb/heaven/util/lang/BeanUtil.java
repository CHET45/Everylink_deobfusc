package com.github.houbb.heaven.util.lang;

import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import com.github.houbb.heaven.support.cache.impl.ClassFieldListCache;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.util.MapUtil;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public final class BeanUtil {
    private BeanUtil() {
    }

    public static Map<String, Object> beanToMap(Object obj) {
        ArgUtil.notNull(obj, "bean");
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Field field : ClassFieldListCache.getInstance().get(obj.getClass())) {
                linkedHashMap.put(field.getName(), field.get(obj));
            }
            return linkedHashMap;
        } catch (IllegalAccessException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static void mapToBean(Map<String, Object> map, Object obj) {
        ArgUtil.notNull(obj, "bean");
        if (MapUtil.isEmpty(map)) {
            return;
        }
        try {
            for (Field field : ClassFieldListCache.getInstance().get(obj.getClass())) {
                Object obj2 = map.get(field.getName());
                if (ObjectUtil.isNotNull(obj2)) {
                    field.set(obj, obj2);
                }
            }
        } catch (IllegalAccessException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static void copyProperties(Object obj, Object obj2) {
        ObjectUtil.copyProperties(obj, obj2);
    }
}
