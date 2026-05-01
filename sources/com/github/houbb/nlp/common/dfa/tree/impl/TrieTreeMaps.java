package com.github.houbb.nlp.common.dfa.tree.impl;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.nlp.common.dfa.tree.ITrieTreeMap;

/* JADX INFO: loaded from: classes3.dex */
public final class TrieTreeMaps {
    private TrieTreeMaps() {
    }

    public static ITrieTreeMap defaults() {
        return (ITrieTreeMap) Instances.singleton(DefaultTrieTreeMap.class);
    }
}
