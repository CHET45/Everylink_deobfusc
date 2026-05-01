package com.aivox.common.model;

/* JADX INFO: loaded from: classes.dex */
public class GetCodeBean {
    private int Total;
    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return this.code;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String str) {
        this.msg = str;
    }

    public DataBean getData() {
        return this.data;
    }

    public void setData(DataBean dataBean) {
        this.data = dataBean;
    }

    public int getTotal() {
        return this.Total;
    }

    public void setTotal(int i) {
        this.Total = i;
    }

    public static class DataBean {
        private String BizId;
        private String Code;
        private String Message;
        private String RequestId;

        public String getMessage() {
            return this.Message;
        }

        public void setMessage(String str) {
            this.Message = str;
        }

        public String getRequestId() {
            return this.RequestId;
        }

        public void setRequestId(String str) {
            this.RequestId = str;
        }

        public String getBizId() {
            return this.BizId;
        }

        public void setBizId(String str) {
            this.BizId = str;
        }

        public String getCode() {
            return this.Code;
        }

        public void setCode(String str) {
            this.Code = str;
        }
    }
}
