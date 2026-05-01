package com.github.houbb.nlp.common.format.impl;

import com.github.houbb.heaven.util.lang.CharUtil;
import com.github.houbb.nlp.common.format.ICharFormat;

/* JADX INFO: loaded from: classes3.dex */
public class HalfWidthCharFormat implements ICharFormat {
    @Override // com.github.houbb.nlp.common.format.ICharFormat
    public char format(char c) {
        return CharUtil.toHalfWidth(c);
    }
}
