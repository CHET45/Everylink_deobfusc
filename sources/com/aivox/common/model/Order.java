package com.aivox.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Order implements Serializable {
    String commodity;
    List<Model> commodityOrders = new ArrayList();
    int commoditytype;
    String createtime;

    /* JADX INFO: renamed from: id */
    int f255id;
    String money;
    String outtradeno;
    String overtime;
    int play;
    String point;
    String tradeno;
    int userid;
    String username;

    public String getPoint() {
        return this.point;
    }

    public void setPoint(String str) {
        this.point = str;
    }

    public int getId() {
        return this.f255id;
    }

    public void setId(int i) {
        this.f255id = i;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String str) {
        this.username = str;
    }

    public int getUserid() {
        return this.userid;
    }

    public void setUserid(int i) {
        this.userid = i;
    }

    public String getCommodity() {
        return this.commodity;
    }

    public void setCommodity(String str) {
        this.commodity = str;
    }

    public int getCommoditytype() {
        return this.commoditytype;
    }

    public void setCommoditytype(int i) {
        this.commoditytype = i;
    }

    public String getTradeno() {
        return this.tradeno;
    }

    public void setTradeno(String str) {
        this.tradeno = str;
    }

    public String getOuttradeno() {
        return this.outtradeno;
    }

    public void setOuttradeno(String str) {
        this.outtradeno = str;
    }

    public String getMoney() {
        return this.money;
    }

    public void setMoney(String str) {
        this.money = str;
    }

    public int getPlay() {
        return this.play;
    }

    public void setPlay(int i) {
        this.play = i;
    }

    public String getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(String str) {
        this.createtime = str;
    }

    public String getOvertime() {
        return this.overtime;
    }

    public void setOvertime(String str) {
        this.overtime = str;
    }

    public List<Model> getCommodityOrders() {
        return this.commodityOrders;
    }

    public void setCommodityOrders(List<Model> list) {
        this.commodityOrders = list;
    }
}
