package com.github.houbb.heaven.util.lang.reflect;

import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import com.github.houbb.heaven.util.common.ArgUtil;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes3.dex */
public final class ReflectConstructorUtil {
    private ReflectConstructorUtil() {
    }

    public static <T> T newInstance(Constructor<T> constructor, Object... objArr) {
        ArgUtil.notNull(constructor, "constructor");
        try {
            return constructor.newInstance(objArr);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new CommonRuntimeException(e);
        }
    }
}
