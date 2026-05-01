package com.github.houbb.opencc4j.support.datamap.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.opencc4j.support.data.impl.DataUtil;
import com.github.houbb.opencc4j.support.data.impl.p012jp.JpSTCharSelfData;
import com.github.houbb.opencc4j.support.data.impl.p012jp.JpTSCharReverseSelfData;
import com.github.houbb.opencc4j.support.data.impl.p012jp.JpTSShinjitaiCharSelfData;
import com.github.houbb.opencc4j.support.data.impl.p012jp.JpTSShinjitaiPhrasesSelfData;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class DataMapJpSelf extends AbstractDataMap {
    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> tsPhrase() {
        return new JpTSShinjitaiPhrasesSelfData().data().getDataMap();
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> tsChar() {
        return DataUtil.merge(new JpTSShinjitaiCharSelfData().data().getDataMap(), new JpTSCharReverseSelfData().data().getDataMap());
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> stPhrase() {
        return Collections.emptyMap();
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public Map<String, List<String>> stChar() {
        return new JpSTCharSelfData().data().getDataMap();
    }
}
