package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.common.VideoTargetRec;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class PostVideoTargetRecResponse {
    public List<PostVideoTargetRecResponseJobsDetail> jobsDetail;

    public static class PostVideoTargetRecResponseBodyInfo {
        public PostVideoTargetRecResponseLocation location;
        public String name;
        public int score;
    }

    public static class PostVideoTargetRecResponseBodyRecognition {
        public PostVideoTargetRecResponseBodyInfo bodyInfo;
        public String time;
        public String url;
    }

    public static class PostVideoTargetRecResponseCarInfo {
        public PostVideoTargetRecResponseLocation location;
        public String name;
        public int score;
    }

    public static class PostVideoTargetRecResponseCarRecognition {
        public List<PostVideoTargetRecResponseCarInfo> carInfo;
        public String time;
        public String url;
    }

    public static class PostVideoTargetRecResponseJobsDetail {
        public String code;
        public String creationTime;
        public String endTime;
        public String jobId;
        public String message;
        public PostVideoTargetRecResponseOperation operation;
        public String queueId;
        public String startTime;
        public String state;
        public String tag;
    }

    public static class PostVideoTargetRecResponseLocation {
        public String height;
        public String width;

        /* JADX INFO: renamed from: x */
        public String f1817x;

        /* JADX INFO: renamed from: y */
        public String f1818y;
    }

    public static class PostVideoTargetRecResponseOperation {
        public String jobLevel;
        public String templateId;
        public String templateName;
        public String userData;
        public VideoTargetRec videoTargetRec;
        public PostVideoTargetRecResponseVideoTargetRecResult videoTargetRecResult;
    }

    public static class PostVideoTargetRecResponsePetInfo {
        public PostVideoTargetRecResponseLocation location;
        public String name;
        public int score;
    }

    public static class PostVideoTargetRecResponsePetRecognition {
        public List<PostVideoTargetRecResponsePetInfo> petInfo;
        public String time;
        public String url;
    }

    public static class PostVideoTargetRecResponseVideoTargetRecResult {
        public List<PostVideoTargetRecResponseBodyRecognition> bodyRecognition;
        public List<PostVideoTargetRecResponseCarRecognition> carRecognition;
        public List<PostVideoTargetRecResponsePetRecognition> petRecognition;
    }
}
