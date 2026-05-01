package com.aivox.common.model;

/* JADX INFO: loaded from: classes.dex */
public class PayByWechat {
    String order_sn;
    WxPayModel sign;

    public String getOrder_sn() {
        return this.order_sn;
    }

    public void setOrder_sn(String str) {
        this.order_sn = str;
    }

    public WxPayModel getSign() {
        return this.sign;
    }

    public void setSign(WxPayModel wxPayModel) {
        this.sign = wxPayModel;
    }
}
