package com.aivox.common.model;

/* JADX INFO: loaded from: classes.dex */
public class DeviceInfo {
    String deviceId;
    String deviceName;
    int duration;
    int fileId;
    String fileName;
    int from;
    int fromF;
    int isRealTimeSave;
    int meetingType;
    int online;
    String power;
    String recordStatus;
    String surplusStorage;

    /* JADX INFO: renamed from: to */
    int f235to;
    int toF;
    String totalStorage;
    String transcribeType;
    int uid;
    String uniqueCode;
    String version;
    String volume;

    public String toString() {
        return "DeviceInfo{online=" + this.online + ", uid=" + this.uid + ", deviceId='" + this.deviceId + "', totalStorage='" + this.totalStorage + "', surplusStorage='" + this.surplusStorage + "', version='" + this.version + "', uniqueCode='" + this.uniqueCode + "', volume='" + this.volume + "', deviceName='" + this.deviceName + "', power='" + this.power + "', recordStatus='" + this.recordStatus + "', transcribeType=" + this.transcribeType + ", meetingType=" + this.meetingType + ", from=" + this.from + ", to=" + this.f235to + ", fromF=" + this.fromF + ", toF=" + this.toF + ", isRealTimeSave=" + this.isRealTimeSave + ", fileName='" + this.fileName + "', fileId=" + this.fileId + ", duration=" + this.duration + '}';
    }

    public int getOnline() {
        return this.online;
    }

    public void setOnline(int i) {
        this.online = i;
    }

    public int getMeetingType() {
        return this.meetingType;
    }

    public void setMeetingType(int i) {
        this.meetingType = i;
    }

    public int getFrom() {
        return this.from;
    }

    public void setFrom(int i) {
        this.from = i;
    }

    public int getTo() {
        return this.f235to;
    }

    public void setTo(int i) {
        this.f235to = i;
    }

    public int getFromF() {
        return this.fromF;
    }

    public void setFromF(int i) {
        this.fromF = i;
    }

    public int getToF() {
        return this.toF;
    }

    public void setToF(int i) {
        this.toF = i;
    }

    public int getIsRealTimeSave() {
        return this.isRealTimeSave;
    }

    public void setIsRealTimeSave(int i) {
        this.isRealTimeSave = i;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }

    public int getFileId() {
        return this.fileId;
    }

    public void setFileId(int i) {
        this.fileId = i;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int i) {
        this.duration = i;
    }

    public String getTranscribeType() {
        return this.transcribeType;
    }

    public void setTranscribeType(String str) {
        this.transcribeType = str;
    }

    public int getUid() {
        return this.uid;
    }

    public void setUid(int i) {
        this.uid = i;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String str) {
        this.deviceName = str;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public String getTotalStorage() {
        return this.totalStorage;
    }

    public void setTotalStorage(String str) {
        this.totalStorage = str;
    }

    public String getSurplusStorage() {
        return this.surplusStorage;
    }

    public void setSurplusStorage(String str) {
        this.surplusStorage = str;
    }

    public String getPower() {
        return this.power;
    }

    public void setPower(String str) {
        this.power = str;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public String getUniqueCode() {
        return this.uniqueCode;
    }

    public void setUniqueCode(String str) {
        this.uniqueCode = str;
    }

    public String getVolume() {
        return this.volume;
    }

    public void setVolume(String str) {
        this.volume = str;
    }

    public String getRecordStatus() {
        return this.recordStatus;
    }

    public void setRecordStatus(String str) {
        this.recordStatus = str;
    }
}
