package com.github.houbb.opencc4j.support.match.impl;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.opencc4j.core.ZhConvertCoreContext;
import com.github.houbb.opencc4j.support.match.ZhMatch;
import com.github.houbb.opencc4j.util.InnerCharUtils;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AbstractZhMatch implements ZhMatch {
    protected abstract boolean doMatch(String str, List<String> list, ZhConvertCoreContext zhConvertCoreContext);

    @Override // com.github.houbb.opencc4j.support.match.ZhMatch
    public boolean match(String str, ZhConvertCoreContext zhConvertCoreContext) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        return doMatch(str, zhConvertCoreContext.zhChars().chars(str), zhConvertCoreContext);
    }

    protected boolean isChinese(String str, ZhConvertCoreContext zhConvertCoreContext) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        return InnerCharUtils.isChineseForSingle(str);
    }

    protected boolean isTraditional(String str, ZhConvertCoreContext zhConvertCoreContext) {
        return isChinese(str, zhConvertCoreContext) && zhConvertCoreContext.dataMap().tChars().contains(str);
    }

    protected boolean isSimple(String str, ZhConvertCoreContext zhConvertCoreContext) {
        return isChinese(str, zhConvertCoreContext) && zhConvertCoreContext.dataMap().sChars().contains(str);
    }
}
