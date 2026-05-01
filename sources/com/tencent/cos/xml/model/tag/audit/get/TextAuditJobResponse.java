package com.tencent.cos.xml.model.tag.audit.get;

import com.tencent.cos.xml.model.tag.audit.bean.AuditJobsDetail;
import com.tencent.cos.xml.model.tag.audit.bean.AuditScenarioInfo;
import com.tencent.cos.xml.model.tag.audit.bean.AuditSection;
import com.tencent.cos.xml.model.tag.audit.bean.TextAuditScenarioInfo;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class TextAuditJobResponse {
    public TextAuditJobsDetail jobsDetail;
    public String requestId;

    public static class Section extends AuditSection {
        public TextAuditScenarioInfo abuseInfo;
        public TextAuditScenarioInfo illegalInfo;
        public String label;
        public int result;
        public int startByte;
    }

    public static class TextAuditJobsDetail extends AuditJobsDetail {
        public AuditScenarioInfo abuseInfo;
        public AuditScenarioInfo adsInfo;
        public String content;
        public AuditScenarioInfo illegalInfo;
        public AuditScenarioInfo politicsInfo;
        public AuditScenarioInfo pornInfo;
        public List<Section> section;
        public int sectionCount;
        public AuditScenarioInfo terrorismInfo;
    }
}
