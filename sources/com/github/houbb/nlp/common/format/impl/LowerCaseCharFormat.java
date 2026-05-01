package com.github.houbb.nlp.common.format.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.nlp.common.format.ICharFormat;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class LowerCaseCharFormat implements ICharFormat {
    @Override // com.github.houbb.nlp.common.format.ICharFormat
    public char format(char c) {
        return Character.toLowerCase(c);
    }
}
