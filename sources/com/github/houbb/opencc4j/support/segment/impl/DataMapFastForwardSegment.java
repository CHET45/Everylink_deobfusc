package com.github.houbb.opencc4j.support.segment.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.nlp.common.segment.ICommonSegment;
import com.github.houbb.nlp.common.segment.impl.CommonSegments;
import com.github.houbb.opencc4j.support.datamap.IDataMap;
import com.github.houbb.opencc4j.support.segment.trie.DataMapTrieTreeMap;
import com.github.houbb.opencc4j.support.segment.trie.OpenccTrieTreeMap;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class DataMapFastForwardSegment extends AbstractSegment {
    private ICommonSegment SEGMENT;

    public DataMapFastForwardSegment(IDataMap iDataMap) {
        this.SEGMENT = CommonSegments.fastForward(new OpenccTrieTreeMap());
        this.SEGMENT = CommonSegments.fastForward(new DataMapTrieTreeMap(iDataMap));
    }

    @Override // com.github.houbb.opencc4j.support.segment.impl.AbstractSegment
    protected List<String> doSeg(String str) {
        return this.SEGMENT.segment(str);
    }
}
