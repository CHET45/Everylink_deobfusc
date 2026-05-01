package com.tencent.cos.xml.model.p033ci.metainsight;

/* JADX INFO: loaded from: classes4.dex */
public class DeleteDatasetResponse {
    public Dataset dataset;
    public String requestId;

    public static class Dataset {
        public int bindCount;
        public String createTime;
        public String datasetName;
        public String description;
        public int fileCount;
        public String templateId;
        public int totalFileSize;
        public String updateTime;
    }
}
