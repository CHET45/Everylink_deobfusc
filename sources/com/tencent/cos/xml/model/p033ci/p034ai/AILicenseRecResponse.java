package com.tencent.cos.xml.model.p033ci.p034ai;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class AILicenseRecResponse {
    public List<AILicenseRecResponseIdInfo> idInfo;
    public int status;

    public static class AILicenseRecResponseIdInfo {
        public String detectedText;
        public List<AILicenseRecResponseLocation> location;
        public String name;
        public int score;
    }

    public static class AILicenseRecResponseLocation {
        public String point;
    }
}
