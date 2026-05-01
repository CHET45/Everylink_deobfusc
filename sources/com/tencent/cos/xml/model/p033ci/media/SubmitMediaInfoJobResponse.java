package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.tag.MediaInfo;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitMediaInfoJobResponse {
    public SubmitMediaInfoJobResponseJobsDetail jobsDetail;

    public static class SubmitMediaInfoJobResponseInput {
        public String bucketId;
        public String object;
        public String region;
    }

    public static class SubmitMediaInfoJobResponseJobsDetail {
        public String code;
        public String creationTime;
        public String endTime;
        public SubmitMediaInfoJobResponseInput input;
        public String jobId;
        public String message;
        public SubmitMediaInfoJobResponseOperation operation;
        public String queueId;
        public String startTime;
        public String state;
        public String tag;
    }

    public static class SubmitMediaInfoJobResponseOperation {
        public String jobLevel;
        public MediaInfo mediaInfo;
        public String userData;
    }
}
