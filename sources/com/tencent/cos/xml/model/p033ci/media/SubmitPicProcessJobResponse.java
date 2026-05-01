package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.PicProcess;
import com.tencent.cos.xml.model.p033ci.media.SubmitPicProcessJob;
import com.tencent.cos.xml.model.tag.pic.PicUploadResult;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitPicProcessJobResponse {
    public SubmitPicProcessJobResponseJobsDetail jobsDetail;

    public static class SubmitPicProcessJobResponseInput {
        public String bucketId;
        public String object;
        public String region;
    }

    public static class SubmitPicProcessJobResponseJobsDetail {
        public String code;
        public String creationTime;
        public String endTime;
        public SubmitPicProcessJobResponseInput input;
        public String jobId;
        public String message;
        public SubmitPicProcessJobResponseOperation operation;
        public String queueId;
        public String startTime;
        public String state;
        public String tag;
    }

    public static class SubmitPicProcessJobResponseOperation {
        public SubmitPicProcessJob.SubmitPicProcessJobOutput output;
        public PicProcess picProcess;
        public PicUploadResult picProcessResult;
        public String templateId;
        public String templateName;
        public String userData;
    }
}
