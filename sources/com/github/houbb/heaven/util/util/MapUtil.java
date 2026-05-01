package com.github.houbb.heaven.util.util;

import com.github.houbb.heaven.support.handler.IHandler;
import com.github.houbb.heaven.support.handler.IMapEntryHandler;
import com.github.houbb.heaven.support.handler.IMapHandler;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public final class MapUtil {
    private MapUtil() {
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.size() == 0;
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static <K, V> Map<K, V> toMap(Collection<V> collection, IHandler<? super V, K> iHandler) {
        if (ObjectUtil.isNull(collection)) {
            return Collections.emptyMap();
        }
        HashMap map = new HashMap(collection.size());
        for (V v : collection) {
            map.put(iHandler.handle(v), v);
        }
        return map;
    }

    public static <K, V, O> Map<K, V> toMap(Collection<O> collection, IMapHandler<K, V, O> iMapHandler) {
        if (ObjectUtil.isNull(collection)) {
            return Collections.emptyMap();
        }
        HashMap map = new HashMap(collection.size());
        for (O o : collection) {
            map.put(iMapHandler.getKey(o), iMapHandler.getValue(o));
        }
        return map;
    }

    public static <K, V, T> List<T> toList(Map<K, V> map, IMapEntryHandler<K, V, T> iMapEntryHandler) {
        if (isEmpty(map)) {
            return Collections.emptyList();
        }
        List<T> listNewArrayList = Guavas.newArrayList(map.size());
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            listNewArrayList.add(iMapEntryHandler.handler(it.next()));
        }
        return listNewArrayList;
    }

    public static <V> Map<Integer, V> toIndexMap(Collection<V> collection) {
        if (ObjectUtil.isNull(collection)) {
            return Collections.emptyMap();
        }
        HashMap map = new HashMap(collection.size());
        Iterator<V> it = collection.iterator();
        while (it.hasNext()) {
            map.put(Integer.valueOf(map.size()), it.next());
        }
        return map;
    }

    public static String getMapValue(Map<String, String> map, String str) {
        if (isEmpty(map)) {
            return str;
        }
        String str2 = map.get(str);
        return StringUtil.isEmpty(str2) ? str : str2;
    }

    public static <K, V> V getMapValue(Map<K, V> map, K k, V v) {
        if (isEmpty(map)) {
            return v;
        }
        V v2 = map.get(k);
        return ObjectUtil.isNull(v2) ? v : v2;
    }

    public static Map.Entry<String, Object> getFirstEntry(Map<String, Object> map) {
        if (isEmpty(map)) {
            return null;
        }
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        if (it.hasNext()) {
            return it.next();
        }
        return null;
    }

    public static <T> void putToListMap(Map<String, List<T>> map, String str, T t) {
        List<T> arrayList = map.get(str);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        arrayList.add(t);
        map.put(str, arrayList);
    }

    public static <T> void putToSetMap(Map<String, Set<T>> map, String str, T t) {
        Set<T> hashSet = map.get(str);
        if (hashSet == null) {
            hashSet = new HashSet<>();
        }
        hashSet.add(t);
        map.put(str, hashSet);
    }
}
