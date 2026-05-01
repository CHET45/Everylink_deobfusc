package com.github.houbb.opencc4j.support.datamap.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.opencc4j.support.data.impl.p013tw.TwSTCharSelfData;
import com.github.houbb.opencc4j.support.data.impl.p013tw.TwSTPhraseSelfData;
import com.github.houbb.opencc4j.support.data.impl.p013tw.TwTSCharSelfData;
import com.github.houbb.opencc4j.support.data.impl.p013tw.TwTSPhraseSelfData;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class DataMapTaiwanSelf extends AbstractDataMap {
    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> tsPhrase() {
        return new TwTSPhraseSelfData().data().getDataMap();
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> tsChar() {
        return new TwTSCharSelfData().data().getDataMap();
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> stPhrase() {
        return new TwSTPhraseSelfData().data().getDataMap();
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> stChar() {
        return new TwSTCharSelfData().data().getDataMap();
    }
}
