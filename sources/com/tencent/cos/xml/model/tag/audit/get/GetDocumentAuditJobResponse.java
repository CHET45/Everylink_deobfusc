package com.tencent.cos.xml.model.tag.audit.get;

import com.tencent.cos.xml.model.tag.audit.bean.AuditJobsDetail;
import com.tencent.cos.xml.model.tag.audit.bean.ImageAuditScenarioInfo;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class GetDocumentAuditJobResponse {
    public DocumentAuditJobsDetail jobsDetail;
    public String requestId;

    public static class DocumentAuditJobsDetail extends AuditJobsDetail {
        public Labels labels;
        public String object;
        public int pageCount;
        public PageSegment pageSegment;
        public int suggestion;
        public String url;
    }

    public static class DocumentAuditScenarioInfo {
        public int hitFlag;
        public int score;
    }

    public static class Labels {
        public DocumentAuditScenarioInfo adsInfo;
        public DocumentAuditScenarioInfo politicsInfo;
        public DocumentAuditScenarioInfo pornInfo;
        public DocumentAuditScenarioInfo terrorismInfo;
    }

    public static class PageSegment {
        public List<Results> results;
    }

    public static class Results {
        public ImageAuditScenarioInfo adsInfo;
        public String label;
        public int pageNumber;
        public ImageAuditScenarioInfo politicsInfo;
        public ImageAuditScenarioInfo pornInfo;
        public int sheetNumber;
        public int suggestion;
        public ImageAuditScenarioInfo terrorismInfo;
        public String text;
        public String url;
    }
}
