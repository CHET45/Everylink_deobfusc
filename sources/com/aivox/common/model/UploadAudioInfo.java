package com.aivox.common.model;

/* JADX INFO: loaded from: classes.dex */
public class UploadAudioInfo {
    private long audioLength;
    private long audioTimeDuration;
    private String audioUrl;
    private String localPath;

    public String getLocalPath() {
        return this.localPath;
    }

    public void setLocalPath(String str) {
        this.localPath = str;
    }

    public String getAudioUrl() {
        return this.audioUrl;
    }

    public void setAudioUrl(String str) {
        this.audioUrl = str;
    }

    public long getAudioLength() {
        return this.audioLength;
    }

    public void setAudioLength(long j) {
        this.audioLength = j;
    }

    public long getAudioTimeDuration() {
        return this.audioTimeDuration;
    }

    public void setAudioTimeDuration(long j) {
        this.audioTimeDuration = j;
    }
}
