package com.github.houbb.opencc4j.support.datamap.impl;

import com.github.houbb.opencc4j.support.data.impl.DataUtil;
import com.github.houbb.opencc4j.support.datamap.IDataMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class DataMapMerge extends AbstractDataMap {
    private final List<IDataMap> dataMaps;

    public DataMapMerge(List<IDataMap> list) {
        this.dataMaps = list;
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> tsPhrase() {
        ArrayList arrayList = new ArrayList();
        Iterator<IDataMap> it = this.dataMaps.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().tsPhrase());
        }
        return DataUtil.merge(arrayList);
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> tsChar() {
        ArrayList arrayList = new ArrayList();
        Iterator<IDataMap> it = this.dataMaps.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().tsChar());
        }
        return DataUtil.merge(arrayList);
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> stPhrase() {
        ArrayList arrayList = new ArrayList();
        Iterator<IDataMap> it = this.dataMaps.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().stPhrase());
        }
        return DataUtil.merge(arrayList);
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> stChar() {
        ArrayList arrayList = new ArrayList();
        Iterator<IDataMap> it = this.dataMaps.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().stChar());
        }
        return DataUtil.merge(arrayList);
    }
}
