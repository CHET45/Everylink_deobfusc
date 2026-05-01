package com.tencent.cos.xml.model.tag.audit.get;

import com.tencent.cos.xml.model.tag.audit.bean.AuditJobsDetail;
import com.tencent.cos.xml.model.tag.audit.bean.AuditSection;
import com.tencent.cos.xml.model.tag.audit.bean.TextAuditScenarioInfo;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class GetAudioAuditJobResponse {
    public AudioAuditJobsDetail jobsDetail;
    public String requestId;

    public static class AudioAuditJobsDetail extends AuditJobsDetail {
        public AudioAuditScenarioInfo adsInfo;
        public String audioText;
        public String bucketId;
        public Map<String, String> cosHeaders;
        public String object;
        public AudioAuditScenarioInfo politicsInfo;
        public AudioAuditScenarioInfo pornInfo;
        public String region;
        public List<AudioSection> section;
        public AudioAuditScenarioInfo terrorismInfo;
        public String url;
    }

    public static class AudioAuditScenarioInfo {
        public String category;
        public int hitFlag;
        public String label;
        public int score;
        public String subLabel;
    }

    public static class AudioSection extends AuditSection {
        public int duration;
        public String label;
        public List<TextAuditScenarioInfo.Results> languageResults;
        public int offsetTime;
        public int result;
        public String subLabel;
        public String text;
        public String url;
    }
}
