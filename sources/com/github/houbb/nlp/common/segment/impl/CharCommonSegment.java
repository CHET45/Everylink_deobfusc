package com.github.houbb.nlp.common.segment.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.lang.StringUtil;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class CharCommonSegment extends AbstractCommonSegment {
    @Override // com.github.houbb.nlp.common.segment.impl.AbstractCommonSegment
    protected List<String> doSegment(String str) {
        return StringUtil.toCharStringList(str);
    }
}
