package com.aivox.common.model;

/* JADX INFO: loaded from: classes.dex */
public enum AudioType {
    MP3(1, "mp3"),
    WAV(2, "wav"),
    PCM(3, "pcm");

    int index;
    String type;

    AudioType(int i, String str) {
        this.index = i;
        this.type = str;
    }

    public int getIndex() {
        return this.index;
    }

    public String getType() {
        return this.type;
    }
}
