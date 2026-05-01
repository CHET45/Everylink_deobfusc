package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.AudioMix;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateVideoMontage {
    public TemplateVideoMontageAudio audio;
    public AudioMix audioMix;
    public List<AudioMix> audioMixArray;
    public TemplateVideoMontageContainer container;
    public String duration;
    public String name;
    public String scene;
    public String tag = "VideoMontage";
    public TemplateVideoMontageVideo video;

    public static class TemplateVideoMontageAudio {
        public String bitrate;
        public String channels;
        public String codec;
        public String remove;
        public String samplerate;
    }

    public static class TemplateVideoMontageContainer {
        public String format;
    }

    public static class TemplateVideoMontageVideo {
        public String bitrate;
        public String codec;
        public String crf;
        public String fps;
        public String height;
        public String rotate;
        public String width;
    }
}
