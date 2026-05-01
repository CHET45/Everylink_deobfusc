package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.TemplateVoiceSeparate;
import com.tencent.cos.xml.model.tag.CallBackMqConfig;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitVoiceSeparateJob {
    public String callBack;
    public String callBackFormat;
    public CallBackMqConfig callBackMqConfig;
    public String callBackType;
    public SubmitVoiceSeparateJobInput input;
    public SubmitVoiceSeparateJobOperation operation;
    public String tag = "VoiceSeparate";

    public static class SubmitVoiceSeparateJobInput {
        public String object;
    }

    public static class SubmitVoiceSeparateJobOperation {
        public String jobLevel;
        public SubmitVoiceSeparateJobOutput output;
        public String templateId;
        public VoiceSeparate voiceSeparate;
    }

    public static class SubmitVoiceSeparateJobOutput {
        public String auObject;
        public String bucket;
        public String object;
        public String region;
    }

    public static class VoiceSeparate {
        public TemplateVoiceSeparate.AudioConfig audioConfig;
        public String audioMode;
    }
}
