package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.common.NotifyConfig;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateAsrQueueResponse {
    public UpdateAsrQueueResponseQueue queue;
    public String requestId;

    public static class UpdateAsrQueueResponseQueue {
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
}
