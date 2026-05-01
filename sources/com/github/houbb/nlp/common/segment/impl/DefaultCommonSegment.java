package com.github.houbb.nlp.common.segment.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class DefaultCommonSegment extends SimpleCommonSegment {
    @Override // com.github.houbb.nlp.common.segment.impl.SimpleCommonSegment
    protected List<String> getChineseSegments(String str) {
        return Collections.singletonList(str);
    }
}
