package com.aivox.base.util;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class MapUtil {
    public static <K, V> V getOrDefault(Map<K, V> map, K k, V v) {
        if (map == null) {
            return null;
        }
        V v2 = map.get(k);
        return (v2 != null || map.containsKey(k)) ? v2 : v;
    }

    public static <K, V> V putIfAbsent(Map<K, V> map, K k, V v) {
        if (map == null) {
            return null;
        }
        V v2 = map.get(k);
        return v2 == null ? map.put(k, v) : v2;
    }
}
