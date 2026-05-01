package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.PicProcess;
import com.tencent.cos.xml.model.tag.CallBackMqConfig;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitPicProcessJob {
    public String callBack;
    public String callBackFormat;
    public CallBackMqConfig callBackMqConfig;
    public String callBackType;
    public SubmitPicProcessJobInput input;
    public SubmitPicProcessJobOperation operation;
    public String tag = "PicProcess";

    public static class SubmitPicProcessJobInput {
        public String object;
    }

    public static class SubmitPicProcessJobOperation {
        public String jobLevel;
        public SubmitPicProcessJobOutput output;
        public PicProcess picProcess;
        public String templateId;
        public String userData;
    }

    public static class SubmitPicProcessJobOutput {
        public String bucket;
        public String object;
        public String region;
    }
}
