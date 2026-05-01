package com.tencent.cos.xml.model.p033ci.audit;

/* JADX INFO: loaded from: classes4.dex */
public class PostLiveVideoAudit {
    public PostLiveVideoAuditConf conf;
    public PostLiveVideoAuditInput input;
    public PostLiveVideoAuditStorageConf storageConf;
    public String type = "live_video";

    public static class PostLiveVideoAuditConf {
        public String bizType;
        public String callback;
        public int callbackType;
    }

    public static class PostLiveVideoAuditInput {
        public String dataId;
        public String url;
        public PostLiveVideoAuditUserInfo userInfo;
    }

    public static class PostLiveVideoAuditStorageConf {
        public String path;
    }

    public static class PostLiveVideoAuditUserInfo {
        public String appId;
        public String deviceId;
        public String gender;

        /* JADX INFO: renamed from: iP */
        public String f1822iP;
        public String level;
        public String nickname;
        public String receiveTokenId;
        public String role;
        public String room;
        public String tokenId;
        public String type;
    }
}
