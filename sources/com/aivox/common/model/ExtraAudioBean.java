package com.aivox.common.model;

import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ExtraAudioBean implements Serializable {
    private Object countId;
    private Integer current;
    private Object maxLimit;
    private Boolean optimizeCountSql;
    private Integer pages;
    private List<Records> records;
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

    public List<Records> getRecords() {
        return this.records;
    }

    public void setRecords(List<Records> list) {
        this.records = list;
    }

    public Object getMaxLimit() {
        return this.maxLimit;
    }

    public void setMaxLimit(Object obj) {
        this.maxLimit = obj;
    }

    public Boolean getSearchCount() {
        return this.searchCount;
    }

    public void setSearchCount(Boolean bool) {
        this.searchCount = bool;
    }

    public Object getCountId() {
        return this.countId;
    }

    public void setCountId(Object obj) {
        this.countId = obj;
    }

    public static class Records implements Serializable {
        private Integer audioTimeDuration;
        private Boolean checked;
        private String createdAt;

        /* JADX INFO: renamed from: id */
        private Integer f243id;
        private String title;
        private String uuid;

        public String getCreatedAt() {
            return this.createdAt;
        }

        public void setCreatedAt(String str) {
            this.createdAt = str;
        }

        public Integer getAudioTimeDuration() {
            return this.audioTimeDuration;
        }

        public void setAudioTimeDuration(Integer num) {
            this.audioTimeDuration = num;
        }

        public Integer getId() {
            return this.f243id;
        }

        public void setId(Integer num) {
            this.f243id = num;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public String getUuid() {
            return this.uuid;
        }

        public void setUuid(String str) {
            this.uuid = str;
        }

        public Boolean getChecked() {
            Boolean bool = this.checked;
            return Boolean.valueOf(bool != null && bool.booleanValue());
        }

        public void setChecked(Boolean bool) {
            this.checked = bool;
        }
    }
}
