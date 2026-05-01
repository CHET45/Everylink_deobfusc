package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.TemplateVoiceSeparate;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateVoiceSeparateResponse {
    public String requestId;
    public TemplateVoiceSeparateResponseTemplate template;

    public static class TemplateVoiceSeparateResponseTemplate {
        public String bucketId;
        public String category;
        public String createTime;
        public String name;
        public String tag;
        public String templateId;
        public String updateTime;
        public TemplateVoiceSeparateResponseVoiceSeparate voiceSeparate;
    }

    public static class TemplateVoiceSeparateResponseVoiceSeparate {
        public TemplateVoiceSeparate.AudioConfig audioConfig;
        public String audioMode;
    }
}
