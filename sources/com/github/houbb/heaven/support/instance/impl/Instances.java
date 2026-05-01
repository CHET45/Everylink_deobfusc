package com.github.houbb.heaven.support.instance.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public final class Instances {
    private Instances() {
    }

    public static <T> T singleton(Class<T> cls) {
        return (T) InstanceFactory.getInstance().singleton(cls);
    }

    public static <T> T singleton(Class<T> cls, String str) {
        return (T) InstanceFactory.getInstance().singleton(cls, str);
    }

    public static <T> T threadLocal(Class<T> cls) {
        return (T) InstanceFactory.getInstance().threadLocal(cls);
    }

    public static <T> T threadSafe(Class<T> cls) {
        return (T) InstanceFactory.getInstance().threadSafe(cls);
    }

    public static <T> T multiple(Class<T> cls) {
        return (T) InstanceFactory.getInstance().multiple(cls);
    }
}
