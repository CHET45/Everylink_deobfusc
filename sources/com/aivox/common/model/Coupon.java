package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class Coupon implements Serializable {
    String givepoint;

    /* JADX INFO: renamed from: id */
    int f231id;
    boolean isSelect;
    int onSaleType = 1;
    String point;
    String price;
    String totalpoint;
    String transcribetime;
    String type;

    public String toString() {
        return "Coupon{id=" + this.f231id + ", price='" + this.price + "', type='" + this.type + "', point='" + this.point + "', givepoint='" + this.givepoint + "', totalpoint='" + this.totalpoint + "', isSelect=" + this.isSelect + '}';
    }

    public String getTranscribetime() {
        return this.transcribetime;
    }

    public void setTranscribetime(String str) {
        this.transcribetime = str;
    }

    public int getOnSaleType() {
        return this.onSaleType;
    }

    public void setOnSaleType(int i) {
        this.onSaleType = i;
    }

    public boolean isSelect() {
        return this.isSelect;
    }

    public void setSelect(boolean z) {
        this.isSelect = z;
    }

    public int getId() {
        return this.f231id;
    }

    public void setId(int i) {
        this.f231id = i;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String str) {
        this.price = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getPoint() {
        return this.point;
    }

    public void setPoint(String str) {
        this.point = str;
    }

    public String getGivepoint() {
        return this.givepoint;
    }

    public void setGivepoint(String str) {
        this.givepoint = str;
    }

    public String getTotalpoint() {
        return this.totalpoint;
    }

    public void setTotalpoint(String str) {
        this.totalpoint = str;
    }
}
