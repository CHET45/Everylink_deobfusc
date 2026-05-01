package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class PricePackageList implements Serializable {
    private String applePayId;

    /* JADX INFO: renamed from: id */
    private Integer f256id;
    private Double originalPrice;
    private Double price;
    private String priceUuid;
    private String productName;
    private String validUtil;
    private Integer vipLevel;

    public Integer getId() {
        return this.f256id;
    }

    public void setId(Integer num) {
        this.f256id = num;
    }

    public String getPriceUuid() {
        return this.priceUuid;
    }

    public void setPriceUuid(String str) {
        this.priceUuid = str;
    }

    public String getApplePayId() {
        return this.applePayId;
    }

    public void setApplePayId(String str) {
        this.applePayId = str;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String str) {
        this.productName = str;
    }

    public Double getOriginalPrice() {
        return this.originalPrice;
    }

    public void setOriginalPrice(Double d) {
        this.originalPrice = d;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double d) {
        this.price = d;
    }

    public Integer getVipLevel() {
        return this.vipLevel;
    }

    public void setVipLevel(Integer num) {
        this.vipLevel = num;
    }

    public String getValidUtil() {
        return this.validUtil;
    }

    public void setValidUtil(String str) {
        this.validUtil = str;
    }
}
