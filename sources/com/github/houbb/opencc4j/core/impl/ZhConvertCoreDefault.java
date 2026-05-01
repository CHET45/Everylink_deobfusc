package com.github.houbb.opencc4j.core.impl;

import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.CharUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.opencc4j.core.ZhConvertCore;
import com.github.houbb.opencc4j.core.ZhConvertCoreContext;
import com.github.houbb.opencc4j.support.convert.context.impl.DefaultUnitConvertContext;
import com.github.houbb.opencc4j.support.convert.core.UnitConvert;
import com.github.houbb.opencc4j.support.datamap.IDataMap;
import com.github.houbb.opencc4j.util.InnerCharUtils;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class ZhConvertCoreDefault implements ZhConvertCore {
    @Override // com.github.houbb.opencc4j.core.ZhConvertCore
    public String toSimple(String str, ZhConvertCoreContext zhConvertCoreContext) {
        IDataMap iDataMapDataMap = zhConvertCoreContext.dataMap();
        return convert(str, zhConvertCoreContext, iDataMapDataMap.tsPhrase(), iDataMapDataMap.tsChar());
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvertCore
    public String toTraditional(String str, ZhConvertCoreContext zhConvertCoreContext) {
        IDataMap iDataMapDataMap = zhConvertCoreContext.dataMap();
        return convert(str, zhConvertCoreContext, iDataMapDataMap.stPhrase(), iDataMapDataMap.stChar());
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvertCore
    public List<String> simpleList(String str, ZhConvertCoreContext zhConvertCoreContext) {
        List<String> listNewArrayList = Guavas.newArrayList();
        for (String str2 : zhConvertCoreContext.segment().seg(str)) {
            if (isSimple(str2, zhConvertCoreContext)) {
                listNewArrayList.add(str2);
            }
        }
        return listNewArrayList;
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvertCore
    public List<String> traditionalList(String str, ZhConvertCoreContext zhConvertCoreContext) {
        List<String> listNewArrayList = Guavas.newArrayList();
        for (String str2 : zhConvertCoreContext.segment().seg(str)) {
            if (isTraditional(str2, zhConvertCoreContext)) {
                listNewArrayList.add(str2);
            }
        }
        return listNewArrayList;
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvertCore
    public boolean isSimple(char c, ZhConvertCoreContext zhConvertCoreContext) {
        return isSimple(String.valueOf(c), zhConvertCoreContext);
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvertCore
    public boolean isSimple(String str, ZhConvertCoreContext zhConvertCoreContext) {
        return zhConvertCoreContext.isSimpleMatch().match(str, zhConvertCoreContext);
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvertCore
    public boolean containsSimple(String str, ZhConvertCoreContext zhConvertCoreContext) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        Iterator<String> it = zhConvertCoreContext.zhChars().chars(str).iterator();
        while (it.hasNext()) {
            if (isSimple(it.next(), zhConvertCoreContext)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvertCore
    public boolean isTraditional(char c, ZhConvertCoreContext zhConvertCoreContext) {
        return isTraditional(String.valueOf(c), zhConvertCoreContext);
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvertCore
    public boolean isTraditional(String str, ZhConvertCoreContext zhConvertCoreContext) {
        return zhConvertCoreContext.isTraditionalMatch().match(str, zhConvertCoreContext);
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvertCore
    public boolean containsTraditional(String str, ZhConvertCoreContext zhConvertCoreContext) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        Iterator<String> it = zhConvertCoreContext.zhChars().chars(str).iterator();
        while (it.hasNext()) {
            if (isTraditional(it.next(), zhConvertCoreContext)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvertCore
    public boolean isChinese(char c, ZhConvertCoreContext zhConvertCoreContext) {
        return CharUtil.isChinese(c);
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvertCore
    public boolean isChinese(String str, ZhConvertCoreContext zhConvertCoreContext) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        Iterator<String> it = zhConvertCoreContext.zhChars().chars(str).iterator();
        while (it.hasNext()) {
            if (!InnerCharUtils.isChineseForSingle(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvertCore
    public boolean containsChinese(String str, ZhConvertCoreContext zhConvertCoreContext) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        Iterator<String> it = zhConvertCoreContext.zhChars().chars(str).iterator();
        while (it.hasNext()) {
            if (isChinese(it.next(), zhConvertCoreContext)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvertCore
    public List<String> toSimple(char c, ZhConvertCoreContext zhConvertCoreContext) {
        return zhConvertCoreContext.dataMap().tsChar().get(String.valueOf(c));
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvertCore
    public List<String> toTraditional(char c, ZhConvertCoreContext zhConvertCoreContext) {
        return zhConvertCoreContext.dataMap().stChar().get(String.valueOf(c));
    }

    protected String convert(String str, ZhConvertCoreContext zhConvertCoreContext, Map<String, List<String>> map, Map<String, List<String>> map2) {
        if (StringUtil.isEmpty(str)) {
            return str;
        }
        List<String> listSeg = zhConvertCoreContext.segment().seg(str);
        if (CollectionUtil.isEmpty(listSeg)) {
            return str;
        }
        UnitConvert unitConvert = zhConvertCoreContext.unitConvert();
        DefaultUnitConvertContext defaultUnitConvertContext = new DefaultUnitConvertContext();
        defaultUnitConvertContext.setPhraseData(map);
        defaultUnitConvertContext.setCharData(map2);
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = listSeg.iterator();
        while (it.hasNext()) {
            defaultUnitConvertContext.setUnit(it.next());
            sb.append(unitConvert.convert(defaultUnitConvertContext));
        }
        return sb.toString();
    }
}
