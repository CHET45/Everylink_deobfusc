package com.github.houbb.heaven.util.lang.reflect;

/* JADX INFO: loaded from: classes3.dex */
public final class ReflectArrayUtil {
    private ReflectArrayUtil() {
    }

    public static Class getComponentType(Object[] objArr) {
        return objArr.getClass().getComponentType();
    }
}
