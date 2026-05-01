package com.github.houbb.opencc4j.support.datamap.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.opencc4j.support.data.impl.OpenccDatas;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class DataMapDefault extends AbstractDataMap {
    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> tsPhrase() {
        return OpenccDatas.tsPhrase().data().getDataMap();
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> tsChar() {
        return OpenccDatas.tsChar().data().getDataMap();
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> stPhrase() {
        return OpenccDatas.stPhrase().data().getDataMap();
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> stChar() {
        return OpenccDatas.stChar().data().getDataMap();
    }
}
