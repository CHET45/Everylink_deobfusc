package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class ServiceUrl implements Serializable {
    String httpUrl;
    String privacy_agreement;
    String recording_sharing;
    String service_agreement;
    String user_manual;

    public String getService_agreement() {
        return this.service_agreement;
    }

    public void setService_agreement(String str) {
        this.service_agreement = str;
    }

    public String getRecording_sharing() {
        return this.recording_sharing;
    }

    public void setRecording_sharing(String str) {
        this.recording_sharing = str;
    }

    public String getPrivacy_agreement() {
        return this.privacy_agreement;
    }

    public void setPrivacy_agreement(String str) {
        this.privacy_agreement = str;
    }

    public String getUser_manual() {
        return this.user_manual;
    }

    public void setUser_manual(String str) {
        this.user_manual = str;
    }

    public String getHttpUrl() {
        return this.httpUrl;
    }

    public void setHttpUrl(String str) {
        this.httpUrl = str;
    }
}
