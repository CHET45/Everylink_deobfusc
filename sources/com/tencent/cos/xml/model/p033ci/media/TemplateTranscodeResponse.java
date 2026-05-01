package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.AudioMix;
import com.tencent.cos.xml.model.p033ci.media.TemplateTranscode;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateTranscodeResponse {
    public String requestId;
    public TemplateTranscodeResponseTemplate template;

    public static class TemplateTranscodeResponseTemplate {
        public String bucketId;
        public String category;
        public String createTime;
        public String name;
        public String tag;
        public String templateId;
        public TemplateTranscodeResponseTransTpl transTpl;
        public String updateTime;
    }

    public static class TemplateTranscodeResponseTransTpl {
        public TemplateTranscode.TemplateTranscodeAudio audio;
        public AudioMix audioMix;
        public List<AudioMix> audioMixArray;
        public TemplateTranscode.TemplateTranscodeContainer container;
        public TemplateTranscode.TemplateTranscodeTimeInterval timeInterval;
        public TemplateTranscode.TemplateTranscodeTransConfig transConfig;
        public TemplateTranscode.TemplateTranscodeVideo video;
    }
}
