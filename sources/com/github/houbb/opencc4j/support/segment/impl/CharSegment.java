package com.github.houbb.opencc4j.support.segment.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.opencc4j.util.InnerCharUtils;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class CharSegment extends AbstractSegment {
    @Override // com.github.houbb.opencc4j.support.segment.impl.AbstractSegment
    protected List<String> doSeg(String str) {
        return InnerCharUtils.toCharList(str);
    }
}
