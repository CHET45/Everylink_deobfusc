package com.github.houbb.opencc4j.support.segment.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.nlp.common.segment.ICommonSegment;
import com.github.houbb.nlp.common.segment.impl.CommonSegments;
import com.github.houbb.opencc4j.support.segment.trie.TwOpenccTrieTreeMap;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class TwFastForwardSegment extends AbstractSegment {
    private static final ICommonSegment SEGMENT = CommonSegments.fastForward(new TwOpenccTrieTreeMap());

    @Override // com.github.houbb.opencc4j.support.segment.impl.AbstractSegment
    protected List<String> doSeg(String str) {
        return SEGMENT.segment(str);
    }
}
