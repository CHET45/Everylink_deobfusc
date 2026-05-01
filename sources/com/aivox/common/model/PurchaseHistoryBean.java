package com.aivox.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PurchaseHistoryBean extends PageBean {
    private List<OrderDetail> records = new ArrayList();

    public List<OrderDetail> getRecords() {
        return this.records;
    }

    public void setRecords(List<OrderDetail> list) {
        this.records = list;
    }

    public static class OrderDetail implements Serializable {
        private Double amount;
        private String createdAt;
        private String productName;

        public String getCreatedAt() {
            return this.createdAt;
        }

        public void setCreatedAt(String str) {
            this.createdAt = str;
        }

        public String getProductName() {
            return this.productName;
        }

        public void setProductName(String str) {
            this.productName = str;
        }

        public Double getAmount() {
            return this.amount;
        }

        public void setAmount(Double d) {
            this.amount = d;
        }
    }
}
