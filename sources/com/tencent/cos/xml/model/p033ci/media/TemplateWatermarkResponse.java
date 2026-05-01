package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.TemplateWatermark;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateWatermarkResponse {
    public String requestId;
    public TemplateWatermarkResponseTemplate template;

    public static class TemplateWatermarkResponseTemplate {
        public String bucketId;
        public String category;
        public String createTime;
        public String name;
        public String tag;
        public String templateId;
        public String updateTime;
        public TemplateWatermark.Watermark watermark;
    }
}
