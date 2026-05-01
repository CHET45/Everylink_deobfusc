package com.tencent.cos.xml.model.p033ci.media;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class GetWorkflowDetailResponse {
    public String requestId;
    public GetWorkflowDetailResponseWorkflowExecution workflowExecution;

    public static class GetWorkflowDetailResponseAudio {
        public String duration;
    }

    public static class GetWorkflowDetailResponseBasicInfo {
        public String contentType;
        public String eTag;
        public String lastModified;
        public String object;
        public String size;
    }

    public static class GetWorkflowDetailResponseFileInfo {
        public GetWorkflowDetailResponseBasicInfo basicInfo;
        public GetWorkflowDetailResponseImageInfo imageInfo;
        public GetWorkflowDetailResponseMediaInfo mediaInfo;
    }

    public static class GetWorkflowDetailResponseFormat {
        public String duration;
    }

    public static class GetWorkflowDetailResponseImageInfo {
        public String format;
        public String frameCount;
        public String height;
        public String md5;
        public String width;
    }

    public static class GetWorkflowDetailResponseInputObjectInfo {
        public String dar;
        public String duration;
        public String height;
        public String imageDar;
        public String imageHeight;
        public String imageWidth;
        public String size;
        public String width;
    }

    public static class GetWorkflowDetailResponseJudgementInfo {
        public List<GetWorkflowDetailResponseJudgementResult> judgementResult;
        public int objectCount;
    }

    public static class GetWorkflowDetailResponseJudgementResult {
        public GetWorkflowDetailResponseInputObjectInfo inputObjectInfo;
        public String objectName;
        public String objectUrl;
        public String state;
    }

    public static class GetWorkflowDetailResponseMediaInfo {
        public GetWorkflowDetailResponseAudio audio;
        public GetWorkflowDetailResponseFormat format;
        public GetWorkflowDetailResponseVideo video;
    }

    public static class GetWorkflowDetailResponseObjectInfo {
        public String code;
        public String inputObjectName;
        public String message;
        public String objectName;
        public String objectUrl;
    }

    public static class GetWorkflowDetailResponseResultInfo {
        public String objectCount;
        public List<GetWorkflowDetailResponseObjectInfo> objectInfo;
        public String spriteObjectCount;
        public List<GetWorkflowDetailResponseSpriteObjectInfo> spriteObjectInfo;
    }

    public static class GetWorkflowDetailResponseSpriteObjectInfo {
        public String objectName;
        public String objectUrl;
    }

    public static class GetWorkflowDetailResponseTasks {
        public String code;
        public String createTime;
        public String endTime;
        public List<GetWorkflowDetailResponseFileInfo> fileInfo;
        public String jobId;
        public GetWorkflowDetailResponseJudgementInfo judgementInfo;
        public String message;
        public String name;
        public GetWorkflowDetailResponseResultInfo resultInfo;
        public String startTime;
        public String state;
        public String type;
    }

    public static class GetWorkflowDetailResponseVideo {
        public String dar;
        public String duration;
        public String height;
        public String width;
    }

    public static class GetWorkflowDetailResponseWorkflowExecution {
        public String createTime;
        public String object;
        public String runId;
        public String state;
        public List<GetWorkflowDetailResponseTasks> tasks;
        public String workflowId;
        public String workflowName;
    }
}
