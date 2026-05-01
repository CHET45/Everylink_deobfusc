package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.PicProcess;

/* JADX INFO: loaded from: classes4.dex */
public class TemplatePicProcessResponse {
    public String requestId;
    public TemplatePicProcessResponseTemplate template;

    public static class TemplatePicProcessResponseTemplate {
        public String bucketId;
        public String category;
        public String createTime;
        public String name;
        public PicProcess picProcess;
        public String tag;
        public String templateId;
        public String updateTime;
    }
}
