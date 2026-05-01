package com.github.houbb.heaven.support.handler;

import com.github.houbb.heaven.util.lang.ObjectUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AbstractHandler<T, R> implements IHandler<T, R> {
    protected abstract R doHandle(T t);

    @Override // com.github.houbb.heaven.support.handler.IHandler
    public R handle(T t) {
        if (ObjectUtil.isNull(t)) {
            return null;
        }
        return doHandle(t);
    }
}
