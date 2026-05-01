package com.tencent.cos.xml.model.p033ci.media;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateAnimationResponse {
    public String bucketId;
    public String category;
    public String createTime;
    public String name;
    public String tag;
    public String templateId;
    public TemplateAnimationResponseTransTpl transTpl;
    public String updateTime;

    public static class TemplateAnimationResponseTransTpl {
        public TransTplContainer container;
        public TransTplTimeInterval timeInterval;
        public TransTplVideo video;
    }

    public static class TransTplContainer {
        public String format;
    }

    public static class TransTplTimeInterval {
        public String duration;
        public String start;
    }

    public static class TransTplVideo {
        public String animateFramesPerSecond;
        public String animateOnlyKeepKeyFrame;
        public String animateTimentervalOfFrame;
        public String codec;
        public String fps;
        public String height;
        public String quality;
        public String width;
    }
}
