package com.aivox.common.model;

/* JADX INFO: loaded from: classes.dex */
public class UploadFileBean {
    public static final int TYPE_IMG = 1;
    public static final int TYPE_SELECT = 0;
    public static final int TYPE_VIDEO = 2;
    private String localPath;
    private int type;
    private String url;

    public UploadFileBean(int i, String str) {
        this.type = i;
        this.localPath = str;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public String getLocalPath() {
        return this.localPath;
    }

    public void setLocalPath(String str) {
        this.localPath = str;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }
}
