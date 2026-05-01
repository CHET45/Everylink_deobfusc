package com.github.houbb.opencc4j.support.match.impl;

import com.github.houbb.opencc4j.core.ZhConvertCoreContext;

/* JADX INFO: loaded from: classes3.dex */
public class TraditionalZhMatchOverHalf extends AbstractZhMatchOverHalf {
    @Override // com.github.houbb.opencc4j.support.match.impl.AbstractZhMatchOverHalf
    protected boolean matchCondition(String str, String str2, ZhConvertCoreContext zhConvertCoreContext) {
        return super.isTraditional(str2, zhConvertCoreContext);
    }
}
