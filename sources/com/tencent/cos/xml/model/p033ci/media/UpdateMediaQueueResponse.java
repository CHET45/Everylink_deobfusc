package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.UpdateMediaQueue;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateMediaQueueResponse {
    public UpdateMediaQueueResponseQueue queue;
    public String requestId;

    public static class UpdateMediaQueueResponseQueue {
        public String category;
        public String createTime;
        public int maxConcurrent;
        public int maxSize;
        public String name;
        public UpdateMediaQueue.UpdateMediaQueueNotifyConfig notifyConfig;
        public String queueId;
        public String state;
        public String updateTime;
    }
}
