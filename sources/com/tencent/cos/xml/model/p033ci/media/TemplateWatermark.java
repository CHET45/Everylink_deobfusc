package com.tencent.cos.xml.model.p033ci.media;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateWatermark {
    public String name;
    public String tag = "Watermark";
    public Watermark watermark;

    public static class TemplateWatermarkImage {
        public String background;
        public String height;
        public String mode;
        public String transparency;
        public String url;
        public String width;
    }

    public static class TemplateWatermarkSlideConfig {
        public String slideMode;
        public String xSlideSpeed;
        public String ySlideSpeed;
    }

    public static class TemplateWatermarkText {
        public String fontColor;
        public String fontSize;
        public String fontType;
        public String text;
        public String transparency;
    }

    public static class Watermark {

        /* JADX INFO: renamed from: dx */
        public String f1829dx;

        /* JADX INFO: renamed from: dy */
        public String f1830dy;
        public String endTime;
        public TemplateWatermarkImage image;
        public String locMode;
        public String pos;
        public TemplateWatermarkSlideConfig slideConfig;
        public String startTime;
        public TemplateWatermarkText text;
        public String type;
    }
}
