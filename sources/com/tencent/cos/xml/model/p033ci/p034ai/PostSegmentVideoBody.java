package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.common.CallBackMqConfig;

/* JADX INFO: loaded from: classes4.dex */
public class PostSegmentVideoBody {
    public String callBack;
    public String callBackFormat;
    public CallBackMqConfig callBackMqConfig;
    public String callBackType;
    public PostSegmentVideoBodyInput input;
    public PostSegmentVideoBodyOperation operation;
    public String tag = "SegmentVideoBody";

    public static class PostSegmentVideoBodyInput {
        public String object;
    }

    public static class PostSegmentVideoBodyOperation {
        public String jobLevel;
        public PostSegmentVideoBodyOutput output;
        public PostSegmentVideoBodySegmentVideoBody segmentVideoBody;
        public String userData;
    }

    public static class PostSegmentVideoBodyOutput {
        public String bucket;
        public String object;
        public String region;
    }

    public static class PostSegmentVideoBodySegmentVideoBody {
        public String backgroundBlue;
        public String backgroundGreen;
        public String backgroundLogoUrl;
        public String backgroundRed;
        public String binaryThreshold;
        public String mode;
        public String removeBlue;
        public String removeGreen;
        public String removeRed;
        public String segmentType;
    }
}
