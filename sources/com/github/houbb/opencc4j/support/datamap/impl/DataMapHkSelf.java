package com.github.houbb.opencc4j.support.datamap.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.opencc4j.support.data.impl.p011hk.HkSTCharSelfData;
import com.github.houbb.opencc4j.support.data.impl.p011hk.HkSTPhraseSelfData;
import com.github.houbb.opencc4j.support.data.impl.p011hk.HkTSCharSelfData;
import com.github.houbb.opencc4j.support.data.impl.p011hk.HkTSPhraseSelfData;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class DataMapHkSelf extends AbstractDataMap {
    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> tsPhrase() {
        return new HkTSPhraseSelfData().data().getDataMap();
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> tsChar() {
        return new HkTSCharSelfData().data().getDataMap();
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> stPhrase() {
        return new HkSTPhraseSelfData().data().getDataMap();
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> stChar() {
        return new HkSTCharSelfData().data().getDataMap();
    }
}
