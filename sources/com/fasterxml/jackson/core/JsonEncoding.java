package com.fasterxml.jackson.core;

import com.azure.xml.implementation.aalto.util.CharsetNames;

/* JADX INFO: loaded from: classes3.dex */
public enum JsonEncoding {
    UTF8("UTF-8", false, 8),
    UTF16_BE(CharsetNames.CS_UTF16BE, true, 16),
    UTF16_LE(CharsetNames.CS_UTF16LE, false, 16),
    UTF32_BE(CharsetNames.CS_UTF32BE, true, 32),
    UTF32_LE(CharsetNames.CS_UTF32LE, false, 32);

    private final boolean _bigEndian;
    private final int _bits;
    private final String _javaName;

    JsonEncoding(String str, boolean z, int i) {
        this._javaName = str;
        this._bigEndian = z;
        this._bits = i;
    }

    public String getJavaName() {
        return this._javaName;
    }

    public boolean isBigEndian() {
        return this._bigEndian;
    }

    public int bits() {
        return this._bits;
    }
}
