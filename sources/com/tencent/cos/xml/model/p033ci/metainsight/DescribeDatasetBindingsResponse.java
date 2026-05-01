package com.tencent.cos.xml.model.p033ci.metainsight;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class DescribeDatasetBindingsResponse {
    public List<Binding> bindings;
    public String nextToken;
    public String requestId;

    public static class Binding {
        public String createTime;
        public String datasetName;
        public String detail;
        public String state;
        public String uRI;
        public String updateTime;
    }
}
