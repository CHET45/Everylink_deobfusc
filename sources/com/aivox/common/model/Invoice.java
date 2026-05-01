package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class Invoice implements Serializable {
    String companyName;
    String companyNum;
    String createTime;
    String email;
    String money;
    int status;

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String str) {
        this.companyName = str;
    }

    public String getCompanyNum() {
        return this.companyNum;
    }

    public void setCompanyNum(String str) {
        this.companyNum = str;
    }

    public String getMoney() {
        return this.money;
    }

    public void setMoney(String str) {
        this.money = str;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }
}
