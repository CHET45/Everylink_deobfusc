package com.tencent.cos.xml.model.tag.audit.bean;

/* JADX INFO: loaded from: classes4.dex */
public class ImageAuditJobsDetail extends AuditJobsDetail {
    public ImagesAuditScenarioInfo adsInfo;
    public int compressionResult;
    public String object;
    public ImagesAuditScenarioInfo politicsInfo;
    public ImagesAuditScenarioInfo pornInfo;
    public ImagesAuditScenarioInfo qualityInfo;
    public int score;
    public ImagesAuditScenarioInfo terrorismInfo;
    public String text;
    public String url;

    public static class ImagesAuditScenarioInfo extends ImageAuditScenarioInfo {
        public int code;
        public String label;
        public String msg;
    }
}
