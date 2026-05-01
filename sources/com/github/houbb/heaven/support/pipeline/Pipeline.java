package com.github.houbb.heaven.support.pipeline;

import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public interface Pipeline<T> {
    Pipeline addFirst(T t);

    Pipeline addLast(T t);

    T get(int i);

    T getFirst();

    T getLast();

    List<T> list();

    Pipeline remove(int i);

    Pipeline removeFirst();

    Pipeline removeLast();

    Pipeline set(int i, T t);

    List<T> slice(int i, int i2);
}
