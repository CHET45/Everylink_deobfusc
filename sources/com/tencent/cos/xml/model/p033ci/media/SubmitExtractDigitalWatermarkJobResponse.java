package com.tencent.cos.xml.model.p033ci.media;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitExtractDigitalWatermarkJobResponse {
    public SubmitExtractDigitalWatermarkJobResponseJobsDetail jobsDetail;

    public static class SubmitExtractDigitalWatermarkJobResponseExtractDigitalWatermark {
        public String message;
        public String type;
        public String version;
    }

    public static class SubmitExtractDigitalWatermarkJobResponseInput {
        public String bucketId;
        public String object;
        public String region;
    }

    public static class SubmitExtractDigitalWatermarkJobResponseJobsDetail {
        public String code;
        public String creationTime;
        public String endTime;
        public SubmitExtractDigitalWatermarkJobResponseInput input;
        public String jobId;
        public String message;
        public SubmitExtractDigitalWatermarkJobResponseOperation operation;
        public String queueId;
        public String state;
        public String tag;
    }

    public static class SubmitExtractDigitalWatermarkJobResponseOperation {
        public SubmitExtractDigitalWatermarkJobResponseExtractDigitalWatermark extractDigitalWatermark;
        public String jobLevel;
        public String userData;
    }
}
