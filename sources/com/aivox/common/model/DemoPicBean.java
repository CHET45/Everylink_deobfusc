package com.aivox.common.model;

/* JADX INFO: loaded from: classes.dex */
public class DemoPicBean {
    private long createTime;
    private String date;
    private long duration;

    /* JADX INFO: renamed from: id */
    private int f233id;
    private boolean isFavorite;
    private boolean isSelected;
    private String url;

    public DemoPicBean(int i, String str, String str2, long j, boolean z) {
        this.f233id = i;
        this.url = str;
        this.date = str2;
        this.duration = j;
        this.isFavorite = z;
    }

    public int getId() {
        return this.f233id;
    }

    public void setId(int i) {
        this.f233id = i;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long j) {
        this.createTime = j;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long j) {
        this.duration = j;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String str) {
        this.date = str;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }

    public boolean isFavorite() {
        return this.isFavorite;
    }

    public void setFavorite(boolean z) {
        this.isFavorite = z;
    }
}
