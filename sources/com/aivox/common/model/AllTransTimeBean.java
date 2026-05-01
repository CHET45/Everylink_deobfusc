package com.aivox.common.model;

import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AllTransTimeBean implements Serializable {
    private List<RecordTimeList> recordTimeList;
    private List<RecordTimeList> usedRecordTimeList;

    public List<RecordTimeList> getUsedRecordTimeList() {
        return this.usedRecordTimeList;
    }

    public void setUsedRecordTimeList(List<RecordTimeList> list) {
        this.usedRecordTimeList = list;
    }

    public List<RecordTimeList> getRecordTimeList() {
        return this.recordTimeList;
    }

    public void setRecordTimeList(List<RecordTimeList> list) {
        this.recordTimeList = list;
    }

    public static class RecordTimeList implements Serializable {
        private String beginAt;
        private String expireAt;
        private boolean expired = true;

        /* JADX INFO: renamed from: id */
        private Integer f220id;
        private Integer source;
        private String sourceText;
        private Integer status;
        private Integer total;
        private Integer type;
        private Integer used;
        private String uuid;

        public Integer getTotal() {
            return this.total;
        }

        public void setTotal(Integer num) {
            this.total = num;
        }

        public String getBeginAt() {
            return this.beginAt;
        }

        public void setBeginAt(String str) {
            this.beginAt = str;
        }

        public String getSourceText() {
            return this.sourceText;
        }

        public void setSourceText(String str) {
            this.sourceText = str;
        }

        public Integer getId() {
            return this.f220id;
        }

        public void setId(Integer num) {
            this.f220id = num;
        }

        public Integer getUsed() {
            return this.used;
        }

        public void setUsed(Integer num) {
            this.used = num;
        }

        public Integer getSource() {
            return this.source;
        }

        public void setSource(Integer num) {
            this.source = num;
        }

        public String getExpireAt() {
            return this.expireAt;
        }

        public void setExpireAt(String str) {
            this.expireAt = str;
        }

        public Integer getType() {
            return this.type;
        }

        public void setType(Integer num) {
            this.type = num;
        }

        public String getUuid() {
            return this.uuid;
        }

        public void setUuid(String str) {
            this.uuid = str;
        }

        public Integer getStatus() {
            return this.status;
        }

        public void setStatus(Integer num) {
            this.status = num;
        }

        public boolean isExpired() {
            return this.expired;
        }

        public void setExpired(boolean z) {
            this.expired = z;
        }
    }
}
