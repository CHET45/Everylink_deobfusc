package com.tencent.cos.xml.model.tag.audit.post;

import com.tencent.cos.xml.model.tag.audit.bean.AuditConf;
import com.tencent.cos.xml.model.tag.audit.bean.AuditInput;

/* JADX INFO: loaded from: classes4.dex */
public class PostDocumentAudit {
    public DocumentAuditInput input = new DocumentAuditInput();
    public AuditConf conf = new AuditConf();

    public static class DocumentAuditInput extends AuditInput {
        public String type;
    }
}
