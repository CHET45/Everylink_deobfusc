package com.github.houbb.nlp.common.dfa.tree.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.p010io.StreamUtil;
import com.github.houbb.nlp.common.constant.NlpConst;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class DefaultTrieTreeMap extends AbstractTrieTreeMap {
    private static volatile Map innerWordMap = Guavas.newHashMap();

    @Override // com.github.houbb.nlp.common.dfa.tree.impl.AbstractTrieTreeMap
    protected Map getStaticVolatileMap() {
        return innerWordMap;
    }

    @Override // com.github.houbb.nlp.common.dfa.tree.impl.AbstractTrieTreeMap
    protected Collection<String> getWordCollection() {
        List<String> allLines = StreamUtil.readAllLines(getDictPath());
        Set setNewHashSet = Guavas.newHashSet();
        for (String str : allLines) {
            if (!StringUtil.isEmpty(str)) {
                setNewHashSet.add(getLineWord(str));
            }
        }
        return setNewHashSet;
    }

    protected String getDictPath() {
        return NlpConst.NLP_WORD_FREQ_DICT_PATH;
    }

    protected String getLineWord(String str) {
        return str.split(" ")[0];
    }
}
