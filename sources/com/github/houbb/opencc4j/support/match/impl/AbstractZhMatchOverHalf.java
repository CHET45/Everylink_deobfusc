package com.github.houbb.opencc4j.support.match.impl;

import com.github.houbb.opencc4j.core.ZhConvertCoreContext;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AbstractZhMatchOverHalf extends AbstractZhMatch {
    protected abstract boolean matchCondition(String str, String str2, ZhConvertCoreContext zhConvertCoreContext);

    @Override // com.github.houbb.opencc4j.support.match.impl.AbstractZhMatch
    protected boolean doMatch(String str, List<String> list, ZhConvertCoreContext zhConvertCoreContext) {
        Iterator<String> it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (matchCondition(str, it.next(), zhConvertCoreContext)) {
                i++;
            }
        }
        return i * 2 > list.size();
    }
}
