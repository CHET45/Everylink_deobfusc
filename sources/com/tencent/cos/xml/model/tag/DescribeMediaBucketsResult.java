package com.tencent.cos.xml.model.tag;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class DescribeMediaBucketsResult {
    public List<MediaBucketList> mediaBucketList;
    public int pageNumber;
    public int pageSize;
    public String requestId;
    public int totalCount;

    public static class MediaBucketList {
        public String bucketId;
        public String createTime;
        public String name;
        public String region;
    }
}
