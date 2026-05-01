package com.github.houbb.opencc4j.support.segment.impl;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.opencc4j.support.segment.Segment;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AbstractSegment implements Segment {
    protected abstract List<String> doSeg(String str);

    @Override // com.github.houbb.opencc4j.support.segment.Segment
    public List<String> seg(String str) {
        if (StringUtil.isEmpty(str)) {
            return Collections.emptyList();
        }
        return doSeg(str);
    }
}
