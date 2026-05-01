package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.TemplateSmartCover;
import com.tencent.cos.xml.model.tag.CallBackMqConfig;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitSmartCoverJob {
    public String callBack;
    public String callBackFormat;
    public CallBackMqConfig callBackMqConfig;
    public String callBackType;
    public SubmitSmartCoverJobInput input;
    public SubmitSmartCoverJobOperation operation;
    public String tag = "SmartCover";

    public static class SubmitSmartCoverJobInput {
        public String object;
    }

    public static class SubmitSmartCoverJobOperation {
        public String jobLevel;
        public SubmitSmartCoverJobOutput output;
        public TemplateSmartCover.TemplateSmartCoverSmartCover smartCover;
        public String templateId;
        public String userData;
    }

    public static class SubmitSmartCoverJobOutput {
        public String bucket;
        public String object;
        public String region;
    }
}
