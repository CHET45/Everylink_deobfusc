package com.tencent.cos.xml.model.p033ci.metainsight;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SearchImageResponse {
    public List<ImageResult> imageResult;
    public String requestId;

    public static class ImageResult {
        public int score;
        public String uRI;
    }
}
