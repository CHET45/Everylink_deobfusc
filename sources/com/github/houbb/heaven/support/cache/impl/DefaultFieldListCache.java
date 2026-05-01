package com.github.houbb.heaven.support.cache.impl;

import com.github.houbb.heaven.annotation.NotThreadSafe;
import com.github.houbb.heaven.reflect.api.IField;
import com.github.houbb.heaven.reflect.util.Classes;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
@NotThreadSafe
public class DefaultFieldListCache extends AbstractCache<Class, List<IField>> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.github.houbb.heaven.support.cache.impl.AbstractCache
    public List<IField> buildValue(Class cls) {
        return Classes.getFields(cls);
    }
}
