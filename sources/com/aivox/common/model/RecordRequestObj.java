package com.aivox.common.model;

/* JADX INFO: loaded from: classes.dex */
public class RecordRequestObj {
    int from;
    int fromF;
    int isRealTimeSave;
    int meetingType;

    /* JADX INFO: renamed from: to */
    int f257to;
    int toF;
    int transcribeType;

    public int getTranscribeType() {
        return this.transcribeType;
    }

    public void setTranscribeType(int i) {
        this.transcribeType = i;
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
        return this.f257to;
    }

    public void setTo(int i) {
        this.f257to = i;
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

    public RecordRequestObj(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        this.transcribeType = i;
        this.meetingType = i2;
        this.from = i3;
        this.f257to = i4;
        this.fromF = i5;
        this.toF = i6;
        this.isRealTimeSave = i7;
    }
}
