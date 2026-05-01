package com.github.houbb.heaven.support.cache;

/* JADX INFO: loaded from: classes3.dex */
public interface ICache<K, V> {
    V get(K k);

    void set(K k, V v);
}
