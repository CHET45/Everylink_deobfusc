package com.github.houbb.heaven.support.proxy;

import com.github.houbb.heaven.constant.enums.ProxyTypeEnum;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import java.lang.reflect.Proxy;

/* JADX INFO: loaded from: classes3.dex */
public class ProxyFactory {
    private ProxyFactory() {
    }

    public static ProxyTypeEnum getProxyType(Object obj) {
        if (ObjectUtil.isNull(obj)) {
            return ProxyTypeEnum.NONE;
        }
        Class<?> cls = obj.getClass();
        if (cls.isInterface() || Proxy.isProxyClass(cls)) {
            return ProxyTypeEnum.DYNAMIC;
        }
        return ProxyTypeEnum.CGLIB;
    }
}
