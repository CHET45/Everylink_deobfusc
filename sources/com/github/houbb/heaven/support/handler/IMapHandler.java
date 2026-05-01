package com.github.houbb.heaven.support.handler;

/* JADX INFO: loaded from: classes3.dex */
public interface IMapHandler<K, V, O> {
    K getKey(O o);

    V getValue(O o);
}
