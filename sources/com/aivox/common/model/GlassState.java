package com.aivox.common.model;

import com.aivox.base.common.GlassesCmd;

/* JADX INFO: loaded from: classes.dex */
public class GlassState {
    private GlassesCmd currentCmd;
    private String diskApi;
    private String fileApi;
    private int fileCount;

    /* JADX INFO: renamed from: ip */
    private String f245ip;

    public String getIp() {
        return this.f245ip;
    }

    public void setIp(String str) {
        this.f245ip = str;
    }

    public int getFileCount() {
        return this.fileCount;
    }

    public void setFileCount(int i) {
        this.fileCount = i;
    }

    public String getFileApi() {
        return this.fileApi;
    }

    public void setFileApi(String str) {
        this.fileApi = str;
    }

    public String getDiskApi() {
        return this.diskApi;
    }

    public void setDiskApi(String str) {
        this.diskApi = str;
    }

    public GlassesCmd getCurrentCmd() {
        return this.currentCmd;
    }

    public void setCurrentCmd(GlassesCmd glassesCmd) {
        this.currentCmd = glassesCmd;
    }
}
