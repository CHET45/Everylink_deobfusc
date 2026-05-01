package com.aivox.common.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class WxPayModel implements Serializable {
    String appid;
    String noncestr;
    String out_trade_no;

    @SerializedName("package")
    String packageStr;
    String partnerid;
    String prepayid;
    String sign;
    String timestamp;

    public String toString() {
        return "WxPayModel{appid='" + this.appid + "', partnerid='" + this.partnerid + "', prepayid='" + this.prepayid + "', packageStr='" + this.packageStr + "', noncestr='" + this.noncestr + "', timestamp='" + this.timestamp + "', sign='" + this.sign + "'}";
    }

    public String getOut_trade_no() {
        return this.out_trade_no;
    }

    public void setOut_trade_no(String str) {
        this.out_trade_no = str;
    }

    public String getAppid() {
        return this.appid;
    }

    public void setAppid(String str) {
        this.appid = str;
    }

    public String getPartnerid() {
        return this.partnerid;
    }

    public void setPartnerid(String str) {
        this.partnerid = str;
    }

    public String getPrepayid() {
        return this.prepayid;
    }

    public void setPrepayid(String str) {
        this.prepayid = str;
    }

    public String getPackageStr() {
        return this.packageStr;
    }

    public void setPackageStr(String str) {
        this.packageStr = str;
    }

    public String getNoncestr() {
        return this.noncestr;
    }

    public void setNoncestr(String str) {
        this.noncestr = str;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String str) {
        this.timestamp = str;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String str) {
        this.sign = str;
    }
}
