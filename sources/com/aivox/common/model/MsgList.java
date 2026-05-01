package com.aivox.common.model;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MsgList extends PageBean {
    List<Msg> records = new ArrayList();

    public List<Msg> getRecords() {
        return this.records;
    }

    public void setRecords(List<Msg> list) {
        this.records = list;
    }
}
