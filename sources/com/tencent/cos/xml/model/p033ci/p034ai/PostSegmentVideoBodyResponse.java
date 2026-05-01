package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.common.MediaInfo;
import com.tencent.cos.xml.model.p033ci.common.MediaResult;
import com.tencent.cos.xml.model.p033ci.p034ai.PostSegmentVideoBody;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class PostSegmentVideoBodyResponse {
    public List<PostSegmentVideoBodyResponseJobsDetail> jobsDetail;

    public static class PostSegmentVideoBodyResponseJobsDetail {
        public String code;
        public String creationTime;
        public String endTime;
        public PostSegmentVideoBody.PostSegmentVideoBodyInput input;
        public String jobId;
        public String message;
        public PostSegmentVideoBodyResponseOperation operation;
        public String queueId;
        public String startTime;
        public String state;
        public String tag;
    }

    public static class PostSegmentVideoBodyResponseOperation {
        public String jobLevel;
        public MediaInfo mediaInfo;
        public MediaResult mediaResult;
        public PostSegmentVideoBody.PostSegmentVideoBodyOutput output;
        public PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody segmentVideoBody;
        public String userData;
    }
}
