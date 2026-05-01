package com.tencent.cos.xml.model.tag.audit.bean;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class TextAuditScenarioInfo {
    public String category;
    public int hitFlag;
    public String keywords;
    public List<LibResults> libResults;
    public List<Results> recognitionResults;
    public int score;
    public List<Results> speakerResults;
    public String subLabel;

    public static class LibResults {
        public List<String> keywords;
        public String libName;
        public int libType;
    }

    public static class Results {
        public int endTime;
        public String label;
        public int score;
        public int startTime;
    }
}
