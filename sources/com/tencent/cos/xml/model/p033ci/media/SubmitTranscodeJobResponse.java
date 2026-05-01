package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.DigitalWatermark;
import com.tencent.cos.xml.model.p033ci.common.MediaResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitTranscodeJob;
import com.tencent.cos.xml.model.p033ci.media.TemplateWatermark;
import com.tencent.cos.xml.model.tag.MediaInfo;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitTranscodeJobResponse {
    public SubmitTranscodeJobResponseJobsDetail jobsDetail;

    public static class SubmitTranscodeJobResponseInput {
        public String bucketId;
        public String object;
        public String region;
    }

    public static class SubmitTranscodeJobResponseJobsDetail {
        public String code;
        public String creationTime;
        public String endTime;
        public SubmitTranscodeJobResponseInput input;
        public String jobId;
        public String message;
        public SubmitTranscodeJobResponseOperation operation;
        public String progress;
        public String queueId;
        public String startTime;
        public String state;
        public String tag;
    }

    public static class SubmitTranscodeJobResponseOperation {
        public DigitalWatermark digitalWatermark;
        public String jobLevel;
        public MediaInfo mediaInfo;
        public MediaResult mediaResult;
        public SubmitTranscodeJob.SubmitTranscodeJobOutput output;
        public SubmitTranscodeJob.SubmitTranscodeJobRemoveWatermark removeWatermark;
        public String templateId;
        public String templateName;
        public SubmitTranscodeJob.SubmitTranscodeJobTranscode transcode;
        public String userData;
        public List<TemplateWatermark.Watermark> watermark;
        public List<String> watermarkTemplateId;
    }
}
