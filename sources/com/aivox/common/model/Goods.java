package com.aivox.common.model;

/* JADX INFO: loaded from: classes.dex */
public class Goods {
    String commodity;
    String commodityid;
    String commoditytype;

    public Goods(String str, String str2, String str3) {
        this.commodity = str;
        this.commodityid = str2;
        this.commoditytype = str3;
    }

    public String getCommodityid() {
        return this.commodityid;
    }

    public void setCommodityid(String str) {
        this.commodityid = str;
    }

    public String getCommodity() {
        return this.commodity;
    }

    public void setCommodity(String str) {
        this.commodity = str;
    }

    public String getCommoditytype() {
        return this.commoditytype;
    }

    public void setCommoditytype(String str) {
        this.commoditytype = str;
    }
}
