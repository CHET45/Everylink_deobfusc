package com.aivox.common.model;

/* JADX INFO: loaded from: classes.dex */
public class LocalFileBean {
    long addTime;

    /* JADX INFO: renamed from: id */
    int f248id;
    String localPath;
    String uid;
    int vid;

    public String toString() {
        return "LocalFileBean{localPath='" + this.localPath + "', addTime=" + this.addTime + ", uid='" + this.uid + "', id=" + this.f248id + ", vid='" + this.vid + "'}";
    }

    public int getVid() {
        return this.vid;
    }

    public void setVid(int i) {
        this.vid = i;
    }

    public int getId() {
        return this.f248id;
    }

    public void setId(int i) {
        this.f248id = i;
    }

    public String getLocalPath() {
        return this.localPath;
    }

    public void setLocalPath(String str) {
        this.localPath = str;
    }

    public long getAddTime() {
        return this.addTime;
    }

    public void setAddTime(long j) {
        this.addTime = j;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String str) {
        this.uid = str;
    }
}
