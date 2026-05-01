package com.tencent.cos.xml.model.p033ci.metainsight;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class DatasetFaceSearchResponse {
    public List<FaceResult> faceResult;
    public String requestId;

    public static class FaceBoundary {
        public int height;
        public int left;

        /* JADX INFO: renamed from: top, reason: collision with root package name */
        public int f2367top;
        public int width;
    }

    public static class FaceInfos {
        public FaceBoundary faceBoundary;
        public String faceId;
        public String personId;
        public int score;
        public String uRI;
    }

    public static class FaceResult {
        public List<FaceInfos> faceInfos;
        public FaceBoundary inputFaceBoundary;
    }
}
