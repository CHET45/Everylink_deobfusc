package com.github.houbb.heaven.support.cache.impl;

import com.github.houbb.heaven.annotation.NotThreadSafe;
import com.github.houbb.heaven.reflect.api.IField;
import com.github.houbb.heaven.support.cache.ICache;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes3.dex */
@NotThreadSafe
public class DefaultFieldCache implements ICache<Class, IField> {
    private static final Map<Class, IField> MAP = new ConcurrentHashMap();

    @Override // com.github.houbb.heaven.support.cache.ICache
    public IField get(Class cls) {
        return MAP.get(cls);
    }

    @Override // com.github.houbb.heaven.support.cache.ICache
    public void set(Class cls, IField iField) {
        MAP.put(cls, iField);
    }
}
