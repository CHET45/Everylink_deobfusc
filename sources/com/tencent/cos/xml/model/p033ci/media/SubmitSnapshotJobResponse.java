package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.MediaResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitSnapshotJob;
import com.tencent.cos.xml.model.p033ci.media.TemplateSnapshot;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitSnapshotJobResponse {
    public SubmitSnapshotJobResponseJobsDetail jobsDetail;

    public static class SubmitSnapshotJobResponseInput {
        public String bucketId;
        public String object;
        public String region;
    }

    public static class SubmitSnapshotJobResponseJobsDetail {
        public String code;
        public String creationTime;
        public String endTime;
        public SubmitSnapshotJobResponseInput input;
        public String jobId;
        public String message;
        public SubmitSnapshotJobResponseOperation operation;
        public String queueId;
        public String startTime;
        public String state;
        public String tag;
    }

    public static class SubmitSnapshotJobResponseOperation {
        public String jobLevel;
        public MediaResult mediaResult;
        public SubmitSnapshotJob.SubmitSnapshotJobOutput output;
        public TemplateSnapshot.TemplateSnapshotSnapshot snapshot;
        public String templateId;
        public String templateName;
        public String userData;
    }
}
