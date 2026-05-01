package com.aivox.common.model;

import com.aivox.base.util.BaseStringUtil;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class Transcribe implements Serializable {
    private String audioId;
    private AudioMarkBean audioMark;
    private String beginAt;
    private String currentStartTime;
    private String endAt;
    private boolean hasBindImg;

    /* JADX INFO: renamed from: id */
    private int f260id;
    private List<TagImgBean> imageList;
    private boolean isConversationLeft;
    private boolean isPick;
    private String keywords;
    private String onebest;
    private String sessionId;
    private boolean showNoteMark;
    private String speaker;
    private String speakerName;
    private long time;
    private String toLang;
    private String translate;
    private String type;
    private String uuid;
    private String var;
    private int tempId = -1;
    private int curSpeakIndex = -1;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public boolean isConversationLeft() {
        return this.isConversationLeft;
    }

    public void setConversationLeft(boolean z) {
        this.isConversationLeft = z;
    }

    public String getSpeakerName() {
        return this.speakerName;
    }

    public void setSpeakerName(String str) {
        this.speakerName = str;
    }

    public boolean isPick() {
        return this.isPick;
    }

    public void setPick(boolean z) {
        this.isPick = z;
    }

    public boolean isHasBindImg() {
        return this.hasBindImg;
    }

    public void setHasBindImg(boolean z) {
        this.hasBindImg = z;
    }

    public List<TagImgBean> getImageList() {
        List<TagImgBean> list = this.imageList;
        return list == null ? new ArrayList() : list;
    }

    public void setImageList(List<TagImgBean> list) {
        this.imageList = list;
    }

    public String toString() {
        return "Transcribe{id=" + this.f260id + ", bg='" + this.beginAt + "', ed='" + this.endAt + "', onebest='" + this.onebest + "', speaker='" + this.speaker + "', videoid=" + this.audioId + ", translate='" + this.translate + "'}";
    }

    public int getTempId() {
        return this.tempId;
    }

    public void setTempId(int i) {
        this.tempId = i;
    }

    public String getToLang() {
        return this.toLang;
    }

    public void setToLang(String str) {
        this.toLang = str;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long j) {
        this.time = j;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getVar() {
        return this.var;
    }

    public void setVar(String str) {
        this.var = str;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String str) {
        this.uuid = str;
    }

    public String getCurrentStartTime() {
        return this.currentStartTime;
    }

    public void setCurrentStartTime(String str) {
        this.currentStartTime = str;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String str) {
        this.keywords = str;
    }

    public int getCurSpeakIndex() {
        return this.curSpeakIndex;
    }

    public void setCurSpeakIndex(int i) {
        this.curSpeakIndex = i;
    }

    public int getId() {
        return this.f260id;
    }

    public void setId(int i) {
        this.f260id = i;
    }

    public String getBeginAt() {
        return this.beginAt;
    }

    public void setBeginAt(String str) {
        this.beginAt = str;
    }

    public String getEndAt() {
        return this.endAt;
    }

    public void setEndAt(String str) {
        this.endAt = str;
    }

    public String getOnebest() {
        return this.onebest;
    }

    public void setOnebest(String str) {
        this.onebest = str;
    }

    public String getSpeaker() {
        return BaseStringUtil.isEmpty(this.speaker) ? "" : this.speaker;
    }

    public void setSpeaker(String str) {
        this.speaker = str;
    }

    public String getAudioId() {
        return this.audioId;
    }

    public void setAudioId(String str) {
        this.audioId = str;
    }

    public String getTranslate() {
        return BaseStringUtil.isEmpty(this.translate) ? "" : this.translate;
    }

    public void setTranslate(String str) {
        this.translate = str;
    }

    public AudioMarkBean getAudioMark() {
        return this.audioMark;
    }

    public void setAudioMark(AudioMarkBean audioMarkBean) {
        this.audioMark = audioMarkBean;
    }

    public boolean isShowNoteMark() {
        return this.showNoteMark;
    }

    public void setShowNoteMark(boolean z) {
        this.showNoteMark = z;
    }

    public static class TagImgBean implements Serializable {

        /* JADX INFO: renamed from: id */
        private int f261id;
        private String imageUrl;

        public TagImgBean(int i, String str) {
            this.f261id = i;
            this.imageUrl = str;
        }

        public int getId() {
            return this.f261id;
        }

        public void setId(int i) {
            this.f261id = i;
        }

        public String getImageUrl() {
            return this.imageUrl;
        }

        public void setImageUrl(String str) {
            this.imageUrl = str;
        }
    }

    public AiTranscribe toEntity(long j) {
        try {
            return new AiTranscribe(this.f260id, Integer.parseInt(getAudioId()), Integer.parseInt(this.beginAt), Integer.parseInt(this.endAt), 1, this.onebest, this.sdf.format(Long.valueOf(j + ((long) Integer.parseInt(this.beginAt)))));
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
