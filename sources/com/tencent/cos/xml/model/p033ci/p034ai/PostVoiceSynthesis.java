package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.common.CallBackMqConfig;

/* JADX INFO: loaded from: classes4.dex */
public class PostVoiceSynthesis {
    public String callBack;
    public String callBackFormat;
    public CallBackMqConfig callBackMqConfig;
    public String callBackType;
    public PostVoiceSynthesisOperation operation;
    public String tag = "Tts";

    public static class PostVoiceSynthesisOperation {
        public String jobLevel;
        public PostVoiceSynthesisOutput output;
        public String templateId;
        public PostVoiceSynthesisTtsConfig ttsConfig;
        public PostVoiceSynthesisTtsTpl ttsTpl;
        public String userData;
    }

    public static class PostVoiceSynthesisOutput {
        public String bucket;
        public String object;
        public String region;
    }

    public static class PostVoiceSynthesisTtsConfig {
        public String input;
        public String inputType;
    }

    public static class PostVoiceSynthesisTtsTpl {
        public String codec;
        public String emotion;
        public String mode;
        public String speed;
        public String voiceType;
        public String volume;
    }
}
