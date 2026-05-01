package com.tencent.cos.xml.model.tag;

import com.tencent.cos.xml.model.tag.audit.bean.AuditLibResults;
import com.tencent.cos.xml.model.tag.audit.bean.AuditOcrResults;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class RecognitionResult {
    public AdsInfo adsInfo;
    public String category;
    public int compressionResult;
    public String dataId;
    public String jobId;
    public String label;
    public String object;
    public PoliticsInfo politicsInfo;
    public PornInfo pornInfo;
    public QualityInfo qualityInfo;
    public int result;
    public int score;
    public String state;
    public String subLabel;
    public TerroristInfo terroristInfo;
    public String text;
    public String url;

    public static class AdsInfo {
        public String category;
        public int code;
        public int hitFlag;
        public String label;
        public List<AuditLibResults> libResults;
        public String msg;
        public List<AuditOcrResults> ocrResults;
        public int score;
        public String subLabel;
    }

    public static class PoliticsInfo {
        public String category;
        public int code;
        public int hitFlag;
        public String label;
        public List<AuditLibResults> libResults;
        public String msg;
        public List<AuditOcrResults> ocrResults;
        public int score;
        public String subLabel;
    }

    public static class PornInfo {
        public String category;
        public int code;
        public int hitFlag;
        public String label;
        public List<AuditLibResults> libResults;
        public String msg;
        public List<AuditOcrResults> ocrResults;
        public int score;
        public String subLabel;
    }

    public static class QualityInfo {
        public String category;
        public int code;
        public int hitFlag;
        public String label;
        public List<AuditLibResults> libResults;
        public String msg;
        public List<AuditOcrResults> ocrResults;
        public int score;
        public String subLabel;
    }

    public static class TerroristInfo {
        public String category;
        public int code;
        public int hitFlag;
        public String label;
        public List<AuditLibResults> libResults;
        public String msg;
        public List<AuditOcrResults> ocrResults;
        public int score;
        public String subLabel;
    }
}
