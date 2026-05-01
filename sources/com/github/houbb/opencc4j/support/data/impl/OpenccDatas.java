package com.github.houbb.opencc4j.support.data.impl;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.opencc4j.support.data.Data;

/* JADX INFO: loaded from: classes3.dex */
public final class OpenccDatas {
    private OpenccDatas() {
    }

    public static Data stChar() {
        return (Data) Instances.singleton(STCharData.class);
    }

    public static Data tsChar() {
        return (Data) Instances.singleton(TSCharData.class);
    }

    public static Data tsPhrase() {
        return (Data) Instances.singleton(TSPhraseData.class);
    }

    public static Data stPhrase() {
        return (Data) Instances.singleton(STPhraseData.class);
    }

    public static Data twStChar() {
        return (Data) Instances.singleton(TwSTCharData.class);
    }

    public static Data twTsChar() {
        return (Data) Instances.singleton(TwTSCharData.class);
    }

    public static Data twTsPhrase() {
        return (Data) Instances.singleton(TwTSPhraseData.class);
    }

    public static Data twStPhrase() {
        return (Data) Instances.singleton(TwSTPhraseData.class);
    }
}
