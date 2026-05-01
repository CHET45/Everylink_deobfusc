package com.github.houbb.heaven.support.instance.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import com.github.houbb.heaven.support.instance.Instance;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.lzy.okgo.cache.CacheHelper;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public final class InstanceFactory implements Instance {
    private ThreadLocal<Map<String, Object>> mapThreadLocal;
    private final Map<String, Object> singletonMap;

    private InstanceFactory() {
        this.singletonMap = new ConcurrentHashMap();
        this.mapThreadLocal = new ThreadLocal<>();
    }

    private static class SingletonHolder {
        private static final InstanceFactory INSTANCE_FACTORY = new InstanceFactory();

        private SingletonHolder() {
        }
    }

    public static InstanceFactory getInstance() {
        return SingletonHolder.INSTANCE_FACTORY;
    }

    public static <T> T singletion(Class<T> cls) {
        return (T) getInstance().singleton(cls);
    }

    public static <T> T singletion(Class<T> cls, String str) {
        return (T) getInstance().singleton(cls, str);
    }

    @Override // com.github.houbb.heaven.support.instance.Instance
    public <T> T singleton(Class<T> cls, String str) {
        return (T) getSingleton(cls, str, this.singletonMap);
    }

    @Override // com.github.houbb.heaven.support.instance.Instance
    public <T> T singleton(Class<T> cls) {
        notNull(cls);
        return (T) getSingleton(cls, this.singletonMap);
    }

    @Override // com.github.houbb.heaven.support.instance.Instance
    public <T> T threadLocal(Class<T> cls) {
        notNull(cls);
        Map<String, Object> concurrentHashMap = this.mapThreadLocal.get();
        if (ObjectUtil.isNull(concurrentHashMap)) {
            concurrentHashMap = new ConcurrentHashMap<>();
        }
        T t = (T) getSingleton(cls, concurrentHashMap);
        this.mapThreadLocal.set(concurrentHashMap);
        return t;
    }

    @Override // com.github.houbb.heaven.support.instance.Instance
    public <T> T multiple(Class<T> cls) {
        notNull(cls);
        try {
            return cls.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new CommonRuntimeException(e);
        }
    }

    @Override // com.github.houbb.heaven.support.instance.Instance
    public <T> T threadSafe(Class<T> cls) {
        if (cls.isAnnotationPresent(ThreadSafe.class)) {
            return (T) singleton(cls);
        }
        return (T) multiple(cls);
    }

    private <T> T getSingleton(Class<T> cls, Map<String, Object> map) {
        notNull(cls);
        String name = cls.getName();
        T t = (T) map.get(name);
        if (!ObjectUtil.isNull(t)) {
            return t;
        }
        T t2 = (T) multiple(cls);
        map.put(name, t2);
        return t2;
    }

    private <T> T getSingleton(Class<T> cls, String str, Map<String, Object> map) {
        notNull(cls);
        ArgUtil.notEmpty(str, CacheHelper.KEY);
        String str2 = cls.getName() + "-" + str;
        T t = (T) map.get(str2);
        if (!ObjectUtil.isNull(t)) {
            return t;
        }
        T t2 = (T) multiple(cls);
        map.put(str2, t2);
        return t2;
    }

    private void notNull(Class cls) {
        ArgUtil.notNull(cls, "class");
    }
}
