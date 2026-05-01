package com.aivox.common.p003db.entity;

/* JADX INFO: loaded from: classes.dex */
public class HomeAiChatEntity {
    private String answer;
    private Long audioId;
    private String createdAt;

    /* JADX INFO: renamed from: id */
    private Long f212id;
    private String imageUrl;
    private String question;
    private Integer status;
    private Long tokenCount;
    private Integer type;
    private String uid;

    public HomeAiChatEntity(Long l, String str, Long l2, Long l3, Integer num, Integer num2, String str2, String str3, String str4, String str5) {
        this.f212id = l;
        this.uid = str;
        this.audioId = l2;
        this.tokenCount = l3;
        this.type = num;
        this.status = num2;
        this.question = str2;
        this.answer = str3;
        this.createdAt = str4;
        this.imageUrl = str5;
    }

    public HomeAiChatEntity() {
    }

    public Long getId() {
        return this.f212id;
    }

    public void setId(Long l) {
        this.f212id = l;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String str) {
        this.uid = str;
    }

    public Long getAudioId() {
        return this.audioId;
    }

    public void setAudioId(Long l) {
        this.audioId = l;
    }

    public Long getTokenCount() {
        return this.tokenCount;
    }

    public void setTokenCount(Long l) {
        this.tokenCount = l;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer num) {
        this.type = num;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer num) {
        this.status = num;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String str) {
        this.question = str;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String str) {
        this.answer = str;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String str) {
        this.createdAt = str;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String str) {
        this.imageUrl = str;
    }
}
