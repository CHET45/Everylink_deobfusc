package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class CodeConvertResultBean implements Serializable {
    private Integer expire;
    private Integer size;
    private Integer type;

    public Integer getSize() {
        return this.size;
    }

    public void setSize(Integer num) {
        this.size = num;
    }

    public Integer getExpire() {
        return this.expire;
    }

    public void setExpire(Integer num) {
        this.expire = num;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer num) {
        this.type = num;
    }
}
