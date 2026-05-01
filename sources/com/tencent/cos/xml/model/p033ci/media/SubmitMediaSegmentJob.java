package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.tag.CallBackMqConfig;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitMediaSegmentJob {
    public String callBack;
    public String callBackFormat;
    public CallBackMqConfig callBackMqConfig;
    public String callBackType;
    public SubmitMediaSegmentJobInput input;
    public SubmitMediaSegmentJobOperation operation;
    public String tag = "Segment";

    public static class SubmitMediaSegmentJobHlsEncrypt {
        public String isHlsEncrypt;
        public String uriKey;
    }

    public static class SubmitMediaSegmentJobInput {
        public String object;
    }

    public static class SubmitMediaSegmentJobOperation {
        public String jobLevel;
        public SubmitMediaSegmentJobOutput output;
        public SubmitMediaSegmentJobSegment segment;
    }

    public static class SubmitMediaSegmentJobOutput {
        public String bucket;
        public String object;
        public String region;
    }

    public static class SubmitMediaSegmentJobSegment {
        public String duration;
        public String format;
        public SubmitMediaSegmentJobHlsEncrypt hlsEncrypt;
        public String transcodeIndex;
    }
}
