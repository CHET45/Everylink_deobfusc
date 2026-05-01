package com.tencent.cos.xml.model.p033ci.media;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class GetWorkflowListResponse {
    public List<GetWorkflowListResponseMediaWorkflowList> mediaWorkflowList;
    public int pageNumber;
    public int pageSize;
    public String requestId;
    public int totalCount;

    public static class GetWorkflowListResponseMediaWorkflowList {
        public String createTime;
        public String name;
        public String state;
        public String updateTime;
        public String workflowId;
    }
}
