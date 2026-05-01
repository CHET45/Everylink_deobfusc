package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class ActivityBean implements Serializable {

    /* JADX INFO: renamed from: id */
    private Integer f216id;
    private int notifyOnly;
    private String pictureUrl;
    private Integer showType;
    private String text;
    private String title;
    private Integer type;
    private String url;

    public String getPictureUrl() {
        return this.pictureUrl;
    }

    public void setPictureUrl(String str) {
        this.pictureUrl = str;
    }

    public Integer getShowType() {
        return this.showType;
    }

    public void setShowType(Integer num) {
        this.showType = num;
    }

    public Integer getId() {
        return this.f216id;
    }

    public void setId(Integer num) {
        this.f216id = num;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer num) {
        this.type = num;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public int getNotifyOnly() {
        return this.notifyOnly;
    }

    public void setNotifyOnly(int i) {
        this.notifyOnly = i;
    }
}
