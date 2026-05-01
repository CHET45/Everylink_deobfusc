package com.github.houbb.opencc4j.support.convert.core.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.opencc4j.support.convert.context.UnitConvertContext;
import com.github.houbb.opencc4j.support.convert.core.UnitConvert;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class DefaultUnitConvert implements UnitConvert {
    @Override // com.github.houbb.opencc4j.support.convert.core.UnitConvert
    public String convert(UnitConvertContext unitConvertContext) {
        return getPhraseResult(unitConvertContext.getUnit(), unitConvertContext.getPhraseData(), unitConvertContext.getCharData());
    }

    private String getPhraseResult(String str, Map<String, List<String>> map, Map<String, List<String>> map2) {
        List<String> list = map.get(str);
        if (CollectionUtil.isNotEmpty(list)) {
            return list.get(0);
        }
        char[] charArray = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : charArray) {
            String string = Character.toString(c);
            String charResult = getCharResult(string, map2);
            if (charResult.length() == 1) {
                sb.append(charResult);
            } else {
                sb.append(string);
            }
        }
        return sb.toString();
    }

    private String getCharResult(String str, Map<String, List<String>> map) {
        List<String> list = map.get(str);
        return CollectionUtil.isNotEmpty(list) ? list.get(0) : str;
    }
}
