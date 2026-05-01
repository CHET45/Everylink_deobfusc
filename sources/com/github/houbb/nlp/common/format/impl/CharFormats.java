package com.github.houbb.nlp.common.format.impl;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.heaven.support.pipeline.Pipeline;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.nlp.common.format.ICharFormat;

/* JADX INFO: loaded from: classes3.dex */
public final class CharFormats {
    private CharFormats() {
    }

    public static ICharFormat defaults() {
        return chains(halfWidth(), lowerCase());
    }

    public static ICharFormat halfWidth() {
        return (ICharFormat) Instances.singleton(HalfWidthCharFormat.class);
    }

    public static ICharFormat lowerCase() {
        return (ICharFormat) Instances.singleton(LowerCaseCharFormat.class);
    }

    public static ICharFormat none() {
        return (ICharFormat) Instances.singleton(NoneCharFormat.class);
    }

    public static ICharFormat chineseSimple() {
        return (ICharFormat) Instances.singleton(ChineseTsCharFormat.class);
    }

    public static ICharFormat chains(final ICharFormat... iCharFormatArr) {
        if (ArrayUtil.isEmpty(iCharFormatArr)) {
            return none();
        }
        return new AbstractCharFormatInit() { // from class: com.github.houbb.nlp.common.format.impl.CharFormats.1
            @Override // com.github.houbb.nlp.common.format.impl.AbstractCharFormatInit
            protected void init(Pipeline<ICharFormat> pipeline) {
                for (ICharFormat iCharFormat : iCharFormatArr) {
                    pipeline.addLast(iCharFormat);
                }
            }
        };
    }
}
