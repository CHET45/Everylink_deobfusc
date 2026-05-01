package com.aivox.common.p003db.entity;

import com.aivox.base.util.BaseStringUtil;
import com.aivox.common.model.AudioInfo;
import com.aivox.common.model.AudioInfoBean;

/* JADX INFO: loaded from: classes.dex */
public class LocalFileEntity {
    private boolean autoSync;
    private String createTime;
    private int duration;
    private String editTime;
    private long fileSize;
    private boolean fromOldVersion;

    /* JADX INFO: renamed from: id */
    private Long f213id;
    private boolean isBreak;
    private String localPath;
    private String participant;
    private String site;
    private int syncStatus;
    private String title;
    private int topStatus;
    private int transStatus;
    private String uid;
    private int vid;
    private String videoUrl;

    public LocalFileEntity() {
    }

    public LocalFileEntity(AudioInfoBean audioInfoBean) {
        this.vid = audioInfoBean.getId();
        this.uid = audioInfoBean.getUuid();
        this.title = audioInfoBean.getTitle();
        this.localPath = audioInfoBean.getLocalPath();
        this.createTime = audioInfoBean.getStartTime();
        this.site = audioInfoBean.getSite();
        this.transStatus = audioInfoBean.getIsTrans();
        this.topStatus = audioInfoBean.getIsTop();
        if (audioInfoBean.getAudioInfo() != null) {
            this.videoUrl = audioInfoBean.getAudioInfo().getAudioUrl();
            this.duration = audioInfoBean.getAudioInfo().getAudioTimeDuration().intValue();
            this.syncStatus = !BaseStringUtil.isEmpty(audioInfoBean.getAudioInfo().getAudioUrl()) ? 1 : 0;
            this.autoSync = false;
        }
    }

    public LocalFileEntity(Long l, int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, int i2, int i3, int i4, int i5, long j, boolean z, boolean z2, boolean z3) {
        this.f213id = l;
        this.vid = i;
        this.uid = str;
        this.title = str2;
        this.localPath = str3;
        this.createTime = str4;
        this.editTime = str5;
        this.site = str6;
        this.participant = str7;
        this.videoUrl = str8;
        this.duration = i2;
        this.transStatus = i3;
        this.syncStatus = i4;
        this.topStatus = i5;
        this.fileSize = j;
        this.fromOldVersion = z;
        this.autoSync = z2;
        this.isBreak = z3;
    }

    public AudioInfoBean convert2AudioInfo() {
        AudioInfoBean audioInfoBean = new AudioInfoBean();
        audioInfoBean.setId(getVid());
        audioInfoBean.setTitle(getTitle());
        audioInfoBean.setSite(getSite());
        audioInfoBean.setIsTrans(getTransStatus());
        audioInfoBean.setIsTop(getTopStatus());
        audioInfoBean.setStartTime(getCreateTime());
        audioInfoBean.setLocalPath(getLocalPath());
        AudioInfo audioInfo = new AudioInfo();
        audioInfo.setAudioTimeDuration(Integer.valueOf(getDuration()));
        audioInfo.setAudioUrl(getVideoUrl());
        audioInfo.setLocalPath(getLocalPath());
        audioInfoBean.setAudioInfo(audioInfo);
        return audioInfoBean;
    }

    public Long getId() {
        return this.f213id;
    }

    public void setId(Long l) {
        this.f213id = l;
    }

    public int getVid() {
        return this.vid;
    }

    public void setVid(int i) {
        this.vid = i;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String str) {
        this.uid = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getLocalPath() {
        return this.localPath;
    }

    public void setLocalPath(String str) {
        this.localPath = str;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public String getEditTime() {
        return this.editTime;
    }

    public void setEditTime(String str) {
        this.editTime = str;
    }

    public int getTransStatus() {
        return this.transStatus;
    }

    public void setTransStatus(int i) {
        this.transStatus = i;
    }

    public int getSyncStatus() {
        return this.syncStatus;
    }

    public void setSyncStatus(int i) {
        this.syncStatus = i;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int i) {
        this.duration = i;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(long j) {
        this.fileSize = j;
    }

    public int getTopStatus() {
        return this.topStatus;
    }

    public void setTopStatus(int i) {
        this.topStatus = i;
    }

    public String getSite() {
        return this.site;
    }

    public void setSite(String str) {
        this.site = str;
    }

    public String getParticipant() {
        return this.participant;
    }

    public void setParticipant(String str) {
        this.participant = str;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public void setVideoUrl(String str) {
        this.videoUrl = str;
    }

    public boolean getFromOldVersion() {
        return this.fromOldVersion;
    }

    public void setFromOldVersion(boolean z) {
        this.fromOldVersion = z;
    }

    public boolean getAutoSync() {
        return this.autoSync;
    }

    public void setAutoSync(boolean z) {
        this.autoSync = z;
    }

    public boolean getIsBreak() {
        return this.isBreak;
    }

    public void setIsBreak(boolean z) {
        this.isBreak = z;
    }
}
