package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.model.p033ci.audit.CreateStrategy;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class GetStrategyDetailResponse {
    public String requestId;
    public Strategy strategy;

    public static class Strategy {
        public String bizType;
        public CreateStrategy.Labels labels;
        public String name;
        public String service;
        public List<String> textLibs;
    }
}
