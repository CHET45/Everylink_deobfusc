package com.aivox.common.model;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RegisterBean {
    private int Total;
    private int code;
    private List<?> data;
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

    public int getTotal() {
        return this.Total;
    }

    public void setTotal(int i) {
        this.Total = i;
    }

    public List<?> getData() {
        return this.data;
    }

    public void setData(List<?> list) {
        this.data = list;
    }
}
