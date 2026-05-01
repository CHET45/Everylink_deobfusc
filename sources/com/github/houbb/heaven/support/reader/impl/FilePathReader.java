package com.github.houbb.heaven.support.reader.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.support.reader.IReader;
import com.github.houbb.heaven.util.p010io.FileUtil;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class FilePathReader implements IReader {
    private final String charset;
    private final String path;

    public FilePathReader(String str, String str2) {
        this.path = str;
        this.charset = str2;
    }

    public FilePathReader(String str) {
        this(str, "UTF-8");
    }

    @Override // com.github.houbb.heaven.support.reader.IReader
    public String read() {
        return FileUtil.getFileContent(this.path, this.charset);
    }
}
