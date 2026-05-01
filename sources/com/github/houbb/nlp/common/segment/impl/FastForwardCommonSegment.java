package com.github.houbb.nlp.common.segment.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.nlp.common.constant.NlpConst;
import com.github.houbb.nlp.common.dfa.tree.ITrieTreeMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class FastForwardCommonSegment extends SimpleCommonSegment {
    private final ITrieTreeMap trieTreeMap;

    public FastForwardCommonSegment(ITrieTreeMap iTrieTreeMap) {
        this.trieTreeMap = iTrieTreeMap;
    }

    @Override // com.github.houbb.nlp.common.segment.impl.SimpleCommonSegment
    protected List<String> getChineseSegments(String str) {
        List<String> listNewArrayList = Guavas.newArrayList();
        int i = 0;
        while (i < str.length()) {
            int wordMatchLength = getWordMatchLength(str, i);
            if (wordMatchLength > 0) {
                listNewArrayList.add(str.substring(i, i + wordMatchLength));
                i += wordMatchLength - 1;
            } else {
                listNewArrayList.add(String.valueOf(str.charAt(i)));
            }
            i++;
        }
        return listNewArrayList;
    }

    private int getWordMatchLength(String str, int i) {
        Map trieTreeMap = this.trieTreeMap.getTrieTreeMap();
        int i2 = 0;
        int i3 = 0;
        while (i < str.length()) {
            trieTreeMap = getCurrentMap(trieTreeMap, str, i);
            if (!ObjectUtil.isNotNull(trieTreeMap)) {
                break;
            }
            i3++;
            if (isEnd(trieTreeMap)) {
                i2 = i3;
            }
            i++;
        }
        return i2;
    }

    private Map getCurrentMap(Map map, String str, int i) {
        return (Map) map.get(Character.valueOf(str.charAt(i)));
    }

    private static boolean isEnd(Map map) {
        if (ObjectUtil.isNull(map)) {
            return false;
        }
        Object obj = map.get(NlpConst.IS_END);
        if (ObjectUtil.isNull(obj)) {
            return false;
        }
        return ((Boolean) obj).booleanValue();
    }
}
