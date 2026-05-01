package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UserInfo implements Serializable {
    private String avatar;
    private String birthday;
    private String email;
    private int gender;
    private int isPassword;
    private String nickname;
    private String phone;
    private String uuid;
    private boolean vip;
    private String vipExpire;

    public String toString() {
        return "UserInfo{uuid=" + this.uuid + ", avatar='" + this.avatar + "', nickname='" + this.nickname + "', birthday='" + this.birthday + "', gender=" + this.gender + ", phone='" + this.phone + "', email='" + this.email + '}';
    }

    public boolean isVip() {
        return this.vip;
    }

    public void setVip(boolean z) {
        this.vip = z;
    }

    public String getVipExpire() {
        return this.vipExpire;
    }

    public void setVipExpire(String str) {
        this.vipExpire = str;
    }

    public String getUuid() {
        String str = this.uuid;
        return str == null ? "" : str;
    }

    public void setUuid(String str) {
        this.uuid = str;
    }

    public String getAvatar() {
        String str = this.avatar;
        return str == null ? "" : str;
    }

    public void setAvatar(String str) {
        this.avatar = str;
    }

    public String getNickname() {
        String str = this.nickname;
        return str == null ? "" : str;
    }

    public void setNickname(String str) {
        this.nickname = str;
    }

    public String getBirthday() {
        String str = this.birthday;
        return str == null ? "" : str;
    }

    public void setBirthday(String str) {
        this.birthday = str;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int i) {
        this.gender = i;
    }

    public String getPhone() {
        String str = this.phone;
        return str == null ? "" : str;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public String getEmail() {
        String str = this.email;
        return str == null ? "" : str;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public int getIsPassword() {
        return this.isPassword;
    }

    public void setIsPassword(int i) {
        this.isPassword = i;
    }
}
