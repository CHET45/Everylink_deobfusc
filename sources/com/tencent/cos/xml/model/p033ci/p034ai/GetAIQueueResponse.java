package com.tencent.cos.xml.model.p033ci.p034ai;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class GetAIQueueResponse {
    public List<String> nonExistPIDs;
    public int pageNumber;
    public int pageSize;
    public List<GetAIQueueResponseQueueList> queueList;
    public String requestId;
    public int totalCount;

    public static class GetAIQueueResponseNotifyConfig {
        public String event;
        public String mqMode;
        public String mqName;
        public String mqRegion;
        public String resultFormat;
        public String state;
        public String type;
        public String url;
    }

    public static class GetAIQueueResponseQueueList {
        public String category;
        public String createTime;
        public int maxConcurrent;
        public int maxSize;
        public String name;
        public GetAIQueueResponseNotifyConfig notifyConfig;
        public String queueId;
        public String state;
        public String updateTime;
    }
}
