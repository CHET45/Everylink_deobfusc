package com.aivox.common.model;

/* JADX INFO: loaded from: classes.dex */
public class RecordState {
    int deviceType;
    int duration;
    String identification;
    boolean isRecord;
    boolean isTransmission;
    int uid;

    public RecordState(int i, int i2, String str, boolean z, int i3, boolean z2) {
        this.uid = i;
        this.deviceType = i2;
        this.identification = str;
        this.isRecord = z;
        this.duration = i3;
        this.isTransmission = z2;
    }

    public String toString() {
        return "RecordState{uid=" + this.uid + ", deviceType=" + this.deviceType + ", identification='" + this.identification + "', isRecord=" + this.isRecord + ", duration=" + this.duration + ", isTransmission=" + this.isTransmission + '}';
    }

    public int getUid() {
        return this.uid;
    }

    public void setUid(int i) {
        this.uid = i;
    }

    public int getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(int i) {
        this.deviceType = i;
    }

    public String getIdentification() {
        return this.identification;
    }

    public void setIdentification(String str) {
        this.identification = str;
    }

    public boolean isRecord() {
        return this.isRecord;
    }

    public void setRecord(boolean z) {
        this.isRecord = z;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int i) {
        this.duration = i;
    }

    public boolean isTransmission() {
        return this.isTransmission;
    }

    public void setTransmission(boolean z) {
        this.isTransmission = z;
    }
}
