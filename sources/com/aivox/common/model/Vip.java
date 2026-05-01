package com.aivox.common.model;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Vip {

    /* JADX INFO: renamed from: id */
    int f262id;
    List<MemberPrice> memberPriceList = new ArrayList();
    String recycle;
    String storage;
    int timestamp;
    String voicetime;

    public List<MemberPrice> getMemberPriceList() {
        return this.memberPriceList;
    }

    public void setMemberPriceList(List<MemberPrice> list) {
        this.memberPriceList = list;
    }

    public int getId() {
        return this.f262id;
    }

    public void setId(int i) {
        this.f262id = i;
    }

    public String getVoicetime() {
        return this.voicetime;
    }

    public void setVoicetime(String str) {
        this.voicetime = str;
    }

    public String getStorage() {
        return this.storage;
    }

    public void setStorage(String str) {
        this.storage = str;
    }

    public int getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(int i) {
        this.timestamp = i;
    }

    public String getRecycle() {
        return this.recycle;
    }

    public void setRecycle(String str) {
        this.recycle = str;
    }
}
