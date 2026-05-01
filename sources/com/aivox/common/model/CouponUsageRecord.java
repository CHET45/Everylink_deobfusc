package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class CouponUsageRecord implements Serializable {
    String consumptiontype;
    String duration;
    String endtime;
    String enterpriseid;

    /* JADX INFO: renamed from: id */
    int f232id;
    String identity;
    String nickname;
    String point;
    String userid;

    public String toString() {
        return "CouponUsageRecord{id=" + this.f232id + ", consumptiontype='" + this.consumptiontype + "', duration='" + this.duration + "', endtime='" + this.endtime + "', point='" + this.point + "', nickname='" + this.nickname + "', identity='" + this.identity + "', userid='" + this.userid + "', enterpriseid='" + this.enterpriseid + "'}";
    }

    public int getId() {
        return this.f232id;
    }

    public void setId(int i) {
        this.f232id = i;
    }

    public String getConsumptiontype() {
        return this.consumptiontype;
    }

    public void setConsumptiontype(String str) {
        this.consumptiontype = str;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String str) {
        this.duration = str;
    }

    public String getEndtime() {
        return this.endtime;
    }

    public void setEndtime(String str) {
        this.endtime = str;
    }

    public String getPoint() {
        return this.point;
    }

    public void setPoint(String str) {
        this.point = str;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String str) {
        this.nickname = str;
    }

    public String getIdentity() {
        return this.identity;
    }

    public void setIdentity(String str) {
        this.identity = str;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String str) {
        this.userid = str;
    }

    public String getEnterpriseid() {
        return this.enterpriseid;
    }

    public void setEnterpriseid(String str) {
        this.enterpriseid = str;
    }
}
