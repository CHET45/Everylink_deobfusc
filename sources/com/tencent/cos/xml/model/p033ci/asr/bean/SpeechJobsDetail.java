package com.tencent.cos.xml.model.p033ci.asr.bean;

import com.tencent.cos.xml.model.p033ci.p034ai.bean.WordsGeneralizeJobDetail;
import com.tencent.cos.xml.model.tag.Locator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SpeechJobsDetail {
    public String code;
    public String creationTime;
    public String endTime;
    public SpeechJobsDetailInput input;
    public String jobId;
    public String message;
    public SpeechJobsDetailOperation operation;
    public String queueId;
    public String startTime;
    public String state;
    public String tag;

    public static class FlashResult {
        public int channelId;
        public List<FlashSentence> sentenceList;
        public String text;
    }

    public static class FlashSentence {
        public int endTime;
        public int speakerId;
        public int startTime;
        public String text;
        public List<FlashSentenceWord> wordList;
    }

    public static class FlashSentenceWord {
        public int endTime;
        public int startTime;
        public String word;
    }

    public static class SentenceDetail {
        public String endMs;
        public String finalSentence;
        public String sliceSentence;
        public String speakerId;
        public String speechSpeed;
        public String startMs;
        public List<SentenceWords> words;
        public String wordsNum;
    }

    public static class SentenceWords {
        public String offsetEndMs;
        public String offsetStartMs;
        public String voiceType;
        public String word;
    }

    public static class SpeechJobsDetailInput {
        public String bucketId;
        public String object;
        public String region;
    }

    public static class SpeechJobsDetailOperation {
        public int jobLevel;
        public Locator output;
        public SpeechRecognition speechRecognition;
        public SpeechRecognitionResult speechRecognitionResult;
        public String templateId;
        public String userData;
    }

    public static class SpeechRecognitionResult {
        public double audioTime;
        public List<FlashResult> flashResult;
        public String result;
        public List<SentenceDetail> resultDetail;
        public WordsGeneralizeJobDetail.WordsGeneralizeResult wordsGeneralizeResult;
    }
}
