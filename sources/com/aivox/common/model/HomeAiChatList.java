package com.aivox.common.model;

import com.aivox.common.p003db.entity.HomeAiChatEntity;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class HomeAiChatList {
    private int current;
    private int pages;
    private List<HomeAiChatEntity> records;
    private int size;
    private int total;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int i) {
        this.total = i;
    }

    public int getCurrent() {
        return this.current;
    }

    public void setCurrent(int i) {
        this.current = i;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int i) {
        this.size = i;
    }

    public int getPages() {
        return this.pages;
    }

    public void setPages(int i) {
        this.pages = i;
    }

    public List<HomeAiChatEntity> getRecords() {
        return this.records;
    }

    public void setRecords(List<HomeAiChatEntity> list) {
        this.records = list;
    }
}
