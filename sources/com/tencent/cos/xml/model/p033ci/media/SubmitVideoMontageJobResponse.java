package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.MediaResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitVideoMontageJob;
import com.tencent.cos.xml.model.tag.MediaInfo;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitVideoMontageJobResponse {
    public SubmitVideoMontageJobResponseJobsDetail jobsDetail;

    public static class SubmitVideoMontageJobResponseInput {
        public String bucketId;
        public String object;
        public String region;
    }

    public static class SubmitVideoMontageJobResponseJobsDetail {
        public String code;
        public String creationTime;
        public String endTime;
        public SubmitVideoMontageJobResponseInput input;
        public String jobId;
        public String message;
        public SubmitVideoMontageJobResponseOperation operation;
        public String queueId;
        public String startTime;
        public String state;
        public String tag;
    }

    public static class SubmitVideoMontageJobResponseOperation {
        public String jobLevel;
        public MediaInfo mediaInfo;
        public MediaResult mediaResult;
        public SubmitVideoMontageJob.SubmitVideoMontageJobOutput output;
        public String templateId;
        public String templateName;
        public String userData;
        public SubmitVideoMontageJob.SubmitVideoMontageJobVideoMontage videoMontage;
    }
}
