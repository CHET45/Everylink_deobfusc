package com.aivox.common.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class Label implements Serializable {

    /* JADX INFO: renamed from: id */
    int f247id;
    String keywords = "";

    @SerializedName("markAt")
    int punctuationtime;

    @SerializedName("contents")
    String punctuationtitle;

    @SerializedName("videoId")
    int videofileid;

    public String toString() {
        return "Label{punctuationtitle='" + this.punctuationtitle + "', punctuationtime='" + this.punctuationtime + "', id=" + this.f247id + ", videofileid=" + this.videofileid + ", keywords='" + this.keywords + "'}";
    }

    public Label(String str, int i) {
        this.punctuationtitle = str;
        this.punctuationtime = i;
    }

    public Label(String str, int i, int i2, int i3) {
        this.punctuationtitle = str;
        this.punctuationtime = i;
        this.f247id = i2;
        this.videofileid = i3;
    }

    public String getPunctuationtitle() {
        return this.punctuationtitle;
    }

    public void setPunctuationtitle(String str) {
        this.punctuationtitle = str;
    }

    public int getPunctuationtime() {
        return this.punctuationtime;
    }

    public void setPunctuationtime(int i) {
        this.punctuationtime = i;
    }

    public int getId() {
        return this.f247id;
    }

    public void setId(int i) {
        this.f247id = i;
    }

    public int getVideofileid() {
        return this.videofileid;
    }

    public void setVideofileid(int i) {
        this.videofileid = i;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String str) {
        this.keywords = str;
    }
}
