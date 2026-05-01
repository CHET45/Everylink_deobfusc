package com.tencent.cos.xml.model.p033ci.p034ai;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class AIDetectPetResponse {
    public List<AIDetectPetResponseResultInfo> resultInfo;

    public static class AIDetectPetResponseLocation {
        public int height;
        public int width;

        /* JADX INFO: renamed from: x */
        public int f1809x;

        /* JADX INFO: renamed from: y */
        public int f1810y;
    }

    public static class AIDetectPetResponseResultInfo {
        public AIDetectPetResponseLocation location;
        public String name;
        public int score;
    }
}
