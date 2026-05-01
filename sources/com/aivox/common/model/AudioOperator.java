package com.aivox.common.model;

/* JADX INFO: loaded from: classes.dex */
public class AudioOperator {

    /* JADX INFO: renamed from: id */
    int f225id;
    int ids;
    String name;

    public AudioOperator(int i, String str, int i2) {
        this.f225id = i;
        this.name = str;
        this.ids = i2;
    }

    public int getId() {
        return this.f225id;
    }

    public void setId(int i) {
        this.f225id = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public int getIds() {
        return this.ids;
    }

    public void setIds(int i) {
        this.ids = i;
    }
}
