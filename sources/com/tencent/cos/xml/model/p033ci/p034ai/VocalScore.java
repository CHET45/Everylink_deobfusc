package com.tencent.cos.xml.model.p033ci.p034ai;

/* JADX INFO: loaded from: classes4.dex */
public class VocalScore {
    public String callBack;
    public String callBackFormat;
    public CallBackMqConfig callBackMqConfig;
    public String callBackType;
    public VocalScoreInput input;
    public VocalScoreOperation operation;
    public String tag = "VocalScore";

    public static class CallBackMqConfig {
        public String mqMode;
        public String mqName;
        public String mqRegion;
    }

    public static class VocalScoreInput {
        public String object;
    }

    public static class VocalScoreOperation {
        public String jobLevel;
        public String userData;
        public VocalScoreVocalScore vocalScore;
    }

    public static class VocalScoreVocalScore {
        public String standardObject;
    }
}
