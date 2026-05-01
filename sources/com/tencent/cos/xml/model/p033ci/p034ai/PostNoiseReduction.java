package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.common.CallBackMqConfig;
import com.tencent.cos.xml.model.p033ci.common.NoiseReduction;

/* JADX INFO: loaded from: classes4.dex */
public class PostNoiseReduction {
    public String callBack;
    public String callBackFormat;
    public CallBackMqConfig callBackMqConfig;
    public String callBackType;
    public PostNoiseReductionInput input;
    public PostNoiseReductionOperation operation;
    public String tag = "NoiseReduction";

    public static class PostNoiseReductionInput {
        public String object;
    }

    public static class PostNoiseReductionOperation {
        public String jobLevel;
        public NoiseReduction noiseReduction;
        public PostNoiseReductionOutput output;
        public String templateId;
        public String userData;
    }

    public static class PostNoiseReductionOutput {
        public String bucket;
        public String object;
        public String region;
    }
}
