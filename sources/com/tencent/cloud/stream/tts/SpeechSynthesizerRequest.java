package com.tencent.cloud.stream.tts;

import com.google.gson.annotations.SerializedName;
import com.microsoft.azure.storage.table.TableConstants;
import com.tencent.cloud.stream.tts.core.p032ws.CommonRequest;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class SpeechSynthesizerRequest extends CommonRequest {

    @SerializedName("Action")
    private String action;

    @SerializedName("AppId")
    private Integer appId;

    @SerializedName("Codec")
    private String codec;

    @SerializedName("EmotionCategory")
    private String emotionCategory;

    @SerializedName("EmotionIntensity")
    private Integer emotionIntensity;

    @SerializedName("EnableSubtitle")
    private Boolean enableSubtitle;

    @SerializedName("Expired")
    protected Long expired;

    @SerializedName("SampleRate")
    private Integer sampleRate;

    @SerializedName("SecretId")
    private String secretid;

    @SerializedName("SessionId")
    private String sessionId;

    @SerializedName("Speed")
    private Float speed;

    @SerializedName("Text")
    private String text;

    @SerializedName(TableConstants.TIMESTAMP)
    protected Long timestamp;

    @SerializedName("VoiceType")
    private Integer voiceType;

    @SerializedName("Volume")
    private Float volume;

    public String getSecretid() {
        return this.secretid;
    }

    public void setSecretid(String secretid) {
        this.secretid = secretid;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getExpired() {
        return this.expired;
    }

    public void setExpired(Long expired) {
        this.expired = expired;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getVoiceType() {
        return this.voiceType;
    }

    public void setVoiceType(Integer voiceType) {
        this.voiceType = voiceType;
    }

    public Float getVolume() {
        return this.volume;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    public Float getSpeed() {
        return this.speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public Integer getSampleRate() {
        return this.sampleRate;
    }

    public void setSampleRate(Integer sampleRate) {
        this.sampleRate = sampleRate;
    }

    public String getCodec() {
        return this.codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public Boolean getEnableSubtitle() {
        return this.enableSubtitle;
    }

    public void setEnableSubtitle(Boolean enableSubtitle) {
        this.enableSubtitle = enableSubtitle;
    }

    public String getEmotionCategory() {
        return this.emotionCategory;
    }

    public void setEmotionCategory(String emotionCategory) {
        this.emotionCategory = emotionCategory;
    }

    public Integer getEmotionIntensity() {
        return this.emotionIntensity;
    }

    public void setEmotionIntensity(Integer emotionIntensity) {
        this.emotionIntensity = emotionIntensity;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getAppId() {
        return this.appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    @Override // com.tencent.cloud.stream.tts.core.p032ws.CommonRequest
    public Map<String, Object> toTreeMap() {
        Map<String, Object> treeMap = super.toTreeMap();
        if (treeMap != null) {
            for (String str : treeMap.keySet()) {
                if (treeMap.get(str) instanceof Float) {
                    Float fValueOf = Float.valueOf(Float.parseFloat(treeMap.get(str).toString()));
                    if (fValueOf != null && fValueOf.intValue() == fValueOf.floatValue()) {
                        treeMap.put(str, Integer.valueOf(fValueOf.intValue()));
                    } else {
                        treeMap.put(str, fValueOf);
                    }
                }
            }
        }
        return treeMap;
    }
}
