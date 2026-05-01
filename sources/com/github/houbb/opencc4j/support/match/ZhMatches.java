package com.github.houbb.opencc4j.support.match;

import com.github.houbb.opencc4j.support.match.impl.SimpleZhMatchAll;
import com.github.houbb.opencc4j.support.match.impl.SimpleZhMatchAny;
import com.github.houbb.opencc4j.support.match.impl.SimpleZhMatchOverHalf;
import com.github.houbb.opencc4j.support.match.impl.TraditionalZhMatchAll;
import com.github.houbb.opencc4j.support.match.impl.TraditionalZhMatchAny;
import com.github.houbb.opencc4j.support.match.impl.TraditionalZhMatchOverHalf;

/* JADX INFO: loaded from: classes3.dex */
public final class ZhMatches {
    public static ZhMatch simpleAny() {
        return new SimpleZhMatchAny();
    }

    public static ZhMatch simpleAll() {
        return new SimpleZhMatchAll();
    }

    public static ZhMatch simpleOverHalf() {
        return new SimpleZhMatchOverHalf();
    }

    public static ZhMatch traditionalAny() {
        return new TraditionalZhMatchAny();
    }

    public static ZhMatch traditionalAll() {
        return new TraditionalZhMatchAll();
    }

    public static ZhMatch traditionalOverHalf() {
        return new TraditionalZhMatchOverHalf();
    }
}
