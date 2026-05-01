package com.tencent.cos.xml.model.p033ci.audit;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class GetAuditTextlibKeywordListResponse {
    public List<Keywords> keywords;
    public String requestId;
    public int totalCount;

    public static class Keywords {
        public String content;
        public String createTime;
        public String keywordID;
        public String label;
        public String remark;
    }
}
