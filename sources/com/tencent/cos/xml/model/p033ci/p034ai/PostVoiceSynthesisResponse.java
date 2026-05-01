package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.common.MediaInfo;
import com.tencent.cos.xml.model.p033ci.common.MediaResult;
import com.tencent.cos.xml.model.p033ci.p034ai.PostVoiceSynthesis;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class PostVoiceSynthesisResponse {
    public List<PostVoiceSynthesisResponseJobsDetail> jobsDetail;

    public static class PostVoiceSynthesisResponseJobsDetail {
        public String code;
        public String creationTime;
        public String endTime;
        public String jobId;
        public String message;
        public PostVoiceSynthesisResponseOperation operation;
        public String queueId;
        public String startTime;
        public String state;
        public String tag;
    }

    public static class PostVoiceSynthesisResponseOperation {
        public String jobLevel;
        public MediaInfo mediaInfo;
        public MediaResult mediaResult;
        public PostVoiceSynthesis.PostVoiceSynthesisOutput output;
        public String templateId;
        public String templateName;
        public PostVoiceSynthesis.PostVoiceSynthesisTtsConfig ttsConfig;
        public PostVoiceSynthesis.PostVoiceSynthesisTtsTpl ttsTpl;
        public String userData;
    }
}
