package com.github.houbb.opencc4j.util;

import com.github.houbb.opencc4j.support.collection.Char2CharMap;
import com.github.houbb.opencc4j.support.data.impl.TSCharData;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public final class ZhSlimUtil {
    private static final Char2CharMap char2CharMap;

    private ZhSlimUtil() {
    }

    static {
        Map<String, List<String>> dataMap = new TSCharData().data().getDataMap();
        char2CharMap = new Char2CharMap(dataMap.size());
        for (Map.Entry<String, List<String>> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            char[] charArray = key.toCharArray();
            char[] charArray2 = value.get(0).toCharArray();
            if (charArray.length <= 1 && charArray2.length <= 1) {
                char2CharMap.put(charArray[0], charArray2[0]);
            }
        }
    }

    public static char toSimple(char c) {
        return char2CharMap.get(c, c);
    }
}
