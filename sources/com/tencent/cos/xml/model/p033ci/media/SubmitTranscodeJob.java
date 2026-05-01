package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.AudioMix;
import com.tencent.cos.xml.model.p033ci.common.DigitalWatermark;
import com.tencent.cos.xml.model.p033ci.media.TemplateTranscode;
import com.tencent.cos.xml.model.p033ci.media.TemplateWatermark;
import com.tencent.cos.xml.model.tag.CallBackMqConfig;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitTranscodeJob {
    public String callBack;
    public String callBackFormat;
    public CallBackMqConfig callBackMqConfig;
    public String callBackType;
    public SubmitTranscodeJobInput input;
    public SubmitTranscodeJobOperation operation;
    public String queueType;
    public String tag = "Transcode";

    public static class SubmitTranscodeJobInput {
        public String object;
    }

    public static class SubmitTranscodeJobOperation {
        public DigitalWatermark digitalWatermark;
        public String jobLevel;
        public SubmitTranscodeJobOutput output;
        public SubmitTranscodeJobRemoveWatermark removeWatermark;
        public SubmitTranscodeJobSubtitles subtitles;
        public String templateId;
        public SubmitTranscodeJobTranscode transcode;
        public String userData;
        public List<TemplateWatermark.Watermark> watermark;
        public List<String> watermarkTemplateId;
    }

    public static class SubmitTranscodeJobOutput {
        public String bucket;
        public String object;
        public String region;
    }

    public static class SubmitTranscodeJobRemoveWatermark {

        /* JADX INFO: renamed from: dx */
        public String f1823dx;

        /* JADX INFO: renamed from: dy */
        public String f1824dy;
        public String height;
        public String width;
    }

    public static class SubmitTranscodeJobSubtitle {
        public String url;
    }

    public static class SubmitTranscodeJobSubtitles {
        public List<SubmitTranscodeJobSubtitle> subtitle;
    }

    public static class SubmitTranscodeJobTranscode {
        public TemplateTranscode.TemplateTranscodeAudio audio;
        public AudioMix audioMix;
        public List<AudioMix> audioMixArray;
        public TemplateTranscode.TemplateTranscodeContainer container;
        public TemplateTranscode.TemplateTranscodeTimeInterval timeInterval;
        public TemplateTranscode.TemplateTranscodeTransConfig transConfig;
        public TemplateTranscode.TemplateTranscodeVideo video;
    }
}
