package com.aivox.base.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class Identity implements Serializable {
    String adminid;
    String company;
    String companyimage;
    String enterpriseid;

    /* JADX INFO: renamed from: id */
    int f188id;
    String identity;
    boolean isSelect;
    boolean ivDelShow;
    String nickname;
    String phone;
    String pinYinHeadChar;
    int pointauthority;
    String userid;
    String userimage;
    String usernickname;

    public String toString() {
        return "Identity{id=" + this.f188id + ", userid='" + this.userid + "', identity='" + this.identity + "', enterpriseid='" + this.enterpriseid + "', adminid='" + this.adminid + "', company='" + this.company + "', companyimage='" + this.companyimage + "', isSelect=" + this.isSelect + ", phone='" + this.phone + "', nickname='" + this.nickname + "', usernickname='" + this.usernickname + "', userimage='" + this.userimage + "', pointauthority=" + this.pointauthority + ", pinYinHeadChar='" + this.pinYinHeadChar + "', ivDelShow=" + this.ivDelShow + '}';
    }

    public int getId() {
        return this.f188id;
    }

    public void setId(int i) {
        this.f188id = i;
    }

    public String getUsernickname() {
        return this.usernickname;
    }

    public void setUsernickname(String str) {
        this.usernickname = str;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String str) {
        this.nickname = str;
    }

    public String getUserimage() {
        return this.userimage;
    }

    public void setUserimage(String str) {
        this.userimage = str;
    }

    public int getPointauthority() {
        return this.pointauthority;
    }

    public void setPointauthority(int i) {
        this.pointauthority = i;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public String getPinYinHeadChar() {
        return this.pinYinHeadChar;
    }

    public void setPinYinHeadChar(String str) {
        this.pinYinHeadChar = str;
    }

    public boolean isIvDelShow() {
        return this.ivDelShow;
    }

    public void setIvDelShow(boolean z) {
        this.ivDelShow = z;
    }

    public boolean isSelect() {
        return this.isSelect;
    }

    public void setSelect(boolean z) {
        this.isSelect = z;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String str) {
        this.company = str;
    }

    public String getCompanyimage() {
        return this.companyimage;
    }

    public void setCompanyimage(String str) {
        this.companyimage = str;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String str) {
        this.userid = str;
    }

    public String getIdentity() {
        return this.identity;
    }

    public void setIdentity(String str) {
        this.identity = str;
    }

    public String getEnterpriseid() {
        return this.enterpriseid;
    }

    public void setEnterpriseid(String str) {
        this.enterpriseid = str;
    }

    public String getAdminid() {
        return this.adminid;
    }

    public void setAdminid(String str) {
        this.adminid = str;
    }
}
