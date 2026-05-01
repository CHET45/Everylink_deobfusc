package com.aivox.common.model;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AiTranscribe {
    private int beginAt;
    private int endAt;

    /* JADX INFO: renamed from: id */
    private int f219id;
    private List<ImageListBean> imageList;
    private String onebest;
    private String speaker;
    private String speakerName;
    private String startTime;
    private String toLang;
    private String translate;
    private int vid;
    private int videoType;

    public int getId() {
        return this.f219id;
    }

    public void setId(int i) {
        this.f219id = i;
    }

    public int getVid() {
        return this.vid;
    }

    public void setVid(int i) {
        this.vid = i;
    }

    public int getBeginAt() {
        return this.beginAt;
    }

    public void setBeginAt(int i) {
        this.beginAt = i;
    }

    public int getEndAt() {
        return this.endAt;
    }

    public void setEndAt(int i) {
        this.endAt = i;
    }

    public int getVideoType() {
        return this.videoType;
    }

    public void setVideoType(int i) {
        this.videoType = i;
    }

    public String getSpeaker() {
        return this.speaker;
    }

    public void setSpeaker(String str) {
        this.speaker = str;
    }

    public String getOnebest() {
        return this.onebest;
    }

    public void setOnebest(String str) {
        this.onebest = str;
    }

    public String getTranslate() {
        return this.translate;
    }

    public void setTranslate(String str) {
        this.translate = str;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String str) {
        this.startTime = str;
    }

    public String getToLang() {
        return this.toLang;
    }

    public void setToLang(String str) {
        this.toLang = str;
    }

    public String getSpeakerName() {
        return this.speakerName;
    }

    public void setSpeakerName(String str) {
        this.speakerName = str;
    }

    public List<ImageListBean> getImageList() {
        return this.imageList;
    }

    public void setImageList(List<ImageListBean> list) {
        this.imageList = list;
    }

    public AiTranscribe(int i, int i2, int i3, int i4, int i5, String str, String str2) {
        this.f219id = i;
        this.vid = i2;
        this.beginAt = i3;
        this.endAt = i4;
        this.videoType = i5;
        this.onebest = str;
        this.startTime = str2;
    }

    public static class ImageListBean {
        private int imageSize;
        private String imageUrl;

        public int getImageSize() {
            return this.imageSize;
        }

        public void setImageSize(int i) {
            this.imageSize = i;
        }

        public String getImageUrl() {
            return this.imageUrl;
        }

        public void setImageUrl(String str) {
            this.imageUrl = str;
        }

        public ImageListBean(int i, String str) {
            this.imageSize = i;
            this.imageUrl = str;
        }
    }
}
