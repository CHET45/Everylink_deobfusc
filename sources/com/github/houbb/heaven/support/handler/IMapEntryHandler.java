package com.github.houbb.heaven.support.handler;

import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public interface IMapEntryHandler<K, V, T> {
    T handler(Map.Entry<K, V> entry);
}
