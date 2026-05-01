package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class DeviceRightsBean implements Serializable {
    private Integer aiChatLimit;
    private Boolean aiChatUnlimited;
    private Integer aiImageLimit;
    private Boolean aiImageUnlimited;
    private Integer aiSummaryLimit;
    private Boolean aiSummaryUnlimited;
    private Integer bilingualCountLimit;
    private Boolean bilingualUnlimited;
    private String deviceName;
    private String devicePrivilegeBindAt;
    private String devicePrivilegeExpireAt;
    private Integer generalTime;
    private Boolean recordTimeUnlimited;

    public Boolean getBilingualUnlimited() {
        return this.bilingualUnlimited;
    }

    public void setBilingualUnlimited(Boolean bool) {
        this.bilingualUnlimited = bool;
    }

    public Integer getAiChatLimit() {
        return this.aiChatLimit;
    }

    public void setAiChatLimit(Integer num) {
        this.aiChatLimit = num;
    }

    public Integer getBilingualCountLimit() {
        return this.bilingualCountLimit;
    }

    public void setBilingualCountLimit(Integer num) {
        this.bilingualCountLimit = num;
    }

    public String getDevicePrivilegeExpireAt() {
        return this.devicePrivilegeExpireAt;
    }

    public void setDevicePrivilegeExpireAt(String str) {
        this.devicePrivilegeExpireAt = str;
    }

    public Boolean getAiSummaryUnlimited() {
        return this.aiSummaryUnlimited;
    }

    public void setAiSummaryUnlimited(Boolean bool) {
        this.aiSummaryUnlimited = bool;
    }

    public Integer getAiImageLimit() {
        return this.aiImageLimit;
    }

    public void setAiImageLimit(Integer num) {
        this.aiImageLimit = num;
    }

    public Boolean getRecordTimeUnlimited() {
        return this.recordTimeUnlimited;
    }

    public void setRecordTimeUnlimited(Boolean bool) {
        this.recordTimeUnlimited = bool;
    }

    public String getDevicePrivilegeBindAt() {
        return this.devicePrivilegeBindAt;
    }

    public void setDevicePrivilegeBindAt(String str) {
        this.devicePrivilegeBindAt = str;
    }

    public Boolean getAiChatUnlimited() {
        return this.aiChatUnlimited;
    }

    public void setAiChatUnlimited(Boolean bool) {
        this.aiChatUnlimited = bool;
    }

    public Boolean getAiImageUnlimited() {
        return this.aiImageUnlimited;
    }

    public void setAiImageUnlimited(Boolean bool) {
        this.aiImageUnlimited = bool;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String str) {
        this.deviceName = str;
    }

    public Integer getAiSummaryLimit() {
        return this.aiSummaryLimit;
    }

    public void setAiSummaryLimit(Integer num) {
        this.aiSummaryLimit = num;
    }

    public Integer getGeneralTime() {
        return this.generalTime;
    }

    public void setGeneralTime(Integer num) {
        this.generalTime = num;
    }
}
