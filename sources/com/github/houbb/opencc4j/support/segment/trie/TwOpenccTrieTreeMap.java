package com.github.houbb.opencc4j.support.segment.trie;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.nlp.common.dfa.tree.impl.AbstractTrieTreeMap;
import com.github.houbb.opencc4j.support.data.impl.OpenccDatas;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class TwOpenccTrieTreeMap extends AbstractTrieTreeMap {
    private static volatile Map innerWordMap = Guavas.newHashMap();

    @Override // com.github.houbb.nlp.common.dfa.tree.impl.AbstractTrieTreeMap
    protected Map getStaticVolatileMap() {
        return innerWordMap;
    }

    @Override // com.github.houbb.nlp.common.dfa.tree.impl.AbstractTrieTreeMap
    protected Collection<String> getWordCollection() {
        Set setNewHashSet = Guavas.newHashSet();
        setNewHashSet.addAll(OpenccDatas.twStPhrase().data().getDataMap().keySet());
        setNewHashSet.addAll(OpenccDatas.twTsPhrase().data().getDataMap().keySet());
        return setNewHashSet;
    }
}
