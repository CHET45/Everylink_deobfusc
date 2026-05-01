package com.github.houbb.heaven.support.reader.impl;

import com.github.houbb.heaven.support.reader.IReader;
import java.io.File;

/* JADX INFO: loaded from: classes3.dex */
public final class Readers {
    private Readers() {
    }

    public static IReader string(String str) {
        return new StringReader(str);
    }

    public static IReader filePath(String str, String str2) {
        return new FilePathReader(str, str2);
    }

    public static IReader file(File file, String str) {
        return new FileReader(file, str);
    }
}
