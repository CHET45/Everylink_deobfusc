package com.aivox.common_ui.adview;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class AdvertInfo implements Serializable {
    String AddTime;
    String Brief;
    String EndTime;
    String FilePath;
    int GroupID;
    int HtmlID;
    String Pic1;
    String Pic2;
    String Pic3;
    int Sequence;
    String StartTiem;
    String SubTitle;
    String Title;
    String Url;

    public String getFilePath() {
        return this.FilePath;
    }

    public void setFilePath(String str) {
        this.FilePath = str;
    }

    public int getHtmlID() {
        return this.HtmlID;
    }

    public void setHtmlID(int i) {
        this.HtmlID = i;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String str) {
        this.Title = str;
    }

    public String getSubTitle() {
        return this.SubTitle;
    }

    public void setSubTitle(String str) {
        this.SubTitle = str;
    }

    public String getBrief() {
        return this.Brief;
    }

    public void setBrief(String str) {
        this.Brief = str;
    }

    public String getUrl() {
        return this.Url;
    }

    public void setUrl(String str) {
        this.Url = str;
    }

    public String getPic1() {
        return this.Pic1;
    }

    public void setPic1(String str) {
        this.Pic1 = str;
    }

    public String getPic2() {
        return this.Pic2;
    }

    public void setPic2(String str) {
        this.Pic2 = str;
    }

    public String getPic3() {
        return this.Pic3;
    }

    public void setPic3(String str) {
        this.Pic3 = str;
    }

    public int getGroupID() {
        return this.GroupID;
    }

    public void setGroupID(int i) {
        this.GroupID = i;
    }

    public int getSequence() {
        return this.Sequence;
    }

    public void setSequence(int i) {
        this.Sequence = i;
    }

    public String getStartTiem() {
        return this.StartTiem;
    }

    public void setStartTiem(String str) {
        this.StartTiem = str;
    }

    public String getEndTime() {
        return this.EndTime;
    }

    public void setEndTime(String str) {
        this.EndTime = str;
    }

    public String getAddTime() {
        return this.AddTime;
    }

    public void setAddTime(String str) {
        this.AddTime = str;
    }
}
