package com.tencent.cos.xml.common;

/* JADX INFO: loaded from: classes4.dex */
public class Range {
    private long end;
    private long start;

    public Range(long j, long j2) {
        this.start = j;
        this.end = j2;
    }

    public Range(long j) {
        this(j, -1L);
    }

    public long getEnd() {
        return this.end;
    }

    public long getStart() {
        return this.start;
    }

    public String getRange() {
        Long lValueOf = Long.valueOf(this.start);
        long j = this.end;
        return String.format("bytes=%s-%s", lValueOf, j == -1 ? "" : String.valueOf(j));
    }
}
