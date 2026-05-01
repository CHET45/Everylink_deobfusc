package com.aivox.common.model;

import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TranscribeListBean implements Serializable {
    private Integer current;
    private Boolean hitCount;
    private Boolean optimizeCountSql;
    private Integer pages;
    private List<Transcribe> records;
    private Boolean searchCount;
    private Integer size;
    private Integer total;

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer num) {
        this.total = num;
    }

    public Integer getCurrent() {
        return this.current;
    }

    public void setCurrent(Integer num) {
        this.current = num;
    }

    public Boolean getHitCount() {
        return this.hitCount;
    }

    public void setHitCount(Boolean bool) {
        this.hitCount = bool;
    }

    public Integer getPages() {
        return this.pages;
    }

    public void setPages(Integer num) {
        this.pages = num;
    }

    public Integer getSize() {
        return this.size;
    }

    public void setSize(Integer num) {
        this.size = num;
    }

    public Boolean getOptimizeCountSql() {
        return this.optimizeCountSql;
    }

    public void setOptimizeCountSql(Boolean bool) {
        this.optimizeCountSql = bool;
    }

    public List<Transcribe> getRecords() {
        return this.records;
    }

    public void setRecords(List<Transcribe> list) {
        this.records = list;
    }

    public Boolean getSearchCount() {
        return this.searchCount;
    }

    public void setSearchCount(Boolean bool) {
        this.searchCount = bool;
    }
}
