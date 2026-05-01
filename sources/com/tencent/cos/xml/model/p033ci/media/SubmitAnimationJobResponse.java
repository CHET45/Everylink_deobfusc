package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.MediaResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitAnimationJob;
import com.tencent.cos.xml.model.tag.MediaInfo;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitAnimationJobResponse {
    public SubmitAnimationJobResponseJobsDetail jobsDetail;

    public static class SubmitAnimationJobResponseInput {
        public String bucketId;
        public String object;
        public String region;
    }

    public static class SubmitAnimationJobResponseJobsDetail {
        public String code;
        public String creationTime;
        public String endTime;
        public SubmitAnimationJobResponseInput input;
        public String jobId;
        public String message;
        public SubmitAnimationJobResponseOperation operation;
        public String queueId;
        public String startTime;
        public String state;
        public String tag;
    }

    public static class SubmitAnimationJobResponseOperation {
        public SubmitAnimationJob.SubmitAnimationJobAnimation animation;
        public String jobLevel;
        public MediaInfo mediaInfo;
        public MediaResult mediaResult;
        public SubmitAnimationJob.SubmitAnimationJobOutput output;
        public String templateId;
        public String templateName;
        public String userData;
    }
}
