package com.github.houbb.heaven.support.instance;

/* JADX INFO: loaded from: classes3.dex */
public interface Instance {
    <T> T multiple(Class<T> cls);

    <T> T singleton(Class<T> cls);

    <T> T singleton(Class<T> cls, String str);

    <T> T threadLocal(Class<T> cls);

    <T> T threadSafe(Class<T> cls);
}
