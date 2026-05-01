package com.tencent.aai.task;

/* JADX INFO: loaded from: classes4.dex */
public class AudioPcmData implements Cloneable {
    private final short[] buffer;

    public AudioPcmData(short[] sArr) {
        this.buffer = sArr;
    }

    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public AudioPcmData m2693clone() {
        return new AudioPcmData((short[]) this.buffer.clone());
    }

    public short[] getBuffer() {
        return this.buffer;
    }
}
