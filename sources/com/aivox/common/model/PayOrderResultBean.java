package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class PayOrderResultBean implements Serializable {
    private String orderId;
    private String payUrl;

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String str) {
        this.orderId = str;
    }

    public String getPayUrl() {
        return this.payUrl;
    }

    public void setPayUrl(String str) {
        this.payUrl = str;
    }
}
