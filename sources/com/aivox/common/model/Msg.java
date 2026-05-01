package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class Msg implements Serializable {

    /* JADX INFO: renamed from: id */
    int f252id;
    String isread;
    String message;
    String pushmessagetype;
    String pushtime;
    String pushtype;
    String title;
    String userid;

    public int getId() {
        return this.f252id;
    }

    public void setId(int i) {
        this.f252id = i;
    }

    public String getPushtype() {
        return this.pushtype;
    }

    public void setPushtype(String str) {
        this.pushtype = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String str) {
        this.userid = str;
    }

    public String getPushtime() {
        return this.pushtime;
    }

    public void setPushtime(String str) {
        this.pushtime = str;
    }

    public String getPushmessagetype() {
        return this.pushmessagetype;
    }

    public void setPushmessagetype(String str) {
        this.pushmessagetype = str;
    }

    public String getIsread() {
        return this.isread;
    }

    public void setIsread(String str) {
        this.isread = str;
    }
}
