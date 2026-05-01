package com.aivox.common.p003db.entity;

/* JADX INFO: loaded from: classes.dex */
public class GlassImageEntity {
    private String createTime;
    private long duration;

    /* JADX INFO: renamed from: id */
    private Long f211id;
    private String imageName;
    private String imagePath;
    private boolean isFavorite;
    private String uid;

    public GlassImageEntity(Long l, String str, String str2, String str3, String str4, long j, boolean z) {
        this.f211id = l;
        this.uid = str;
        this.imagePath = str2;
        this.imageName = str3;
        this.createTime = str4;
        this.duration = j;
        this.isFavorite = z;
    }

    public GlassImageEntity() {
        this.duration = 0L;
        this.isFavorite = false;
    }

    public Long getId() {
        return this.f211id;
    }

    public void setId(Long l) {
        this.f211id = l;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String str) {
        this.imagePath = str;
    }

    public String getImageName() {
        return this.imageName;
    }

    public void setImageName(String str) {
        this.imageName = str;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String str) {
        this.uid = str;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long j) {
        this.duration = j;
    }

    public boolean getIsFavorite() {
        return this.isFavorite;
    }

    public void setIsFavorite(boolean z) {
        this.isFavorite = z;
    }
}
