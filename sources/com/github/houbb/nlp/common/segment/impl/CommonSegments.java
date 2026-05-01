package com.github.houbb.nlp.common.segment.impl;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.nlp.common.dfa.tree.ITrieTreeMap;
import com.github.houbb.nlp.common.dfa.tree.impl.TrieTreeMaps;
import com.github.houbb.nlp.common.segment.ICommonSegment;

/* JADX INFO: loaded from: classes3.dex */
public final class CommonSegments {
    private CommonSegments() {
    }

    public static ICommonSegment single() {
        return (ICommonSegment) Instances.singleton(SingleCommonSegment.class);
    }

    public static ICommonSegment chars() {
        return (ICommonSegment) Instances.singleton(CharCommonSegment.class);
    }

    public static ICommonSegment simple() {
        return (ICommonSegment) Instances.singleton(SimpleCommonSegment.class);
    }

    public static ICommonSegment fastForward() {
        return fastForward(TrieTreeMaps.defaults());
    }

    public static ICommonSegment fastForward(ITrieTreeMap iTrieTreeMap) {
        return new FastForwardCommonSegment(iTrieTreeMap);
    }

    public static ICommonSegment defaults() {
        return new DefaultCommonSegment();
    }
}
