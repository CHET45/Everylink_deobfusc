package com.tencent.cos.xml.model.p033ci.metainsight;

import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class DatasetSimpleQueryResponse {
    public List<AggregationsResult> aggregations;
    public List<FileResult> files;
    public String nextToken;
    public String requestId;

    public static class AggregationsResult {
        public String field;
        public List<Groups> groups;
        public String operation;
        public float value;
    }

    public static class FileResult {
        public String cOSCRC64;
        public String cOSStorageClass;
        public Map<String, String> cOSTagging;
        public int cOSTaggingCount;
        public Map<String, String> cOSUserMeta;
        public String cacheControl;
        public String contentDisposition;
        public String contentEncoding;
        public String contentLanguage;
        public String contentType;
        public String createTime;
        public String customId;
        public Map<String, String> customLabels;
        public String datasetName;
        public String eTag;
        public String fileModifiedTime;
        public String filename;
        public String mediaType;
        public String objectACL;
        public String objectId;
        public String serverSideEncryption;
        public int size;
        public String uRI;
        public String updateTime;
    }

    public static class Groups {
        public int count;
        public String value;
    }
}
