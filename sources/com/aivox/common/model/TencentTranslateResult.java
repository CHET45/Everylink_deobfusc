package com.aivox.common.model;

/* JADX INFO: loaded from: classes.dex */
public class TencentTranslateResult {
    Data data;
    String msg;
    int ret;

    public String toString() {
        return "TencentTranslateResult{ret=" + this.ret + ", msg='" + this.msg + "', data=" + this.data + '}';
    }

    public int getRet() {
        return this.ret;
    }

    public void setRet(int i) {
        this.ret = i;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String str) {
        this.msg = str;
    }

    public Data getData() {
        return this.data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    class Data {
        String source_text;
        String target_text;

        Data() {
        }

        public String toString() {
            return "Data{source_text='" + this.source_text + "', target_text='" + this.target_text + "'}";
        }

        public String getSource_text() {
            return this.source_text;
        }

        public void setSource_text(String str) {
            this.source_text = str;
        }

        public String getTarget_text() {
            return this.target_text;
        }

        public void setTarget_text(String str) {
            this.target_text = str;
        }
    }
}
