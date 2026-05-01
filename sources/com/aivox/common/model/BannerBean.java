package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class BannerBean implements Serializable {
    private String createdAt;
    private String description;

    /* JADX INFO: renamed from: id */
    private Integer f226id;
    private String image;
    private Integer jumpFlag;
    private String jumpUrl;
    private Integer order;
    private Integer status;
    private String title;

    public String getImage() {
        return this.image;
    }

    public void setImage(String str) {
        this.image = str;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String str) {
        this.createdAt = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public Integer getId() {
        return this.f226id;
    }

    public void setId(Integer num) {
        this.f226id = num;
    }

    public Integer getJumpFlag() {
        return this.jumpFlag;
    }

    public void setJumpFlag(Integer num) {
        this.jumpFlag = num;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getJumpUrl() {
        return this.jumpUrl;
    }

    public void setJumpUrl(String str) {
        this.jumpUrl = str;
    }

    public Integer getOrder() {
        return this.order;
    }

    public void setOrder(Integer num) {
        this.order = num;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer num) {
        this.status = num;
    }
}
