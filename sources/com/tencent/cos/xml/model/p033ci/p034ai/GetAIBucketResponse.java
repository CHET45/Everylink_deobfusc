package com.tencent.cos.xml.model.p033ci.p034ai;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class GetAIBucketResponse {
    public List<GetAIBucketResponseAiBucketList> aiBucketList;
    public int pageNumber;
    public int pageSize;
    public String requestId;
    public int totalCount;

    public static class GetAIBucketResponseAiBucketList {
        public String bucketId;
        public String createTime;
        public String name;
        public String region;
    }
}
