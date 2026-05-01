package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class AiSummaryBean implements Serializable {
    private Integer aiCount;
    private Integer audioId;
    private Integer count;
    private String detail;
    private Integer detailTokenCount;

    /* JADX INFO: renamed from: id */
    private Integer f218id;
    private Integer originCount;
    private Integer status;
    private Integer summaryType;
    private String url;
    private String uuid;

    public Integer getAudioId() {
        return this.audioId;
    }

    public void setAudioId(Integer num) {
        this.audioId = num;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer num) {
        this.count = num;
    }

    public Integer getOriginCount() {
        return this.originCount;
    }

    public void setOriginCount(Integer num) {
        this.originCount = num;
    }

    public Integer getAiCount() {
        return this.aiCount;
    }

    public void setAiCount(Integer num) {
        this.aiCount = num;
    }

    public Integer getId() {
        return this.f218id;
    }

    public void setId(Integer num) {
        this.f218id = num;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String str) {
        this.detail = str;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String str) {
        this.uuid = str;
    }

    public Integer getDetailTokenCount() {
        return this.detailTokenCount;
    }

    public void setDetailTokenCount(Integer num) {
        this.detailTokenCount = num;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer num) {
        this.status = num;
    }

    public Integer getSummaryType() {
        return this.summaryType;
    }

    public void setSummaryType(Integer num) {
        this.summaryType = num;
    }
}
