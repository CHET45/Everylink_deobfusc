package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.AudioMix;
import com.tencent.cos.xml.model.p033ci.media.TemplateConcat;
import com.tencent.cos.xml.model.tag.CallBackMqConfig;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitConcatJob {
    public String callBack;
    public String callBackFormat;
    public CallBackMqConfig callBackMqConfig;
    public String callBackType;
    public SubmitConcatJobInput input;
    public SubmitConcatJobOperation operation;
    public String tag = "Concat";

    public static class SubmitConcatJobConcatFragment {
        public String endTime;
        public String fragmentIndex;
        public String startTime;
        public String url;
    }

    public static class SubmitConcatJobConcatTemplate {
        public TemplateConcat.TemplateConcatAudio audio;
        public AudioMix audioMix;
        public List<AudioMix> audioMixArray;
        public List<SubmitConcatJobConcatFragment> concatFragment;
        public TemplateConcat.TemplateConcatContainer container;
        public String directConcat;
        public String index;
        public TemplateConcat.TemplateConcatVideo video;
    }

    public static class SubmitConcatJobInput {
        public String object;
    }

    public static class SubmitConcatJobOperation {
        public SubmitConcatJobConcatTemplate concatTemplate;
        public String jobLevel;
        public SubmitConcatJobOutput output;
        public String templateId;
    }

    public static class SubmitConcatJobOutput {
        public String bucket;
        public String object;
        public String region;
    }
}
