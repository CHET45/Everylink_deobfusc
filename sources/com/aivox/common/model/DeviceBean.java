package com.aivox.common.model;

/* JADX INFO: loaded from: classes.dex */
public class DeviceBean {
    private String address;
    private String name;
    private int state;

    public DeviceBean() {
    }

    public DeviceBean(String str, String str2) {
        this.name = str;
        this.address = str2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int i) {
        this.state = i;
    }
}
