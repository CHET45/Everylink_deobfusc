package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class AudioMarkBean implements Serializable {
    private String content;

    /* JADX INFO: renamed from: id */
    private Integer f224id;
    private Long markTime;

    public Long getMarkTime() {
        return this.markTime;
    }

    public void setMarkTime(Long l) {
        this.markTime = l;
    }

    public Integer getId() {
        return this.f224id;
    }

    public void setId(Integer num) {
        this.f224id = num;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }
}
