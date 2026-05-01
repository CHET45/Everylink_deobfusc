package com.tencent.cos.xml.model.p033ci.media;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateAnimation {
    public TemplateAnimationContainer container;
    public String name;
    public String tag = "Animation";
    public TemplateAnimationTimeInterval timeInterval;
    public TemplateAnimationVideo video;

    public static class TemplateAnimationContainer {
        public String format;
    }

    public static class TemplateAnimationTimeInterval {
        public String duration;
        public String start;
    }

    public static class TemplateAnimationVideo {
        public String animateFramesPerSecond;
        public String animateOnlyKeepKeyFrame;
        public String animateTimeIntervalOfFrame;
        public String codec;
        public String fps;
        public String height;
        public String quality;
        public String width;
    }
}
