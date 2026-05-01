package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.tag.CallBackMqConfig;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitExtractDigitalWatermarkJob {
    public String callBack;
    public String callBackFormat;
    public CallBackMqConfig callBackMqConfig;
    public String callBackType;
    public SubmitExtractDigitalWatermarkJobInput input;
    public SubmitExtractDigitalWatermarkJobOperation operation;
    public String tag = "ExtractDigitalWatermark";

    public static class SubmitExtractDigitalWatermarkJobExtractDigitalWatermark {
        public String type = "Text";
        public String version = "V1";
    }

    public static class SubmitExtractDigitalWatermarkJobInput {
        public String object;
    }

    public static class SubmitExtractDigitalWatermarkJobOperation {
        public SubmitExtractDigitalWatermarkJobExtractDigitalWatermark extractDigitalWatermark;
        public String jobLevel;
        public String userData;
    }
}
