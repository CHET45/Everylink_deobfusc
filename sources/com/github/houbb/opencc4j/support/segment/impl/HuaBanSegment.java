package com.github.houbb.opencc4j.support.segment.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.support.instance.impl.Instances;
import com.huaban.analysis.jieba.JiebaSegmenter;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class HuaBanSegment extends AbstractSegment {
    @Override // com.github.houbb.opencc4j.support.segment.impl.AbstractSegment
    protected List<String> doSeg(String str) {
        return ((JiebaSegmenter) Instances.singleton(JiebaSegmenter.class)).sentenceProcess(str);
    }
}
