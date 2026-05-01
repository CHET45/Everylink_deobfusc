package com.github.houbb.opencc4j.support.segment.trie;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.nlp.common.dfa.tree.impl.AbstractTrieTreeMap;
import com.github.houbb.opencc4j.support.datamap.IDataMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class DataMapTrieTreeMap extends AbstractTrieTreeMap {
    private static volatile Map innerWordMap = Guavas.newHashMap();
    private final IDataMap dataMap;

    public DataMapTrieTreeMap(IDataMap iDataMap) {
        this.dataMap = iDataMap;
    }

    @Override // com.github.houbb.nlp.common.dfa.tree.impl.AbstractTrieTreeMap
    protected Map getStaticVolatileMap() {
        return innerWordMap;
    }

    @Override // com.github.houbb.nlp.common.dfa.tree.impl.AbstractTrieTreeMap
    protected Collection<String> getWordCollection() {
        Set setNewHashSet = Guavas.newHashSet();
        setNewHashSet.addAll(this.dataMap.stPhrase().keySet());
        setNewHashSet.addAll(this.dataMap.tsPhrase().keySet());
        return setNewHashSet;
    }
}
