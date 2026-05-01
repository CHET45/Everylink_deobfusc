package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.TemplateConcat;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateConcatResponse {
    public String requestId;
    public TemplateConcatResponseTemplate template;

    public static class TemplateConcatResponseTemplate {
        public String bucketId;
        public String category;
        public TemplateConcat.TemplateConcatConcatTemplate concatTemplate;
        public String createTime;
        public String name;
        public String tag;
        public String templateId;
        public String updateTime;
    }
}
