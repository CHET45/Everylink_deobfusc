package com.aivox.common.model;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RecycleBinList {
    int current;
    boolean hitCount;
    boolean optimizeCountSql;
    int pages;
    List<AudioInfoBean> records = new ArrayList();
    boolean searchCount;
    int size;
    int total;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int i) {
        this.total = i;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int i) {
        this.size = i;
    }

    public int getCurrent() {
        return this.current;
    }

    public void setCurrent(int i) {
        this.current = i;
    }

    public int getPages() {
        return this.pages;
    }

    public void setPages(int i) {
        this.pages = i;
    }

    public boolean isOptimizeCountSql() {
        return this.optimizeCountSql;
    }

    public void setOptimizeCountSql(boolean z) {
        this.optimizeCountSql = z;
    }

    public boolean isHitCount() {
        return this.hitCount;
    }

    public void setHitCount(boolean z) {
        this.hitCount = z;
    }

    public boolean isSearchCount() {
        return this.searchCount;
    }

    public void setSearchCount(boolean z) {
        this.searchCount = z;
    }

    public List<AudioInfoBean> getRecords() {
        return this.records;
    }

    public void setRecords(List<AudioInfoBean> list) {
        this.records = list;
    }
}
