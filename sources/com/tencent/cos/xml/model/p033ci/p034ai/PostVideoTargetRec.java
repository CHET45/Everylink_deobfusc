package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.common.CallBackMqConfig;
import com.tencent.cos.xml.model.p033ci.common.VideoTargetRec;

/* JADX INFO: loaded from: classes4.dex */
public class PostVideoTargetRec {
    public String callBack;
    public String callBackFormat;
    public CallBackMqConfig callBackMqConfig;
    public String callBackType;
    public PostVideoTargetRecInput input;
    public PostVideoTargetRecOperation operation;
    public String tag = "VideoTargetRec";

    public static class PostVideoTargetRecInput {
        public String object;
    }

    public static class PostVideoTargetRecOperation {
        public String jobLevel;
        public String templateId;
        public String userData;
        public VideoTargetRec videoTargetRec;
    }
}
