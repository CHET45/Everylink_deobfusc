package com.aivox.common.model;

import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class NoticeBean implements Serializable {
    private MessageList messageList;
    private List<SysNotifyList> sysNotifyList;

    public MessageList getMessageList() {
        return this.messageList;
    }

    public void setMessageList(MessageList messageList) {
        this.messageList = messageList;
    }

    public List<SysNotifyList> getSysNotifyList() {
        return this.sysNotifyList;
    }

    public void setSysNotifyList(List<SysNotifyList> list) {
        this.sysNotifyList = list;
    }

    public static class MessageList implements Serializable {
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
            private String content;
            private String createdAt;

            /* JADX INFO: renamed from: id */
            private Integer f253id;
            private boolean isEffective;
            private Integer isRead;
            private String sign;
            private String title;
            private Integer type;
            private String url;
            private String uuid;

            public String getCreatedAt() {
                return this.createdAt;
            }

            public void setCreatedAt(String str) {
                this.createdAt = str;
            }

            public Integer getIsRead() {
                return this.isRead;
            }

            public void setIsRead(Integer num) {
                this.isRead = num;
            }

            public Integer getId() {
                return this.f253id;
            }

            public void setId(Integer num) {
                this.f253id = num;
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

            public Integer getType() {
                return this.type;
            }

            public void setType(Integer num) {
                this.type = num;
            }

            public String getSign() {
                return this.sign;
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
        }
    }

    public static class SysNotifyList implements Serializable {
        private String content;
        private String createdAt;

        /* JADX INFO: renamed from: id */
        private Integer f254id;
        private Integer status;
        private String title;
        private Integer type;
        private String url;

        public String getCreatedAt() {
            return this.createdAt;
        }

        public void setCreatedAt(String str) {
            this.createdAt = str;
        }

        public Integer getId() {
            return this.f254id;
        }

        public void setId(Integer num) {
            this.f254id = num;
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

        public Integer getStatus() {
            return this.status;
        }

        public void setStatus(Integer num) {
            this.status = num;
        }
    }
}
