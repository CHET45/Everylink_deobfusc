package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.AudioMix;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateTranscode {
    public TemplateTranscodeAudio audio;
    public AudioMix audioMix;
    public List<AudioMix> audioMixArray;
    public TemplateTranscodeContainer container;
    public String name;
    public String tag = "Transcode";
    public TemplateTranscodeTimeInterval timeInterval;
    public TemplateTranscodeTransConfig transConfig;
    public TemplateTranscodeVideo video;

    public static class TemplateTranscodeAudio {
        public String bitrate;
        public String channels;
        public String codec;
        public String keepTwoTracks;
        public String remove;
        public String sampleFormat;
        public String samplerate;
        public String switchTrack;
    }

    public static class TemplateTranscodeClipConfig {
        public String duration;
    }

    public static class TemplateTranscodeContainer {
        public TemplateTranscodeClipConfig clipConfig;
        public String format;
    }

    public static class TemplateTranscodeDashEncrypt {
        public String isEncrypt;
        public String uriKey;
    }

    public static class TemplateTranscodeHlsEncrypt {
        public String isHlsEncrypt;
        public String uriKey;
    }

    public static class TemplateTranscodeTimeInterval {
        public String duration;
        public String start;
    }

    public static class TemplateTranscodeTransConfig {
        public String adjDarMethod;
        public String audioBitrateAdjMethod;
        public TemplateTranscodeDashEncrypt dashEncrypt;
        public String deleteMetadata;
        public TemplateTranscodeHlsEncrypt hlsEncrypt;
        public String isCheckAudioBitrate;
        public String isCheckReso;
        public String isCheckVideoBitrate;
        public String isCheckVideoFps;
        public String isHdr2Sdr;
        public String resoAdjMethod;
        public String transcodeIndex;
        public String videoBitrateAdjMethod;
        public String videoFpsAdjMethod;
    }

    public static class TemplateTranscodeVideo {
        public String bitrate;
        public String bufsize;
        public String codec;
        public String crf;
        public String crop;
        public String fps;
        public String gop;
        public String height;
        public String interlaced;
        public String longShortMode;
        public String maxrate;
        public String pixfmt;
        public String preset;
        public String profile;
        public String remove;
        public String roi;
        public String rotate;
        public String width;
    }
}
