package com.tencent.cos.xml.model.p033ci.p034ai;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class AIDetectCarResponse {
    public List<AIDetectCarResponseCarTags> carTags;
    public String requestId;

    public static class AIDetectCarResponseCarLocation {

        /* JADX INFO: renamed from: x */
        public int f1803x;

        /* JADX INFO: renamed from: y */
        public int f1804y;
    }

    public static class AIDetectCarResponseCarTags {
        public String brand;
        public List<AIDetectCarResponseCarLocation> carLocation;
        public String color;
        public int confidence;
        public List<AIDetectCarResponsePlateContent> plateContent;
        public String serial;
        public String type;
        public int year;
    }

    public static class AIDetectCarResponsePlateContent {
        public String color;
        public String plate;
        public List<AIDetectCarResponsePlateLocation> plateLocation;
        public String type;
    }

    public static class AIDetectCarResponsePlateLocation {

        /* JADX INFO: renamed from: x */
        public int f1805x;

        /* JADX INFO: renamed from: y */
        public int f1806y;
    }
}
