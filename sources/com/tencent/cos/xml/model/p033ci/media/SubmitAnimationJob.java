package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.TemplateAnimation;
import com.tencent.cos.xml.model.tag.CallBackMqConfig;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitAnimationJob {
    public String callBack;
    public String callBackFormat;
    public CallBackMqConfig callBackMqConfig;
    public String callBackType;
    public SubmitAnimationJobInput input;
    public SubmitAnimationJobOperation operation;
    public String tag = "Animation";

    public static class SubmitAnimationJobAnimation {
        public TemplateAnimation.TemplateAnimationContainer container;
        public TemplateAnimation.TemplateAnimationTimeInterval timeInterval;
        public TemplateAnimation.TemplateAnimationVideo video;
    }

    public static class SubmitAnimationJobInput {
        public String object;
    }

    public static class SubmitAnimationJobOperation {
        public SubmitAnimationJobAnimation animation;
        public String jobLevel;
        public SubmitAnimationJobOutput output;
        public String templateId;
        public String userData;
    }

    public static class SubmitAnimationJobOutput {
        public String bucket;
        public String object;
        public String region;
    }
}
