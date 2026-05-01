package com.aivox.common.model;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Model {
    long filetime;

    /* JADX INFO: renamed from: id */
    private int f251id;
    List<String> imageurl = new ArrayList();
    private String psw;
    private String sign;
    private String url;

    public int getId() {
        return this.f251id;
    }

    public void setId(int i) {
        this.f251id = i;
    }

    public List<String> getImageurl() {
        return this.imageurl;
    }

    public void setImageurl(List<String> list) {
        this.imageurl = list;
    }

    public String getPsw() {
        return this.psw;
    }

    public void setPsw(String str) {
        this.psw = str;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String str) {
        this.sign = str;
    }

    public long getFiletime() {
        return this.filetime;
    }

    public void setFiletime(long j) {
        this.filetime = j;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }
}
