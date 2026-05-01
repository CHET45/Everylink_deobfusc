package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.PostSoundHound;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class PostSoundHoundResponse {
    public List<PostSoundHoundResponseJobsDetail> jobsDetail;

    public static class PostSoundHoundResponseJobsDetail {
        public String code;
        public String creationTime;
        public String endTime;
        public PostSoundHound.PostSoundHoundInput input;
        public String jobId;
        public String message;
        public PostSoundHoundResponseOperation operation;
        public String queueId;
        public String startTime;
        public String state;
        public String tag;
    }

    public static class PostSoundHoundResponseOperation {
        public String jobLevel;
        public PostSoundHoundResponseSoundHoundResult soundHoundResult;
        public String userData;
    }

    public static class PostSoundHoundResponseSongList {
        public int inlier;
        public String singerName;
        public String songName;
    }

    public static class PostSoundHoundResponseSoundHoundResult {
        public List<PostSoundHoundResponseSongList> songList;
    }
}
