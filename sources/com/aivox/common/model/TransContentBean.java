package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class TransContentBean implements Serializable {
    private Integer audioId;
    private String transContent;

    public Integer getAudioId() {
        return this.audioId;
    }

    public void setAudioId(Integer num) {
        this.audioId = num;
    }

    public String getTransContent() {
        return this.transContent;
    }

    public void setTransContent(String str) {
        this.transContent = str;
    }
}
