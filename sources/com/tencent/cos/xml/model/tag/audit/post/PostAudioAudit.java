package com.tencent.cos.xml.model.tag.audit.post;

import com.tencent.cos.xml.model.tag.audit.bean.AuditConf;
import com.tencent.cos.xml.model.tag.audit.bean.AuditInput;

/* JADX INFO: loaded from: classes4.dex */
public class PostAudioAudit {
    public AuditInput input = new AuditInput();
    public AudioAuditConf conf = new AudioAuditConf();

    public static class AudioAuditConf extends AuditConf {
        public String callbackVersion;
    }
}
