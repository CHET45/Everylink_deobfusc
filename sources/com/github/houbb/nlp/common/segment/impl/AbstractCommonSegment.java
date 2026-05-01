package com.github.houbb.nlp.common.segment.impl;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.nlp.common.segment.ICommonSegment;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AbstractCommonSegment implements ICommonSegment {
    protected abstract List<String> doSegment(String str);

    @Override // com.github.houbb.nlp.common.segment.ICommonSegment
    public List<String> segment(String str) {
        if (str == null) {
            return Collections.emptyList();
        }
        if (StringUtil.isEmptyTrim(str)) {
            return Collections.singletonList(str);
        }
        return doSegment(str);
    }
}
