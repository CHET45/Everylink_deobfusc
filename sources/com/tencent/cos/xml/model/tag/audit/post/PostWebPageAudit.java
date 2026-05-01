package com.tencent.cos.xml.model.tag.audit.post;

import com.tencent.cos.xml.model.tag.audit.bean.AuditConf;
import com.tencent.cos.xml.model.tag.audit.bean.AuditInput;

/* JADX INFO: loaded from: classes4.dex */
public class PostWebPageAudit {
    public AuditInput input = new AuditInput();
    public WebPageAuditConf conf = new WebPageAuditConf();

    public static class WebPageAuditConf extends AuditConf {
        public boolean returnHighlightHtml;
    }
}
