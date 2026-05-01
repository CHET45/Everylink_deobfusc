package com.aivox.common.model;

import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AiChatBean implements Serializable {
    private Integer current;
    private Integer pages;
    private List<Records> records;
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

    public List<Records> getRecords() {
        return this.records;
    }

    public void setRecords(List<Records> list) {
        this.records = list;
    }

    public static class Records implements Serializable {
        private String answer;
        private Integer audioId;
        private String createdAt;

        /* JADX INFO: renamed from: id */
        private Integer f217id;
        private String question;
        private Integer status;
        private Integer tokenCount;
        private Integer type;

        public String getCreatedAt() {
            return this.createdAt;
        }

        public void setCreatedAt(String str) {
            this.createdAt = str;
        }

        public String getQuestion() {
            return this.question;
        }

        public void setQuestion(String str) {
            this.question = str;
        }

        public String getAnswer() {
            String str = this.answer;
            return str == null ? "" : str;
        }

        public void setAnswer(String str) {
            this.answer = str;
        }

        public Integer getAudioId() {
            return this.audioId;
        }

        public void setAudioId(Integer num) {
            this.audioId = num;
        }

        public Integer getTokenCount() {
            return this.tokenCount;
        }

        public void setTokenCount(Integer num) {
            this.tokenCount = num;
        }

        public Integer getId() {
            return this.f217id;
        }

        public void setId(Integer num) {
            this.f217id = num;
        }

        public Integer getType() {
            return this.type;
        }

        public void setType(Integer num) {
            this.type = num;
        }

        public Integer getStatus() {
            return this.status;
        }

        public void setStatus(Integer num) {
            this.status = num;
        }
    }
}
