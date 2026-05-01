package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.AudioMix;
import com.tencent.cos.xml.model.p033ci.media.TemplateVideoMontage;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateVideoMontageResponse {
    public String bucketId;
    public String category;
    public String createTime;
    public String name;
    public String tag;
    public String templateId;
    public String updateTime;
    public TemplateVideoMontageResponseVideoMontage videoMontage;

    public static class TemplateVideoMontageResponseVideoMontage {
        public TemplateVideoMontage.TemplateVideoMontageAudio audio;
        public AudioMix audioMix;
        public List<AudioMix> audioMixArray;
        public TemplateVideoMontage.TemplateVideoMontageContainer container;
        public String duration;
        public String scene;
        public TemplateVideoMontage.TemplateVideoMontageVideo video;
    }
}
