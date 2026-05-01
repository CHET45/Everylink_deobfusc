package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.common.NoiseReduction;
import com.tencent.cos.xml.model.p033ci.p034ai.PostNoiseReduction;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class PostNoiseReductionResponse {
    public List<PostNoiseReductionResponseJobsDetail> jobsDetail;

    public static class PostNoiseReductionResponseInput {
        public String bucket;
        public String object;
        public String region;
    }

    public static class PostNoiseReductionResponseJobsDetail {
        public String code;
        public String creationTime;
        public String endTime;
        public PostNoiseReductionResponseInput input;
        public String jobId;
        public String message;
        public PostNoiseReductionResponseOperation operation;
        public String queueId;
        public String state;
        public String tag;
    }

    public static class PostNoiseReductionResponseOperation {
        public String jobLevel;
        public NoiseReduction noiseReduction;
        public PostNoiseReduction.PostNoiseReductionOutput output;
        public String templateId;
        public String templateName;
        public String userData;
    }
}
