package com.tencent.cos.xml.model.p033ci.media;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SearchMediaQueueResponse {
    public List<QueueID> nonExistPIDs;
    public int pageNumber;
    public int pageSize;
    public List<SearchMediaQueueResponseQueueList> queueList;
    public String requestId;
    public int totalCount;

    public static class QueueID {
        public String queueID;
    }

    public static class SearchMediaQueueResponseNotifyConfig {
        public String event;
        public String resultFormat;
        public String state;
        public String type;
        public String url;
    }

    public static class SearchMediaQueueResponseQueueList {
        public String category;
        public String createTime;
        public int maxConcurrent;
        public int maxSize;
        public String name;
        public SearchMediaQueueResponseNotifyConfig notifyConfig;
        public String queueId;
        public String state;
        public String updateTime;
    }
}
