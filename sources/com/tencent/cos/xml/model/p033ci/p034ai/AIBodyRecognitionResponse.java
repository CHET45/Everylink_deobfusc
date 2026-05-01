package com.tencent.cos.xml.model.p033ci.p034ai;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class AIBodyRecognitionResponse {
    public List<AIBodyRecognitionPedestrianInfo> pedestrianInfo;
    public int status;

    public static class AIBodyRecognitionLocation {
        public List<String> point;
    }

    public static class AIBodyRecognitionPedestrianInfo {
        public List<AIBodyRecognitionLocation> location;
        public String name;
        public String score;
    }
}
