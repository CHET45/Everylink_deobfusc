package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.AudioMix;
import com.tencent.cos.xml.model.p033ci.media.TemplateTranscode;
import com.tencent.cos.xml.model.p033ci.media.TemplateVideoMontage;
import com.tencent.cos.xml.model.tag.CallBackMqConfig;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitVideoMontageJob {
    public String callBack;
    public String callBackFormat;
    public CallBackMqConfig callBackMqConfig;
    public String callBackType;
    public SubmitVideoMontageJobInput input;
    public SubmitVideoMontageJobOperation operation;
    public String tag = "VideoMontage";

    public static class SubmitVideoMontageJobInput {
        public String object;
        public String url;
    }

    public static class SubmitVideoMontageJobOperation {
        public String jobLevel;
        public SubmitVideoMontageJobOutput output;
        public String templateId;
        public String userData;
        public SubmitVideoMontageJobVideoMontage videoMontage;
    }

    public static class SubmitVideoMontageJobOutput {
        public String bucket;
        public String object;
        public String region;
    }

    public static class SubmitVideoMontageJobVideoMontage {
        public TemplateVideoMontage.TemplateVideoMontageAudio audio;
        public AudioMix audioMix;
        public List<AudioMix> audioMixArray;
        public TemplateVideoMontage.TemplateVideoMontageContainer container;
        public TemplateTranscode.TemplateTranscodeTransConfig transConfig;
        public TemplateVideoMontage.TemplateVideoMontageVideo video;
    }
}
