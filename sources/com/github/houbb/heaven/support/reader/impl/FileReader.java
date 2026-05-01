package com.github.houbb.heaven.support.reader.impl;

import com.github.houbb.heaven.support.reader.IReader;
import com.github.houbb.heaven.util.p010io.FileUtil;
import java.io.File;

/* JADX INFO: loaded from: classes3.dex */
public class FileReader implements IReader {
    private final String charset;
    private final File file;

    public FileReader(File file, String str) {
        this.file = file;
        this.charset = str;
    }

    @Override // com.github.houbb.heaven.support.reader.IReader
    public String read() {
        return FileUtil.getFileContent(this.file, this.charset);
    }
}
