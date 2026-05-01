package com.github.houbb.heaven.support.reader.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.support.reader.IReader;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class StringReader implements IReader {
    private final String string;

    public StringReader(String str) {
        this.string = str;
    }

    @Override // com.github.houbb.heaven.support.reader.IReader
    public String read() {
        return this.string;
    }
}
