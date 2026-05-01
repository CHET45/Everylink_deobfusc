package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.tag.CallBackMqConfig;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitVideoTagJob {
    public String callBack;
    public String callBackFormat;
    public CallBackMqConfig callBackMqConfig;
    public String callBackType;
    public SubmitVideoTagJobInput input;
    public SubmitVideoTagJobOperation operation;
    public String tag = "VideoTag";

    public static class SubmitVideoTagJobInput {
        public String object;
    }

    public static class SubmitVideoTagJobOperation {
        public String jobLevel;
        public String userData;
        public OperationVideoTag videoTag;
    }
}
