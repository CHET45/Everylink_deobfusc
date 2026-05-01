package com.tencent.cos.xml.model.tag.audit.post;

import com.tencent.cos.xml.model.tag.audit.bean.AuditConf;
import com.tencent.cos.xml.model.tag.audit.bean.AuditInput;

/* JADX INFO: loaded from: classes4.dex */
public class PostTextAudit {
    public TextAuditInput input = new TextAuditInput();
    public TextAuditConf conf = new TextAuditConf();

    public static class TextAuditConf extends AuditConf {
        public String callbackVersion;
    }

    public static class TextAuditInput extends AuditInput {
        public String content;
    }
}
