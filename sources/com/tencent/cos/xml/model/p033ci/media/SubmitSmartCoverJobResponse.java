package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.MediaResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitSmartCoverJob;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitSmartCoverJobResponse {
    public SubmitSmartCoverJobResponseJobsDetail jobsDetail;

    public static class SubmitSmartCoverJobResponseInput {
        public String bucketId;
        public String object;
        public String region;
    }

    public static class SubmitSmartCoverJobResponseJobsDetail {
        public String code;
        public String creationTime;
        public String endTime;
        public SubmitSmartCoverJobResponseInput input;
        public String jobId;
        public String message;
        public SubmitSmartCoverJobResponseOperation operation;
        public String queueId;
        public String state;
        public String tag;
    }

    public static class SubmitSmartCoverJobResponseOperation {
        public String jobLevel;
        public MediaResult mediaResult;
        public SubmitSmartCoverJob.SubmitSmartCoverJobOutput output;
        public String smartCover;
        public String userData;
    }
}
