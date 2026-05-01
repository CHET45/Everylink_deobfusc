package com.github.houbb.heaven.support.cache.impl;

import com.github.houbb.heaven.support.cache.ICache;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AbstractCache<K, V> implements ICache<K, V> {
    private final Map<K, V> cache = new ConcurrentHashMap();

    protected abstract V buildValue(K k);

    @Override // com.github.houbb.heaven.support.cache.ICache
    public V get(K k) {
        V v = this.cache.get(k);
        if (ObjectUtil.isNotNull(v)) {
            return v;
        }
        V vBuildValue = buildValue(k);
        set(k, vBuildValue);
        return vBuildValue;
    }

    @Override // com.github.houbb.heaven.support.cache.ICache
    public void set(K k, V v) {
        this.cache.put(k, v);
    }
}
