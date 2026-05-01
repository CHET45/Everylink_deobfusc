package com.aivox.common.model;

import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SpaceDetailListBean implements Serializable {
    private int code;
    private List<DetailBean> data;
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

    public List<DetailBean> getData() {
        return this.data;
    }

    public static class DetailBean implements Serializable {
        private int capacity;
        private String createdAt;
        private String expireAt;

        /* JADX INFO: renamed from: id */
        private int f258id;
        private String type;

        public int getId() {
            return this.f258id;
        }

        public void setId(int i) {
            this.f258id = i;
        }

        public String getExpireAt() {
            return this.expireAt;
        }

        public void setExpireAt(String str) {
            this.expireAt = str;
        }

        public String getCreatedAt() {
            return this.createdAt;
        }

        public void setCreatedAt(String str) {
            this.createdAt = str;
        }

        public int getCapacity() {
            return this.capacity;
        }

        public void setCapacity(int i) {
            this.capacity = i;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String str) {
            this.type = str;
        }

        public boolean isExpired() {
            return BaseStringUtil.isNotEmpty(this.expireAt) && DateUtil.isTimePassed(this.expireAt);
        }

        public DetailBean(String str, int i, String str2) {
            this.expireAt = str;
            this.capacity = i;
            this.type = str2;
        }
    }
}
