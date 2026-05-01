package com.aivox.common.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/* JADX INFO: loaded from: classes.dex */
public class DemoMultiEntity implements MultiItemEntity {
    public static final int DATE = 0;
    public static final int PIC = 1;
    private String date;
    private DemoPicBean demoPicBean;
    private int itemType;
    private int spanSize;

    public DemoMultiEntity(int i, int i2, DemoPicBean demoPicBean) {
        this.itemType = i;
        this.spanSize = i2;
        this.demoPicBean = demoPicBean;
    }

    public DemoMultiEntity(int i, int i2, String str) {
        this.itemType = i;
        this.spanSize = i2;
        this.date = str;
    }

    public DemoPicBean getDemoPicBean() {
        return this.demoPicBean;
    }

    public void setDemoPicBean(DemoPicBean demoPicBean) {
        this.demoPicBean = demoPicBean;
    }

    public void setItemType(int i) {
        this.itemType = i;
    }

    public int getSpanSize() {
        return this.spanSize;
    }

    public void setSpanSize(int i) {
        this.spanSize = i;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return this.itemType;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String str) {
        this.date = str;
    }
}
