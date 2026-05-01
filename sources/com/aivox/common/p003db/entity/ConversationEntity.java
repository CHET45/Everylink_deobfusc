package com.aivox.common.p003db.entity;

/* JADX INFO: loaded from: classes.dex */
public class ConversationEntity {
    private String content;

    /* JADX INFO: renamed from: id */
    private Long f209id;
    private boolean mySide;
    private String translation;
    private String uid;

    public ConversationEntity(Long l, String str, String str2, String str3, boolean z) {
        this.f209id = l;
        this.content = str;
        this.translation = str2;
        this.uid = str3;
        this.mySide = z;
    }

    public ConversationEntity() {
    }

    public Long getId() {
        return this.f209id;
    }

    public void setId(Long l) {
        this.f209id = l;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public String getTranslation() {
        return this.translation;
    }

    public void setTranslation(String str) {
        this.translation = str;
    }

    public boolean getMySide() {
        return this.mySide;
    }

    public void setMySide(boolean z) {
        this.mySide = z;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String str) {
        this.uid = str;
    }
}
