package com.tencent.cos.xml.model.p033ci.asr.bean;

import com.tencent.cos.xml.model.tag.CallBackMqConfig;
import com.tencent.cos.xml.model.tag.Locator;

/* JADX INFO: loaded from: classes4.dex */
public class CreateSpeechJobs {
    public String callBack;
    public String callBackFormat;
    public String callBackType;
    public String queueId;
    public String tag = "SpeechRecognition";
    public CreateSpeechJobsInput input = new CreateSpeechJobsInput();
    public CreateSpeechJobsOperation operation = new CreateSpeechJobsOperation();
    public CallBackMqConfig callBackMqConfig = new CallBackMqConfig();

    public static class CreateSpeechJobsInput {
        public String object;
        public String url;
    }

    public static class CreateSpeechJobsOperation {
        public int jobLevel;
        public String templateId;
        public String userData;
        public SpeechRecognition speechRecognition = new SpeechRecognition();
        public Locator output = new Locator();
    }
}
