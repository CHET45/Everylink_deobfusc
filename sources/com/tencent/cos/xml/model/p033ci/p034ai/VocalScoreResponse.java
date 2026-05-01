package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.VocalScore;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class VocalScoreResponse {
    public List<VocalScoreResponseJobsDetail> jobsDetail;

    public static class VocalScoreResponseJobsDetail {
        public String code;
        public String creationTime;
        public String endTime;
        public VocalScore.VocalScoreInput input;
        public String jobId;
        public String message;
        public VocalScoreResponseOperation operation;
        public String queueId;
        public String startTime;
        public String state;
        public String tag;
    }

    public static class VocalScoreResponseOperation {
        public String jobLevel;
        public String userData;
        public VocalScore.VocalScoreVocalScore vocalScore;
        public VocalScoreResponseVocalScoreResult vocalScoreResult;
    }

    public static class VocalScoreResponsePitchScore {
        public List<VocalScoreResponseSentenceScores> sentenceScores;
        public int totalScore;
    }

    public static class VocalScoreResponseRhythemScore {
        public List<VocalScoreResponseRhythemScoreSentenceScores> sentenceScores;
        public int totalScore;
    }

    public static class VocalScoreResponseRhythemScoreSentenceScores {
        public float endTime;
        public int score;
        public float startTime;
    }

    public static class VocalScoreResponseSentenceScores {
        public float endTime;
        public int score;
        public float startTime;
    }

    public static class VocalScoreResponseVocalScoreResult {
        public VocalScoreResponsePitchScore pitchScore;
        public VocalScoreResponseRhythemScore rhythemScore;
    }
}
