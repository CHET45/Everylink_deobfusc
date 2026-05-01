package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.DigitalWatermark;
import com.tencent.cos.xml.model.tag.CallBackMqConfig;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitDigitalWatermarkJob {
    public String callBack;
    public String callBackFormat;
    public CallBackMqConfig callBackMqConfig;
    public String callBackType;
    public SubmitDigitalWatermarkJobInput input;
    public SubmitDigitalWatermarkJobOperation operation;
    public String tag = "DigitalWatermark";

    public static class SubmitDigitalWatermarkJobInput {
        public String object;
    }

    public static class SubmitDigitalWatermarkJobOperation {
        public DigitalWatermark digitalWatermark;
        public String jobLevel;
        public SubmitDigitalWatermarkJobOutput output;
        public String userData;
    }

    public static class SubmitDigitalWatermarkJobOutput {
        public String bucket;
        public String object;
        public String region;
    }
}
