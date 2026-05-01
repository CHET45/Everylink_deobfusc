package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.AudioMix;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateConcat {
    public TemplateConcatConcatTemplate concatTemplate;
    public String name;
    public String tag = "Concat";

    public static class TemplateConcatAudio {
        public String bitrate;
        public String channels;
        public String codec;
        public String samplerate;
    }

    public static class TemplateConcatConcatFragment {
        public String endTime;
        public String mode;
        public String startTime;
        public String url;
    }

    public static class TemplateConcatConcatTemplate {
        public TemplateConcatAudio audio;
        public AudioMix audioMix;
        public List<AudioMix> audioMixArray;
        public List<TemplateConcatConcatFragment> concatFragment;
        public TemplateConcatContainer container;
        public String directConcat;
        public TemplateConcatSceneChangeInfo sceneChangeInfo;
        public TemplateConcatVideo video;
    }

    public static class TemplateConcatContainer {
        public String format;
    }

    public static class TemplateConcatSceneChangeInfo {
        public String mode;
        public String time;
    }

    public static class TemplateConcatVideo {
        public String bitrate;
        public String codec;
        public String crf;
        public String fps;
        public String height;
        public String remove;
        public String rotate;
        public String width;
    }
}
