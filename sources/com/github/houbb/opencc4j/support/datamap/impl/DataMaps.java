package com.github.houbb.opencc4j.support.datamap.impl;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.opencc4j.support.datamap.IDataMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public final class DataMaps {
    private DataMaps() {
    }

    public static IDataMap defaults() {
        return (IDataMap) Instances.singleton(DataMapDefault.class);
    }

    public static IDataMap taiwan() {
        return (IDataMap) Instances.singleton(DataMapTaiwan.class);
    }

    public static IDataMap taiwanSelf() {
        return new DataMapTaiwanSelf();
    }

    public static IDataMap hongKongSelf() {
        return new DataMapHkSelf();
    }

    public static IDataMap hongKong() {
        return chains(defaults(), hongKongSelf());
    }

    public static IDataMap japan() {
        return chains(defaults(), japanSelf());
    }

    public static IDataMap japanSelf() {
        return new DataMapJpSelf();
    }

    public static IDataMap chains(IDataMap iDataMap, IDataMap... iDataMapArr) {
        return new DataMapChains(buildDataMapList(iDataMap, iDataMapArr));
    }

    public static IDataMap merge(IDataMap iDataMap, IDataMap... iDataMapArr) {
        return new DataMapMerge(buildDataMapList(iDataMap, iDataMapArr));
    }

    public static IDataMap chainsAndMerge(IDataMap iDataMap, IDataMap... iDataMapArr) {
        return new DataMapChainsAndMerge(buildDataMapList(iDataMap, iDataMapArr));
    }

    private static List<IDataMap> buildDataMapList(IDataMap iDataMap, IDataMap... iDataMapArr) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(iDataMap);
        if (ArrayUtil.isNotEmpty(iDataMapArr)) {
            arrayList.addAll(Arrays.asList(iDataMapArr));
        }
        return arrayList;
    }
}
