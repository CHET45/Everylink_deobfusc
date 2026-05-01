package com.tencent.cloud.stream.tts;

import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes4.dex */
public class SpeechSynthesizerSubtitle {

    @SerializedName("BeginIndex")
    private Integer beginIndex;

    @SerializedName("BeginTime")
    private Integer beginTime;

    @SerializedName("EndIndex")
    private Integer endIndex;

    @SerializedName("EndTime")
    private Integer endTime;

    @SerializedName("Phoneme")
    private String phoneme;

    @SerializedName("Text")
    private String text;

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(Integer beginTime) {
        this.beginTime = beginTime;
    }

    public Integer getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getBeginIndex() {
        return this.beginIndex;
    }

    public void setBeginIndex(Integer beginIndex) {
        this.beginIndex = beginIndex;
    }

    public Integer getEndIndex() {
        return this.endIndex;
    }

    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }

    public String getPhoneme() {
        return this.phoneme;
    }

    public void setPhoneme(String phoneme) {
        this.phoneme = phoneme;
    }
}
