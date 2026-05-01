package com.tencent.aai.model;

/* JADX INFO: loaded from: classes4.dex */
public class AudioRecognizeResult {
    private int code;
    private int endTime;
    private String message;
    private String resultJson;
    private final int seq;
    private final int sliceType;
    private int startTime;
    private final String text;
    private final String voiceId;

    public AudioRecognizeResult(String str, int i, String str2, int i2, String str3, int i3, int i4, int i5, String str4) {
        this.code = i2;
        this.message = str3;
        this.voiceId = str;
        this.seq = i;
        this.text = str2;
        this.sliceType = i3;
        this.startTime = i4;
        this.endTime = i5;
        this.resultJson = str4;
    }

    public int getCode() {
        return this.code;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public String getMessage() {
        return this.message;
    }

    public String getResultJson() {
        return this.resultJson;
    }

    public int getSeq() {
        return this.seq;
    }

    public int getSliceType() {
        return this.sliceType;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public String getText() {
        return this.text;
    }

    public String getVoiceId() {
        return this.voiceId;
    }
}
