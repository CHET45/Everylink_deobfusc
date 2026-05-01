package com.tencent.cos.xml.model.p033ci.p034ai;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class RecognizeLogoResponse {
    public List<RecognizeLogoResponseLogoInfo> logoInfo;
    public int status;

    public static class RecognizeLogoResponseLocation {
        public String point;
    }

    public static class RecognizeLogoResponseLogoInfo {
        public List<RecognizeLogoResponseLocation> location;
        public String name;
        public int score;
    }
}
