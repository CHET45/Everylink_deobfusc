package com.github.houbb.opencc4j.support.datamap.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.opencc4j.support.data.impl.OpenccDatas;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class DataMapTaiwan extends AbstractDataMap {
    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> tsPhrase() {
        return OpenccDatas.twTsPhrase().data().getDataMap();
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> tsChar() {
        return OpenccDatas.twTsChar().data().getDataMap();
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> stPhrase() {
        return OpenccDatas.twStPhrase().data().getDataMap();
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> stChar() {
        return OpenccDatas.twStChar().data().getDataMap();
    }
}
