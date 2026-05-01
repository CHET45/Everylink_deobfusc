package com.github.houbb.heaven.util.lang;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/* JADX INFO: loaded from: classes3.dex */
public final class SpiUtil {
    private SpiUtil() {
    }

    public static <T> List<T> getClassImplList(Class<T> cls) {
        return getClassImplList(cls, Thread.currentThread().getContextClassLoader());
    }

    public static <T> List<T> getClassImplList(Class<T> cls, ClassLoader classLoader) {
        ArrayList arrayList = new ArrayList();
        Iterator it = ServiceLoader.load(cls, classLoader).iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        return arrayList;
    }
}
