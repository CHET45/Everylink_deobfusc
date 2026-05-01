package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.DigitalWatermark;
import com.tencent.cos.xml.model.p033ci.common.MediaResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitDigitalWatermarkJob;
import com.tencent.cos.xml.model.tag.MediaInfo;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitDigitalWatermarkJobResponse {
    public SubmitDigitalWatermarkJobResponseJobsDetail jobsDetail;

    public static class SubmitDigitalWatermarkJobResponseInput {
        public String bucketId;
        public String object;
        public String region;
    }

    public static class SubmitDigitalWatermarkJobResponseJobsDetail {
        public String code;
        public String creationTime;
        public String endTime;
        public SubmitDigitalWatermarkJobResponseInput input;
        public String jobId;
        public String message;
        public SubmitDigitalWatermarkJobResponseOperation operation;
        public String queueId;
        public String state;
        public String tag;
    }

    public static class SubmitDigitalWatermarkJobResponseOperation {
        public DigitalWatermark digitalWatermark;
        public String jobLevel;
        public MediaInfo mediaInfo;
        public MediaResult mediaResult;
        public SubmitDigitalWatermarkJob.SubmitDigitalWatermarkJobOutput output;
        public String userData;
    }
}
