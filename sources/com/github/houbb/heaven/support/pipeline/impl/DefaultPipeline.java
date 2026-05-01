package com.github.houbb.heaven.support.pipeline.impl;

import com.github.houbb.heaven.annotation.NotThreadSafe;
import com.github.houbb.heaven.support.pipeline.Pipeline;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
@NotThreadSafe
public class DefaultPipeline<T> implements Pipeline<T> {
    private LinkedList<T> list = new LinkedList<>();

    @Override // com.github.houbb.heaven.support.pipeline.Pipeline
    public Pipeline addLast(T t) {
        this.list.addLast(t);
        return this;
    }

    @Override // com.github.houbb.heaven.support.pipeline.Pipeline
    public Pipeline addFirst(T t) {
        this.list.addFirst(t);
        return this;
    }

    @Override // com.github.houbb.heaven.support.pipeline.Pipeline
    public Pipeline set(int i, T t) {
        this.list.set(i, t);
        return this;
    }

    @Override // com.github.houbb.heaven.support.pipeline.Pipeline
    public Pipeline removeLast() {
        this.list.removeLast();
        return this;
    }

    @Override // com.github.houbb.heaven.support.pipeline.Pipeline
    public Pipeline removeFirst() {
        this.list.removeFirst();
        return this;
    }

    @Override // com.github.houbb.heaven.support.pipeline.Pipeline
    public Pipeline remove(int i) {
        this.list.remove(i);
        return this;
    }

    @Override // com.github.houbb.heaven.support.pipeline.Pipeline
    public T get(int i) {
        return this.list.get(i);
    }

    @Override // com.github.houbb.heaven.support.pipeline.Pipeline
    public T getFirst() {
        return this.list.getFirst();
    }

    @Override // com.github.houbb.heaven.support.pipeline.Pipeline
    public T getLast() {
        return this.list.getLast();
    }

    @Override // com.github.houbb.heaven.support.pipeline.Pipeline
    public List<T> list() {
        return Collections.unmodifiableList(this.list);
    }

    @Override // com.github.houbb.heaven.support.pipeline.Pipeline
    public List<T> slice(int i, int i2) {
        return this.list.subList(i, i2);
    }
}
