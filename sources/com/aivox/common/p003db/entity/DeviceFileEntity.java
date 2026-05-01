package com.aivox.common.p003db.entity;

/* JADX INFO: loaded from: classes.dex */
public class DeviceFileEntity {
    private int createTime;
    private int duration;
    private long fileId;
    private long fileSize;

    /* JADX INFO: renamed from: id */
    private Long f210id;
    private String localPath;
    private String name;
    private boolean synced;
    private String uid;

    public DeviceFileEntity(Long l, long j, long j2, int i, int i2, boolean z, String str, String str2, String str3) {
        this.f210id = l;
        this.fileId = j;
        this.fileSize = j2;
        this.createTime = i;
        this.duration = i2;
        this.synced = z;
        this.name = str;
        this.uid = str2;
        this.localPath = str3;
    }

    public DeviceFileEntity() {
    }

    public Long getId() {
        return this.f210id;
    }

    public void setId(Long l) {
        this.f210id = l;
    }

    public long getFileId() {
        return this.fileId;
    }

    public void setFileId(long j) {
        this.fileId = j;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(long j) {
        this.fileSize = j;
    }

    public int getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(int i) {
        this.createTime = i;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int i) {
        this.duration = i;
    }

    public boolean getSynced() {
        return this.synced;
    }

    public void setSynced(boolean z) {
        this.synced = z;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String str) {
        this.uid = str;
    }

    public String getLocalPath() {
        return this.localPath;
    }

    public void setLocalPath(String str) {
        this.localPath = str;
    }
}
