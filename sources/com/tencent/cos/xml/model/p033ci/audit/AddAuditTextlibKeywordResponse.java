package com.tencent.cos.xml.model.p033ci.audit;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class AddAuditTextlibKeywordResponse {
    public String requestId;
    public List<Results> results;

    public static class Results {
        public String code;
        public String content;
        public String errMsg;
        public String keywordID;
        public String label;
        public String remark;
    }
}
