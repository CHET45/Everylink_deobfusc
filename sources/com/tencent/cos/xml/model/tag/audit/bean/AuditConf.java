package com.tencent.cos.xml.model.tag.audit.bean;

/* JADX INFO: loaded from: classes4.dex */
public class AuditConf {
    public int async;
    public String bizType;
    public String callback;
    public int callbackType;
    public String detectType;
    public Freeze freeze;

    public static class Freeze {
        public int abuseScore;
        public int adsScore;
        public int illegalScore;
        public int pornScore;
    }
}
