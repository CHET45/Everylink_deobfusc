package com.github.houbb.nlp.common.dfa.tree.impl;

import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.MapUtil;
import com.github.houbb.nlp.common.constant.NlpConst;
import com.github.houbb.nlp.common.dfa.tree.ITrieTreeMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AbstractTrieTreeMap implements ITrieTreeMap {
    protected abstract Map getStaticVolatileMap();

    protected abstract Collection<String> getWordCollection();

    @Override // com.github.houbb.nlp.common.dfa.tree.ITrieTreeMap
    public Map getTrieTreeMap() {
        Map staticVolatileMap = getStaticVolatileMap();
        if (MapUtil.isNotEmpty(staticVolatileMap)) {
            return staticVolatileMap;
        }
        synchronized (AbstractTrieTreeMap.class) {
            if (MapUtil.isEmpty(staticVolatileMap)) {
                Collection<String> wordCollection = getWordCollection();
                Set setNewHashSet = Guavas.newHashSet();
                for (String str : wordCollection) {
                    if (!StringUtil.isEmpty(str)) {
                        setNewHashSet.add(str.split(" ")[0]);
                    }
                }
                initInnerWordMap(setNewHashSet, staticVolatileMap);
            }
        }
        return staticVolatileMap;
    }

    @Override // com.github.houbb.nlp.common.dfa.tree.ITrieTreeMap
    public ITrieTreeMap add(String str) {
        Map staticVolatileMap = getStaticVolatileMap();
        if (MapUtil.isEmpty(staticVolatileMap)) {
            getTrieTreeMap();
        }
        synchronized (DefaultTrieTreeMap.class) {
            initInnerWordMap(str, staticVolatileMap);
        }
        return this;
    }

    @Override // com.github.houbb.nlp.common.dfa.tree.ITrieTreeMap
    public ITrieTreeMap clear() {
        Map staticVolatileMap = getStaticVolatileMap();
        if (ObjectUtil.isNotNull(staticVolatileMap)) {
            staticVolatileMap.clear();
        }
        return this;
    }

    private void initInnerWordMap(Collection<String> collection, Map map) {
        if (CollectionUtil.isEmpty(collection)) {
            return;
        }
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            initInnerWordMap(it.next(), map);
        }
    }

    private void initInnerWordMap(String str, Map map) {
        Map map2;
        if (StringUtil.isEmpty(str)) {
            return;
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        for (int i = 0; i < length; i++) {
            char c = charArray[i];
            Object obj = map.get(Character.valueOf(c));
            if (ObjectUtil.isNotNull(obj)) {
                map2 = (Map) obj;
            } else {
                map2 = new HashMap(8);
                map2.put(NlpConst.IS_END, false);
                map.put(Character.valueOf(c), map2);
            }
            map = map2;
            if (i == length - 1) {
                map.put(NlpConst.IS_END, true);
            }
        }
    }
}
