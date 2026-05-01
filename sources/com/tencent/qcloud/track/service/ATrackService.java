package com.tencent.qcloud.track.service;

import com.tencent.qcloud.track.IReport;

/* JADX INFO: loaded from: classes4.dex */
public abstract class ATrackService implements IReport {
    private boolean debug = false;
    private boolean isCloseReport = false;
    protected boolean isInit = false;

    public void setIsCloseReport(boolean z) {
        this.isCloseReport = z;
    }

    public boolean isCloseReport() {
        return this.isCloseReport;
    }

    public boolean isDebug() {
        return this.debug;
    }

    public void setDebug(boolean z) {
        this.debug = z;
    }
}
