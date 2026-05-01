package com.aivox.besota.sdk.ota;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class RemoteOTAConfig implements Serializable {
    private String checkSum;
    private String downloadUrl;
    private String localPath;
    private String pid;
    private String version;
    private String whatsNewContent;
    private String whatsNewTitle;

    public String getPid() {
        return this.pid;
    }

    public void setPid(String str) {
        this.pid = str;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public void setDownloadUrl(String str) {
        this.downloadUrl = str;
    }

    public String getLocalPath() {
        return this.localPath;
    }

    public void setLocalPath(String str) {
        this.localPath = str;
    }

    public String getCheckSum() {
        return this.checkSum;
    }

    public void setCheckSum(String str) {
        this.checkSum = str;
    }

    public String getWhatsNewTitle(String str) {
        return this.whatsNewTitle;
    }

    public void setWhatsNewTitle(String str) {
        this.whatsNewTitle = str;
    }

    public String getWhatsNewContent(String str) {
        return this.whatsNewContent;
    }

    public void setWhatsNewContent(String str) {
        this.whatsNewContent = str;
    }

    public String toString() {
        return "RemoteOTAConfig{pid='" + this.pid + "', version='" + this.version + "', downloadUrl='" + this.downloadUrl + "', localPath='" + this.localPath + "', checkSum='" + this.checkSum + "', whatsNewTitle='" + this.whatsNewTitle + "', whatsNewContent='" + this.whatsNewContent + "'}";
    }
}
