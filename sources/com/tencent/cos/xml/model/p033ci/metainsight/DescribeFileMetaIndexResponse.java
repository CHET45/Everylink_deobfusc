package com.tencent.cos.xml.model.p033ci.metainsight;

import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class DescribeFileMetaIndexResponse {
    public List<FilesDetail> files;
    public String requestId;

    public static class FilesDetail {
        public String CustomId;
        public String cOSCRC64;
        public String cOSStorageClass;
        public String cacheControl;
        public String contentType;
        public String createTime;
        public Map<String, String> customLabels;
        public String eTag;
        public String fileModifiedTime;
        public String filename;
        public String mediaType;
        public String objectACL;
        public int size;
        public String uRI;
        public String updateTime;
    }
}
