package com.aivox.common.model;

/* JADX INFO: loaded from: classes.dex */
public class CommonNoticeBean {
    private String content;
    private String createdAt;

    /* JADX INFO: renamed from: id */
    private Integer f228id;
    private boolean isEffective;
    private String sign;
    private String title;
    private Integer type;
    private String url;

    public String getCreatedAt() {
        String str = this.createdAt;
        return str == null ? "" : str;
    }

    public void setCreatedAt(String str) {
        this.createdAt = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer num) {
        this.type = num;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String getSign() {
        String str = this.sign;
        return str == null ? "" : str;
    }

    public void setSign(String str) {
        this.sign = str;
    }

    public boolean isEffective() {
        return this.isEffective;
    }

    public void setEffective(boolean z) {
        this.isEffective = z;
    }

    public Integer getId() {
        return this.f228id;
    }

    public void setId(Integer num) {
        this.f228id = num;
    }
}
