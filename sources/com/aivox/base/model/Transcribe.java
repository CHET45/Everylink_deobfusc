package com.aivox.base.model;

import com.aivox.base.util.BaseStringUtil;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class Transcribe implements Serializable {

    /* JADX INFO: renamed from: bg */
    String f189bg;
    int curSpeakIndex = -1;
    long currentPosition;
    int currentSession;

    /* JADX INFO: renamed from: ed */
    String f190ed;

    /* JADX INFO: renamed from: id */
    int f191id;
    String keywords;
    String onebest;
    String sessionId;
    long sessionStartTime;
    boolean showLabel;
    String speaker;
    String startTime;
    long time;
    String translate;
    String type;
    int userid;
    String var;
    int videofileid;
    int videoid;
    String videoname;

    public String toString() {
        return "Transcribe{id=" + this.f191id + ", bg='" + this.f189bg + "', ed='" + this.f190ed + "', onebest='" + this.onebest + "', var='" + this.var + "', sessionId='" + this.sessionId + "', speaker='" + this.speaker + "', videoid=" + this.videoid + ", userid=" + this.userid + ", videofileid=" + this.videofileid + ", videoname='" + this.videoname + "', translate='" + this.translate + "', type='" + this.type + "', curSpeakIndex=" + this.curSpeakIndex + ", keywords='" + this.keywords + "', time=" + this.time + ", startTime='" + this.startTime + "', sessionStartTime=" + this.sessionStartTime + ", showLabel=" + this.showLabel + ", currentPosition=" + this.currentPosition + ", currentSession=" + this.currentSession + '}';
    }

    public int getCurrentSession() {
        return this.currentSession;
    }

    public void setCurrentSession(int i) {
        this.currentSession = i;
    }

    public long getCurrentPosition() {
        return this.currentPosition;
    }

    public void setCurrentPosition(long j) {
        this.currentPosition = j;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long j) {
        this.time = j;
    }

    public boolean isShowLabel() {
        return this.showLabel;
    }

    public void setShowLabel(boolean z) {
        this.showLabel = z;
    }

    public long getSessionStartTime() {
        return this.sessionStartTime;
    }

    public void setSessionStartTime(long j) {
        this.sessionStartTime = j;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getVar() {
        return this.var;
    }

    public void setVar(String str) {
        this.var = str;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public String getVideoname() {
        return this.videoname;
    }

    public void setVideoname(String str) {
        this.videoname = str;
    }

    public int getVideofileid() {
        return this.videofileid;
    }

    public void setVideofileid(int i) {
        this.videofileid = i;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String str) {
        this.startTime = str;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String str) {
        this.keywords = str;
    }

    public int getCurSpeakIndex() {
        return this.curSpeakIndex;
    }

    public void setCurSpeakIndex(int i) {
        this.curSpeakIndex = i;
    }

    public int getId() {
        return this.f191id;
    }

    public void setId(int i) {
        this.f191id = i;
    }

    public String getBg() {
        return this.f189bg;
    }

    public void setBg(String str) {
        this.f189bg = str;
    }

    public String getEd() {
        return this.f190ed;
    }

    public void setEd(String str) {
        this.f190ed = str;
    }

    public String getOnebest() {
        return this.onebest;
    }

    public void setOnebest(String str) {
        this.onebest = str;
    }

    public String getSpeaker() {
        return BaseStringUtil.isEmpty(this.speaker) ? "" : this.speaker;
    }

    public void setSpeaker(String str) {
        this.speaker = str;
    }

    public int getVideoid() {
        return this.videoid;
    }

    public void setVideoid(int i) {
        this.videoid = i;
    }

    public int getUserid() {
        return this.userid;
    }

    public void setUserid(int i) {
        this.userid = i;
    }

    public String getTranslate() {
        return BaseStringUtil.isEmpty(this.translate) ? "" : this.translate;
    }

    public void setTranslate(String str) {
        this.translate = str;
    }
}
