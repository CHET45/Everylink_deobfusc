package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class AudioInfo implements Serializable {
    private Integer audioTimeDuration;
    private String audioUrl;

    /* JADX INFO: renamed from: id */
    private Object f221id;
    private Integer language;
    private String localPath;
    private Integer source;
    private Integer speakerAvatarStyle;

    public String getAudioUrl() {
        return this.audioUrl;
    }

    public void setAudioUrl(String str) {
        this.audioUrl = str;
    }

    public String getLocalPath() {
        return this.localPath;
    }

    public void setLocalPath(String str) {
        this.localPath = str;
    }

    public Integer getAudioTimeDuration() {
        return this.audioTimeDuration;
    }

    public void setAudioTimeDuration(Integer num) {
        this.audioTimeDuration = num;
    }

    public Integer getLanguage() {
        return this.language;
    }

    public void setLanguage(Integer num) {
        this.language = num;
    }

    public Object getId() {
        return this.f221id;
    }

    public void setId(Object obj) {
        this.f221id = obj;
    }

    public Integer getSource() {
        return this.source;
    }

    public void setSource(Integer num) {
        this.source = num;
    }

    public Integer getSpeakerAvatarStyle() {
        return this.speakerAvatarStyle;
    }

    public void setSpeakerAvatarStyle(Integer num) {
        this.speakerAvatarStyle = num;
    }
}
