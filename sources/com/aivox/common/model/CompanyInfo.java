package com.aivox.common.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class CompanyInfo implements Serializable {
    String adminid;
    String company;
    String contactsphone;
    String detailedaddress;
    String dutyparagraph;

    /* JADX INFO: renamed from: id */
    int f229id;
    String image;
    String point;
    String region;
    String totalstorage;
    String usedstorage;

    public String toString() {
        return "CompanyInfo{id=" + this.f229id + ", company='" + this.company + "', dutyparagraph='" + this.dutyparagraph + "', contactsphone='" + this.contactsphone + "', region='" + this.region + "', detailedaddress='" + this.detailedaddress + "', adminid='" + this.adminid + "', point='" + this.point + "', usedstorage='" + this.usedstorage + "', totalstorage='" + this.totalstorage + "', image=" + this.image + '}';
    }

    public int getId() {
        return this.f229id;
    }

    public void setId(int i) {
        this.f229id = i;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String str) {
        this.company = str;
    }

    public String getDutyparagraph() {
        return this.dutyparagraph;
    }

    public void setDutyparagraph(String str) {
        this.dutyparagraph = str;
    }

    public String getContactsphone() {
        return this.contactsphone;
    }

    public void setContactsphone(String str) {
        this.contactsphone = str;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String str) {
        this.region = str;
    }

    public String getDetailedaddress() {
        return this.detailedaddress;
    }

    public void setDetailedaddress(String str) {
        this.detailedaddress = str;
    }

    public String getAdminid() {
        return this.adminid;
    }

    public void setAdminid(String str) {
        this.adminid = str;
    }

    public String getPoint() {
        return this.point;
    }

    public void setPoint(String str) {
        this.point = str;
    }

    public String getUsedstorage() {
        return this.usedstorage;
    }

    public void setUsedstorage(String str) {
        this.usedstorage = str;
    }

    public String getTotalstorage() {
        return this.totalstorage;
    }

    public void setTotalstorage(String str) {
        this.totalstorage = str;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String str) {
        this.image = str;
    }
}
