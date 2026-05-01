package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.MediaResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitMediaSegmentJob;
import com.tencent.cos.xml.model.tag.MediaInfo;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitMediaSegmentJobResponse {
    public SubmitMediaSegmentJobResponseJobsDetail jobsDetail;

    public static class SubmitMediaSegmentJobResponseInput {
        public String bucketId;
        public String object;
        public String region;
    }

    public static class SubmitMediaSegmentJobResponseJobsDetail {
        public String code;
        public String creationTime;
        public String endTime;
        public SubmitMediaSegmentJobResponseInput input;
        public String jobId;
        public String message;
        public SubmitMediaSegmentJobResponseOperation operation;
        public String queueId;
        public String startTime;
        public String state;
        public String tag;
    }

    public static class SubmitMediaSegmentJobResponseOperation {
        public String jobLevel;
        public MediaInfo mediaInfo;
        public MediaResult mediaResult;
        public SubmitMediaSegmentJob.SubmitMediaSegmentJobOutput output;
        public SubmitMediaSegmentJob.SubmitMediaSegmentJobSegment segment;
        public String userData;
    }
}
