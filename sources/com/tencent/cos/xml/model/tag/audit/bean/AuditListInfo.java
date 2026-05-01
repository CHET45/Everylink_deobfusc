package com.tencent.cos.xml.model.tag.audit.bean;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class AuditListInfo {
    public List<ListResults> listResults;

    public static class ListResults {
        public String entity;
        public String listName;
        public int listType;
    }
}
