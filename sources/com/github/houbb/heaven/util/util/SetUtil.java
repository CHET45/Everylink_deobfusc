package com.github.houbb.heaven.util.util;

import java.util.ArrayList;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public final class SetUtil {
    private SetUtil() {
    }

    public static <T> T getFirst(Set<T> set) {
        if (CollectionUtil.isEmpty(set)) {
            return null;
        }
        return (T) new ArrayList(set).get(0);
    }
}
