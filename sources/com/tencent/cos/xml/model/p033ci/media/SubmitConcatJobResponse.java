package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.MediaResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitConcatJob;
import com.tencent.cos.xml.model.tag.MediaInfo;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitConcatJobResponse {
    public SubmitConcatJobResponseJobsDetail jobsDetail;

    public static class SubmitConcatJobResponseInput {
        public String bucketId;
        public String object;
        public String region;
    }

    public static class SubmitConcatJobResponseJobsDetail {
        public String code;
        public String creationTime;
        public String endTime;
        public SubmitConcatJobResponseInput input;
        public String jobId;
        public String message;
        public SubmitConcatJobResponseOperation operation;
        public String queueId;
        public String startTime;
        public String state;
        public String tag;
    }

    public static class SubmitConcatJobResponseOperation {
        public SubmitConcatJob.SubmitConcatJobConcatTemplate concatTemplate;
        public String jobLevel;
        public MediaInfo mediaInfo;
        public MediaResult mediaResult;
        public SubmitConcatJob.SubmitConcatJobOutput output;
        public String templateId;
        public String templateName;
        public String userData;
    }
}
