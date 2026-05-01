package com.aivox.common.model;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class NuiTransResultBean {
    private Header header;
    private Payload payload;

    public Header getHeader() {
        return this.header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Payload getPayload() {
        return this.payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public static class Header {
        private String message_id;
        private String name;
        private String namespace;
        private int status;
        private String status_text;
        private String task_id;

        public String getNamespace() {
            return this.namespace;
        }

        public void setNamespace(String str) {
            this.namespace = str;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int i) {
            this.status = i;
        }

        public String getMessageId() {
            return this.message_id;
        }

        public void setMessageId(String str) {
            this.message_id = str;
        }

        public String getTaskId() {
            return this.task_id;
        }

        public void setTaskId(String str) {
            this.task_id = str;
        }

        public String getStatusText() {
            return this.status_text;
        }

        public void setStatusText(String str) {
            this.status_text = str;
        }
    }

    public static class Payload {
        private String audio_extra_info;
        private int begin_time;
        private Double confidence;
        private String fixed_result;
        private String gender;
        private Double genderScore;
        private int index;
        private String result;
        private String sentence_id;
        private StashResult stash_result;
        private int status;
        private int time;
        private String unfixed_result;
        private List<?> words;

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int i) {
            this.index = i;
        }

        public int getTime() {
            return this.time;
        }

        public void setTime(int i) {
            this.time = i;
        }

        public String getResult() {
            return this.result;
        }

        public void setResult(String str) {
            this.result = str;
        }

        public Double getConfidence() {
            return this.confidence;
        }

        public void setConfidence(Double d) {
            this.confidence = d;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int i) {
            this.status = i;
        }

        public String getGender() {
            return this.gender;
        }

        public void setGender(String str) {
            this.gender = str;
        }

        public int getBeginTime() {
            return this.begin_time;
        }

        public void setBeginTime(int i) {
            this.begin_time = i;
        }

        public String getFixedResult() {
            return this.fixed_result;
        }

        public void setFixedResult(String str) {
            this.fixed_result = str;
        }

        public String getUnfixedResult() {
            return this.unfixed_result;
        }

        public void setUnfixedResult(String str) {
            this.unfixed_result = str;
        }

        public StashResult getStashResult() {
            return this.stash_result;
        }

        public void setStashResult(StashResult stashResult) {
            this.stash_result = stashResult;
        }

        public String getAudioExtraInfo() {
            return this.audio_extra_info;
        }

        public void setAudioExtraInfo(String str) {
            this.audio_extra_info = str;
        }

        public String getSentenceId() {
            return this.sentence_id;
        }

        public void setSentenceId(String str) {
            this.sentence_id = str;
        }

        public Double getGenderScore() {
            return this.genderScore;
        }

        public void setGenderScore(Double d) {
            this.genderScore = d;
        }

        public List<?> getWords() {
            return this.words;
        }

        public void setWords(List<?> list) {
            this.words = list;
        }

        public static class StashResult {
            private int beginTime;
            private int currentTime;
            private String fixedText;
            private int sentenceId;
            private String text;
            private String unfixedText;

            public int getSentenceId() {
                return this.sentenceId;
            }

            public void setSentenceId(int i) {
                this.sentenceId = i;
            }

            public int getBeginTime() {
                return this.beginTime;
            }

            public void setBeginTime(int i) {
                this.beginTime = i;
            }

            public String getText() {
                return this.text;
            }

            public void setText(String str) {
                this.text = str;
            }

            public String getFixedText() {
                return this.fixedText;
            }

            public void setFixedText(String str) {
                this.fixedText = str;
            }

            public String getUnfixedText() {
                return this.unfixedText;
            }

            public void setUnfixedText(String str) {
                this.unfixedText = str;
            }

            public int getCurrentTime() {
                return this.currentTime;
            }

            public void setCurrentTime(int i) {
                this.currentTime = i;
            }
        }
    }
}
