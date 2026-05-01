package com.github.houbb.heaven.support.cache.impl;

import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import java.lang.reflect.Field;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class ClassFieldListCache extends AbstractCache<Class, List<Field>> {
    private static final ClassFieldListCache INSTANCE = new ClassFieldListCache();

    private ClassFieldListCache() {
    }

    public static ClassFieldListCache getInstance() {
        return INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.github.houbb.heaven.support.cache.impl.AbstractCache
    public List<Field> buildValue(Class cls) {
        return ClassUtil.getAllFieldList(cls);
    }
}
