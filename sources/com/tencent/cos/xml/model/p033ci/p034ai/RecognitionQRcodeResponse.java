package com.tencent.cos.xml.model.p033ci.p034ai;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class RecognitionQRcodeResponse {
    public int codeStatus;
    public RecognitionQRcodeResponseQRcodeInfo qRcodeInfo;
    public String resultImage;

    public static class RecognitionQRcodeResponseCodeLocation {
        public String point;
    }

    public static class RecognitionQRcodeResponseQRcodeInfo {
        public List<RecognitionQRcodeResponseCodeLocation> codeLocation;
        public String codeUrl;
    }
}
