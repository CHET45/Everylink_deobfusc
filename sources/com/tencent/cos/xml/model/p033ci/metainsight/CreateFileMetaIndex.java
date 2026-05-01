package com.tencent.cos.xml.model.p033ci.metainsight;

import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class CreateFileMetaIndex {
    public String callback;
    public String datasetName;
    public File file;

    public static class File {
        public String contentType;
        public String customId;
        public Map<String, String> customLabels;
        public String key;
        public int maxFaceNum;
        public String mediaType;
        public List<Persons> persons;
        public String uRI;
        public String value;
    }

    public static class Persons {
        public String personId;
    }
}
