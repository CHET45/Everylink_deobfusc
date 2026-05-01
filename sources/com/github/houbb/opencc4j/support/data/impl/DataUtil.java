package com.github.houbb.opencc4j.support.data.impl;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.opencc4j.constant.AppConstant;
import com.github.houbb.opencc4j.exception.Opencc4jRuntimeException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public final class DataUtil {
    private DataUtil() {
    }

    public static Map<String, List<String>> buildDataMap(String str) {
        try {
            InputStream resourceAsStream = DataUtil.class.getResourceAsStream(str);
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream, Charset.forName("UTF-8")));
                try {
                    HashMap map = new HashMap();
                    while (bufferedReader.ready()) {
                        String line = bufferedReader.readLine();
                        if (!StringUtil.isEmpty(line)) {
                            String[] strArrSplitByAnyBlank = StringUtil.splitByAnyBlank(line);
                            List<String> listBuildValueList = buildValueList(strArrSplitByAnyBlank);
                            if (CollectionUtil.isNotEmpty(listBuildValueList)) {
                                map.put(strArrSplitByAnyBlank[0], listBuildValueList);
                            }
                        }
                    }
                    bufferedReader.close();
                    if (resourceAsStream != null) {
                        resourceAsStream.close();
                    }
                    return map;
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            throw new Opencc4jRuntimeException("Dict 数据加载失败!", e);
        }
    }

    private static List<String> buildValueList(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        for (int i = 1; i < strArr.length; i++) {
            String str = strArr[i];
            if (!AppConstant.EMPTY_RESULT.equals(str)) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    public static Map<String, List<String>> buildDataMapReverse(String str) {
        try {
            InputStream resourceAsStream = DataUtil.class.getResourceAsStream(str);
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream, Charset.forName("UTF-8")));
                try {
                    HashMap map = new HashMap();
                    while (bufferedReader.ready()) {
                        String line = bufferedReader.readLine();
                        if (!StringUtil.isEmpty(line)) {
                            String[] strArrSplitByAnyBlank = StringUtil.splitByAnyBlank(line);
                            List<String> listBuildValueListReverse = buildValueListReverse(strArrSplitByAnyBlank);
                            if (CollectionUtil.isNotEmpty(listBuildValueListReverse)) {
                                map.put(strArrSplitByAnyBlank[strArrSplitByAnyBlank.length - 1], listBuildValueListReverse);
                            }
                        }
                    }
                    bufferedReader.close();
                    if (resourceAsStream != null) {
                        resourceAsStream.close();
                    }
                    return map;
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            throw new Opencc4jRuntimeException("Dict 数据加载失败!", e);
        }
    }

    private static List<String> buildValueListReverse(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(strArr[0]);
        return arrayList;
    }

    public static Map<String, List<String>> merge(Map<String, List<String>> map, Map<String, List<String>>... mapArr) {
        if (ArrayUtil.isEmpty(mapArr)) {
            return map;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(map);
        arrayList.addAll(Arrays.asList(mapArr));
        return merge(arrayList);
    }

    public static Map<String, List<String>> merge(List<Map<String, List<String>>> list) {
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        HashMap map = new HashMap();
        Iterator<Map<String, List<String>>> it = list.iterator();
        while (it.hasNext()) {
            map.putAll(it.next());
        }
        return map;
    }

    public static Map<String, List<String>> chains(Map<String, List<String>> map, Map<String, List<String>>... mapArr) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(map);
        if (ArrayUtil.isNotEmpty(mapArr)) {
            arrayList.addAll(Arrays.asList(mapArr));
        }
        return chains(arrayList);
    }

    public static Map<String, List<String>> chains(Collection<Map<String, List<String>>> collection) {
        if (collection == null || collection.isEmpty()) {
            return new HashMap();
        }
        Iterator<Map<String, List<String>>> it = collection.iterator();
        Map<String, List<String>> next = it.next();
        while (it.hasNext()) {
            Map<String, List<String>> next2 = it.next();
            HashMap map = new HashMap();
            for (Map.Entry<String, List<String>> entry : next.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                for (int i = 0; i < value.size(); i++) {
                    String str = value.get(i);
                    List<String> list = next2.get(str);
                    if (list != null && !list.isEmpty()) {
                        linkedHashSet.addAll(list);
                    } else {
                        linkedHashSet.add(str);
                    }
                }
                map.put(key, new ArrayList(linkedHashSet));
            }
            next = map;
        }
        return next;
    }

    public static Map<String, List<String>> chainsAndMerge(List<Map<String, List<String>>> list) {
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        Map<String, List<String>> mapChains = chains(list);
        ArrayList arrayList = new ArrayList();
        arrayList.add(mapChains);
        for (int i = 1; i < list.size(); i++) {
            arrayList.add(list.get(i));
        }
        return merge(arrayList);
    }
}
