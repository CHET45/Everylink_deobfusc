package com.tencent.cos.xml.model.p033ci.p034ai;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class GetSearchImageResponse {
    public int count;
    public List<GetSearchImageResponseImageInfos> imageInfos;
    public String requestId;

    public static class GetSearchImageResponseImageInfos {
        public String customContent;
        public String entityId;
        public String picName;
        public int score;
        public String tags;
    }
}
