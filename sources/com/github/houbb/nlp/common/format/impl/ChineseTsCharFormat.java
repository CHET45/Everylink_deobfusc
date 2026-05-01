package com.github.houbb.nlp.common.format.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.p010io.StreamUtil;
import com.github.houbb.nlp.common.constant.NlpConst;
import com.github.houbb.nlp.common.format.ICharFormat;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class ChineseTsCharFormat implements ICharFormat {
    private static final Map<Character, Character> TS_CHAR_MAP = Guavas.newHashMap();

    static {
        long jCurrentTimeMillis = System.currentTimeMillis();
        Iterator<String> it = StreamUtil.readAllLines(NlpConst.NLP_CHINESE_TS_CHAR_PATH).iterator();
        while (it.hasNext()) {
            String[] strArrSplit = it.next().split(" ");
            TS_CHAR_MAP.put(Character.valueOf(strArrSplit[0].charAt(0)), Character.valueOf(strArrSplit[1].charAt(0)));
        }
        System.out.println("[NLP Format] chinese traditional-simple dict load finished, cost " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
    }

    @Override // com.github.houbb.nlp.common.format.ICharFormat
    public char format(char c) {
        Character ch = TS_CHAR_MAP.get(Character.valueOf(c));
        return ObjectUtil.isNull(ch) ? c : ch.charValue();
    }
}
