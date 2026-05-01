package com.aivox.common.model;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class OrderList extends PageBean {
    List<Model> orders = new ArrayList();
    List<Order> records = new ArrayList();

    public List<Model> getOrders() {
        return this.orders;
    }

    public void setOrders(List<Model> list) {
        this.orders = list;
    }

    public List<Order> getRecords() {
        return this.records;
    }

    public void setRecords(List<Order> list) {
        this.records = list;
    }
}
