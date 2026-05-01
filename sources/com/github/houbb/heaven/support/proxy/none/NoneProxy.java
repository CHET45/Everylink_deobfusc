package com.github.houbb.heaven.support.proxy.none;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.support.proxy.IProxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class NoneProxy implements InvocationHandler, IProxy {
    private final Object target;

    public NoneProxy(Object obj) {
        this.target = obj;
    }

    @Override // com.github.houbb.heaven.support.proxy.IProxy
    public Object proxy() {
        return this.target;
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
        return method.invoke(obj, objArr);
    }
}
