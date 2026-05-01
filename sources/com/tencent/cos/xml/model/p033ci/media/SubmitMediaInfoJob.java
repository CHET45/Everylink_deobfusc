package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.tag.CallBackMqConfig;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitMediaInfoJob {
    public String callBack;
    public String callBackFormat;
    public CallBackMqConfig callBackMqConfig;
    public String callBackType;
    public SubmitMediaInfoJobInput input;
    public SubmitMediaInfoJobOperation operation;
    public String tag = "MediaInfo";

    public static class SubmitMediaInfoJobInput {
        public String object;
    }

    public static class SubmitMediaInfoJobOperation {
        public String jobLevel;
        public String userData;
    }
}
