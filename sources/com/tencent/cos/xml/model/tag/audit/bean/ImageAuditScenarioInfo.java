package com.tencent.cos.xml.model.tag.audit.bean;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class ImageAuditScenarioInfo {
    public String category;
    public int hitFlag;
    public List<LibResults> libResults;
    public List<ObjectResults> objectResults;
    public List<AuditOcrResults> ocrResults;
    public int score;
    public String subLabel;

    public static class LibResults {
        public String imageId;
        public int score;
    }

    public static class ObjectResults {
        public AuditOcrLocation location;
        public String name;
        public String subLabel;
    }
}
