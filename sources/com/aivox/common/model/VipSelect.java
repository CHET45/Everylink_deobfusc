package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class VipSelect implements Serializable {
    boolean isSelect;
    String price;

    public String toString() {
        return "VipSelect{price='" + this.price + "', isSelect=" + this.isSelect + '}';
    }

    public VipSelect(String str, boolean z) {
        this.price = str;
        this.isSelect = z;
    }

    public VipSelect(String str) {
        this.price = str;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String str) {
        this.price = str;
    }

    public boolean isSelect() {
        return this.isSelect;
    }

    public void setSelect(boolean z) {
        this.isSelect = z;
    }
}
