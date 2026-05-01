package com.aivox.common_ui.update;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateBean implements Serializable {
    private String apkUrl;

    /* JADX INFO: renamed from: id */
    private Integer f286id;
    private Integer must;
    private String mustVersion;
    private String newVersion;
    private Integer type;
    private String versionLog;

    public String getApkUrl() {
        return this.apkUrl;
    }

    public void setApkUrl(String str) {
        this.apkUrl = str;
    }

    public String getVersionLog() {
        return this.versionLog;
    }

    public void setVersionLog(String str) {
        this.versionLog = str;
    }

    public Integer getMust() {
        return this.must;
    }

    public void setMust(Integer num) {
        this.must = num;
    }

    public Integer getId() {
        return this.f286id;
    }

    public void setId(Integer num) {
        this.f286id = num;
    }

    public String getMustVersion() {
        return this.mustVersion;
    }

    public void setMustVersion(String str) {
        this.mustVersion = str;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer num) {
        this.type = num;
    }

    public String getNewVersion() {
        return this.newVersion;
    }

    public void setNewVersion(String str) {
        this.newVersion = str;
    }
}
