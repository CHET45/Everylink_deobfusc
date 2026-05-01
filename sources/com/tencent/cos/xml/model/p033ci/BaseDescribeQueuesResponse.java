package com.tencent.cos.xml.model.p033ci;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class BaseDescribeQueuesResponse {
    public List<QueueID> nonExistPIDs;
    public int pageNumber;
    public int pageSize;
    public List<Queue> queueList;
    public String requestId;
    public int totalCount;

    public static class NotifyConfig {
        public String event;
        public String resultFormat;
        public String state;
        public String type;
        public String url;
    }

    public static class Queue {
        public String category;
        public String createTime;
        public int maxConcurrent;
        public int maxSize;
        public String name;
        public NotifyConfig notifyConfig;
        public String queueId;
        public String state;
        public String updateTime;
    }

    public static class QueueID {
        public String queueID;
    }
}
