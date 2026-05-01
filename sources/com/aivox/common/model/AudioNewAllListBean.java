package com.aivox.common.model;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AudioNewAllListBean {
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

    public static class DataBean {
        private List<AudioInfoBean> records;
        private int total;

        public int getTotal() {
            return this.total;
        }

        public void setTotal(int i) {
            this.total = i;
        }

        public List<AudioInfoBean> getDatas() {
            return this.records;
        }

        public void setDatas(List<AudioInfoBean> list) {
            this.records = list;
        }
    }
}
