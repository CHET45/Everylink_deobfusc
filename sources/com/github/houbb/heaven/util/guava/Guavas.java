package com.github.houbb.heaven.util.guava;

import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes3.dex */
public final class Guavas {
    private Guavas() {
    }

    public static <E> List<E> newArrayList() {
        return new ArrayList();
    }

    public static <E> List<E> newArrayList(int i) {
        return new ArrayList(i);
    }

    public static <E> List<E> newArrayList(E... eArr) {
        if (ArrayUtil.isEmpty(eArr)) {
            return new ArrayList();
        }
        List<E> listNewArrayList = newArrayList(eArr.length);
        listNewArrayList.addAll(Arrays.asList(eArr));
        return listNewArrayList;
    }

    public static <E> List<E> newArrayList(Collection<E> collection) {
        if (CollectionUtil.isEmpty(collection)) {
            return new ArrayList();
        }
        List<E> listNewArrayList = newArrayList(collection.size());
        listNewArrayList.addAll(collection);
        return listNewArrayList;
    }

    public static <K, V> Map<K, V> newHashMap() {
        return new HashMap();
    }

    public static <K, V> Map<K, V> newHashMap(int i) {
        return new HashMap(i);
    }

    public static <K, V> Map<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap();
    }

    public static <K, V> Map<K, V> newLinkedHashMap() {
        return new LinkedHashMap();
    }

    public static <K, V> Map<K, V> newLinkedHashMap(int i) {
        return new LinkedHashMap(i);
    }

    public static <E> Set<E> newHashSet() {
        return new HashSet();
    }

    public static <E> Set<E> newHashSet(int i) {
        return new HashSet(i);
    }
}
