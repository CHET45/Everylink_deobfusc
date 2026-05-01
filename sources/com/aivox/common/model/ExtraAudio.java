package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class ExtraAudio implements Serializable {
    private Integer audioId;
    private String title;

    public Integer getAudioId() {
        return this.audioId;
    }

    public void setAudioId(Integer num) {
        this.audioId = num;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }
}
