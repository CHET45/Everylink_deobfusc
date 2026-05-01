package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.PostTranslation;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class PostTranslationResponse {
    public List<PostTranslationResponseJobsDetail> jobsDetail;

    public static class PostTranslationResponseAITranslateResult {
        public String result;
    }

    public static class PostTranslationResponseJobsDetail {
        public String code;
        public String creationTime;
        public String endTime;
        public PostTranslation.PostTranslationInput input;
        public String jobId;
        public String message;
        public PostTranslationResponseOperation operation;
        public String queueId;
        public String startTime;
        public String state;
        public String tag;
    }

    public static class PostTranslationResponseOperation {
        public PostTranslationResponseAITranslateResult aITranslateResult;
        public String jobLevel;
        public PostTranslation.PostTranslationOutput output;
        public PostTranslation.PostTranslationTranslation translation;
        public String userData;
    }
}
