package com.tencent.cos.xml.model.tag.eventstreaming;

/* JADX INFO: loaded from: classes4.dex */
public class Stats {
    private Long bytesProcessed;
    private Long bytesReturned;
    private Long bytesScanned;

    public Stats(Long l, Long l2, Long l3) {
        this.bytesScanned = l;
        this.bytesProcessed = l2;
        this.bytesReturned = l3;
    }

    public Long getBytesScanned() {
        return this.bytesScanned;
    }

    public void setBytesScanned(Long l) {
        this.bytesScanned = l;
    }

    public Stats withBytesScanned(Long l) {
        setBytesScanned(l);
        return this;
    }

    public Long getBytesReturned() {
        return this.bytesReturned;
    }

    public void setBytesReturned(Long l) {
        this.bytesReturned = l;
    }

    public Stats withBytesReturned(Long l) {
        setBytesReturned(l);
        return this;
    }

    public Long getBytesProcessed() {
        return this.bytesProcessed;
    }

    public void setBytesProcessed(Long l) {
        this.bytesProcessed = l;
    }

    public Stats withBytesProcessed(Long l) {
        setBytesProcessed(l);
        return this;
    }
}
