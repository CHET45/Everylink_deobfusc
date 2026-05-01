package com.github.houbb.heaven.util.p010io.big;

/* JADX INFO: loaded from: classes3.dex */
public class BigFileStringHandlerContext {
    private String charset;
    private String filePath;
    private long filePointer;
    private int index;
    private String line;

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public String getLine() {
        return this.line;
    }

    public void setLine(String str) {
        this.line = str;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String str) {
        this.filePath = str;
    }

    public String getCharset() {
        return this.charset;
    }

    public void setCharset(String str) {
        this.charset = str;
    }

    public long getFilePointer() {
        return this.filePointer;
    }

    public void setFilePointer(long j) {
        this.filePointer = j;
    }

    public String toString() {
        return "BigFileStringHandlerContext{index=" + this.index + ", line='" + this.line + "', filePath='" + this.filePath + "', charset='" + this.charset + "', filePointer=" + this.filePointer + '}';
    }
}
