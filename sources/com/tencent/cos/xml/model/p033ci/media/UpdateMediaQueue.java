package com.tencent.cos.xml.model.p033ci.media;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateMediaQueue {
    public String name;
    public UpdateMediaQueueNotifyConfig notifyConfig;
    public String state;

    public static class UpdateMediaQueueNotifyConfig {
        public String event;
        public String mqMode;
        public String mqName;
        public String mqRegion;
        public String resultFormat;
        public String state;
        public String type;
        public String url;
    }
}
