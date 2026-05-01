package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.MediaResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitVoiceSeparateJob;
import com.tencent.cos.xml.model.tag.MediaInfo;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitVoiceSeparateJobResponse {
    public SubmitVoiceSeparateJobResponseJobsDetail jobsDetail;

    public static class SubmitVoiceSeparateJobResponseInput {
        public String bucketId;
        public String object;
        public String region;
    }

    public static class SubmitVoiceSeparateJobResponseJobsDetail {
        public String code;
        public String creationTime;
        public String endTime;
        public SubmitVoiceSeparateJobResponseInput input;
        public String jobId;
        public String message;
        public SubmitVoiceSeparateJobResponseOperation operation;
        public String queueId;
        public String startTime;
        public String state;
        public String tag;
    }

    public static class SubmitVoiceSeparateJobResponseOperation {
        public String jobLevel;
        public MediaInfo mediaInfo;
        public MediaResult mediaResult;
        public SubmitVoiceSeparateJob.SubmitVoiceSeparateJobOutput output;
        public String templateId;
        public String templateName;
        public String userData;
        public SubmitVoiceSeparateJob.VoiceSeparate voiceSeparate;
    }
}
