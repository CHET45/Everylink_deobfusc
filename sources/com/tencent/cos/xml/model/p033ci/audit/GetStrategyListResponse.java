package com.tencent.cos.xml.model.p033ci.audit;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class GetStrategyListResponse {
    public String requestId;
    public List<Strategies> strategies;
    public int totalCount;

    public static class Strategies {
        public String bizType;
        public String createTime;
        public String isDefault;
        public String name;
        public String service;
    }
}
