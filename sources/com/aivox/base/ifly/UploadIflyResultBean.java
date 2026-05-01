package com.aivox.base.ifly;

/* JADX INFO: loaded from: classes.dex */
public class UploadIflyResultBean {
    private String code;
    private Content content;
    private String descInfo;

    public String toString() {
        return "UploadIflyResultBean{code='" + this.code + "', descInfo='" + this.descInfo + "', content=" + this.content + '}';
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public String getDescInfo() {
        return this.descInfo;
    }

    public void setDescInfo(String str) {
        this.descInfo = str;
    }

    public Content getContent() {
        return this.content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    private class Content {
        String orderId;

        private Content() {
        }

        public String getOrderId() {
            return this.orderId;
        }

        public void setOrderId(String str) {
            this.orderId = str;
        }
    }
}
