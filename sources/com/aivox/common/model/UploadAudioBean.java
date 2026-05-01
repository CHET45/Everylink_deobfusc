package com.aivox.common.model;

/* JADX INFO: loaded from: classes.dex */
public class UploadAudioBean {
    private boolean isApp = true;
    private boolean isDevice = false;
    private int isTop;
    private String title;
    private UploadAudioInfo uploadAudioInfo;

    public int getIsTop() {
        return this.isTop;
    }

    public void setIsTop(int i) {
        this.isTop = i;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public boolean getIsApp() {
        return this.isApp;
    }

    public void setIsApp(boolean z) {
        this.isApp = z;
    }

    public UploadAudioInfo getUploadAudioInfo() {
        return this.uploadAudioInfo;
    }

    public void setUploadAudioInfo(UploadAudioInfo uploadAudioInfo) {
        this.uploadAudioInfo = uploadAudioInfo;
    }

    public boolean getIsDevice() {
        return this.isDevice;
    }

    public void setIsDevice(boolean z) {
        this.isDevice = z;
    }
}
