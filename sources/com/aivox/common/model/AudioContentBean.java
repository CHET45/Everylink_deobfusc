package com.aivox.common.model;

import com.aivox.base.util.BaseStringUtil;
import com.github.houbb.heaven.constant.CharConst;
import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AudioContentBean implements Serializable {
    private List<DataBean> list;
    int transId;

    public AudioContentBean(int i, List<DataBean> list) {
        this.transId = i;
        this.list = list;
    }

    public String toString() {
        return "{\"audioId\":\"" + this.transId + "\", \"list\":[" + BaseStringUtil.listToString(this.list, CharConst.COMMA) + "]}";
    }

    public static class DataBean implements Serializable {
        String content;
        int pos;
        int transId;

        public DataBean(int i, String str, int i2) {
            this.transId = i;
            this.content = str;
            this.pos = i2;
        }

        public String toString() {
            return "{\"transId\":\"" + this.transId + "\", \"content\":\"" + this.content + "\"}";
        }

        public int getTransId() {
            return this.transId;
        }

        public void setTransId(int i) {
            this.transId = i;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String str) {
            this.content = str;
        }

        public int getPos() {
            return this.pos;
        }

        public void setPos(int i) {
            this.pos = i;
        }
    }
}
