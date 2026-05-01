package com.tencent.cos.xml.model.p033ci.media;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitVideoTagJobResponse {
    public SubmitVideoTagJobResponseJobsDetail jobsDetail;

    public static class SubmitVideoTagJobResponseActionTags {
        public String endTime;
        public String startTime;
        public List<SubmitVideoTagJobResponseTags> tags;
    }

    public static class SubmitVideoTagJobResponseBBox {

        /* JADX INFO: renamed from: x1 */
        public String f1825x1;

        /* JADX INFO: renamed from: x2 */
        public String f1826x2;

        /* JADX INFO: renamed from: y1 */
        public String f1827y1;

        /* JADX INFO: renamed from: y2 */
        public String f1828y2;
    }

    public static class SubmitVideoTagJobResponseData {
        public List<SubmitVideoTagJobResponseActionTags> actionTags;
        public List<SubmitVideoTagJobResponseObjectTags> objectTags;
        public List<SubmitVideoTagJobResponsePersonTags> personTags;
        public List<SubmitVideoTagJobResponsePlaceTags> placeTags;
        public SubmitVideoTagJobResponseTags tags;
    }

    public static class SubmitVideoTagJobResponseDetailPerSecond {
        public List<SubmitVideoTagJobResponseBBox> bBox;
        public float confidence;
        public String timeStamp;
    }

    public static class SubmitVideoTagJobResponseInput {
        public String bucketId;
        public String object;
        public String region;
    }

    public static class SubmitVideoTagJobResponseJobsDetail {
        public String code;
        public String creationTime;
        public String endTime;
        public SubmitVideoTagJobResponseInput input;
        public String jobId;
        public String message;
        public SubmitVideoTagJobResponseOperation operation;
        public String queueId;
        public String startTime;
        public String state;
        public String tag;
    }

    public static class SubmitVideoTagJobResponseObjectTags {
        public List<SubmitVideoTagJobResponseObjects> objects;
        public String timeStamp;
    }

    public static class SubmitVideoTagJobResponseObjects {
        public List<SubmitVideoTagJobResponseBBox> bBox;
        public float confidence;
        public String name;
    }

    public static class SubmitVideoTagJobResponseOperation {
        public String jobLevel;
        public String userData;
        public OperationVideoTag videoTag;
        public SubmitVideoTagJobResponseVideoTagResult videoTagResult;
    }

    public static class SubmitVideoTagJobResponsePersonTags {
        public float confidence;
        public String count;
        public List<SubmitVideoTagJobResponseDetailPerSecond> detailPerSecond;
        public String name;
    }

    public static class SubmitVideoTagJobResponsePlaceTags {
        public String clipFrameResult;
        public String endIndex;
        public String endTime;
        public String startIndex;
        public String startTime;
        public List<SubmitVideoTagJobResponseTags> tags;
    }

    public static class SubmitVideoTagJobResponseStreamData {
        public SubmitVideoTagJobResponseData data;
        public String subErrCode;
        public String subErrMsg;
    }

    public static class SubmitVideoTagJobResponseTags {
        public float confidence;
        public String tag;
        public String tagCls;
    }

    public static class SubmitVideoTagJobResponseVideoTagResult {
        public SubmitVideoTagJobResponseStreamData streamData;
    }
}
