package com.tencent.cos.xml.model.p033ci.audit;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class GetAuditTextlibListResponse {
    public List<Libs> libs;
    public String requestId;
    public int totalCount;

    public static class Libs {
        public String createTime;
        public String libID;
        public String libName;
        public String matchType;
        public List<Strategies> strategies;
        public String suggestion;
    }

    public static class Strategies {
        public String bizType;
        public String bucket;
        public String service;
    }
}
