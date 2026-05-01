package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GlassOtaUpdateBean implements Serializable {
    private String btMessage;
    private String btUrl;
    private String btVersion;
    private String wifiMessage;
    private String wifiUrl;
    private String wifiVersion;

    public String getBtVersion() {
        return this.btVersion;
    }

    public void setBtVersion(String str) {
        this.btVersion = str;
    }

    public String getWifiVersion() {
        return this.wifiVersion;
    }

    public void setWifiVersion(String str) {
        this.wifiVersion = str;
    }

    public String getBtUrl() {
        return this.btUrl;
    }

    public void setBtUrl(String str) {
        this.btUrl = str;
    }

    public String getWifiUrl() {
        return this.wifiUrl;
    }

    public void setWifiUrl(String str) {
        this.wifiUrl = str;
    }

    public String getBtMessage() {
        return this.btMessage;
    }

    public void setBtMessage(String str) {
        this.btMessage = str;
    }

    public String getWifiMessage() {
        return this.wifiMessage;
    }

    public void setWifiMessage(String str) {
        this.wifiMessage = str;
    }
}
