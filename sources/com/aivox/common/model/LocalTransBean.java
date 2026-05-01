package com.aivox.common.model;

/* JADX INFO: loaded from: classes.dex */
public class LocalTransBean {
    private String beginAt;
    private String endAt;
    private String onebest;

    public LocalTransBean(String str, String str2, String str3) {
        this.beginAt = str;
        this.endAt = str2;
        this.onebest = str3;
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
}
