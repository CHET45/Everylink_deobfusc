package com.tencent.cos.xml.model.p033ci.p034ai.bean;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class WordsGeneralizeJobDetail {
    public String code;
    public String creationTime;
    public String endTime;
    public WordsGeneralizeJobInput input;
    public String jobId;
    public String message;
    public WordsGeneralizeJobDetailOperation operation;
    public String queueId;
    public String startTime;
    public String state;
    public String tag;

    public static class WordsGeneralizeJobDetailOperation extends WordsGeneralizeJobOperation {
        public WordsGeneralizeResult wordsGeneralizeResult;
    }

    public static class WordsGeneralizeLable {
        public String category;
        public String word;
    }

    public static class WordsGeneralizeResult {
        public List<WordsGeneralizeLable> wordsGeneralizeLable;
        public List<WordsGeneralizeToken> wordsGeneralizeToken;
    }

    public static class WordsGeneralizeToken {
        public String length;
        public String offset;
        public String pos;
        public String word;
    }
}
