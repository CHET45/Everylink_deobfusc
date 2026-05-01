package com.tencent.cos.xml.model.p033ci.p034ai;

/* JADX INFO: loaded from: classes4.dex */
public class PostTranslation {
    public String callBack;
    public String callBackFormat;
    public CallBackMqConfig callBackMqConfig;
    public String callBackType;
    public PostTranslationInput input;
    public PostTranslationOperation operation;
    public String tag = "Translation";

    public static class CallBackMqConfig {
        public String mqMode;
        public String mqName;
        public String mqRegion;
    }

    public static class PostTranslationInput {
        public String basicType;
        public String lang;
        public String object;
        public String type;
    }

    public static class PostTranslationOperation {
        public String jobLevel;
        public String noNeedOutput;
        public PostTranslationOutput output;
        public PostTranslationTranslation translation;
        public String userData;
    }

    public static class PostTranslationOutput {
        public String bucket;
        public String object;
        public String region;
    }

    public static class PostTranslationTranslation {
        public String lang;
        public String type;
    }
}
