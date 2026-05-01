package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class ChildAccount implements Serializable {
    String headUrl;

    /* JADX INFO: renamed from: id */
    int f227id;
    boolean ivDelShow;
    String nickname;
    String phone;
    String pinYinHeadChar;

    public boolean isIvDelShow() {
        return this.ivDelShow;
    }

    public void setIvDelShow(boolean z) {
        this.ivDelShow = z;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String str) {
        this.nickname = str;
    }

    public String getHeadUrl() {
        return this.headUrl;
    }

    public void setHeadUrl(String str) {
        this.headUrl = str;
    }

    public int getId() {
        return this.f227id;
    }

    public void setId(int i) {
        this.f227id = i;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public String getPinYinHeadChar() {
        return this.pinYinHeadChar;
    }

    public void setPinYinHeadChar(String str) {
        this.pinYinHeadChar = str;
    }
}
