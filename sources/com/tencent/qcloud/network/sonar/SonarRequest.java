package com.tencent.qcloud.network.sonar;

/* JADX INFO: loaded from: classes4.dex */
public class SonarRequest {
    private String host;

    /* JADX INFO: renamed from: ip */
    private String f1873ip;
    private boolean isNetworkAvailable;

    public SonarRequest(String str) {
        this.host = str;
    }

    public SonarRequest(String str, String str2) {
        this.host = str;
        this.f1873ip = str2;
    }

    public String getHost() {
        return this.host;
    }

    public String getIp() {
        return this.f1873ip;
    }

    public void setIp(String str) {
        this.f1873ip = str;
    }

    public boolean isNetworkAvailable() {
        return this.isNetworkAvailable;
    }

    public void setNetworkAvailable(boolean z) {
        this.isNetworkAvailable = z;
    }
}
