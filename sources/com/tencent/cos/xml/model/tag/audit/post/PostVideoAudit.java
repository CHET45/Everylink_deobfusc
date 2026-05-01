package com.tencent.cos.xml.model.tag.audit.post;

import com.tencent.cos.xml.model.tag.audit.bean.AuditConf;
import com.tencent.cos.xml.model.tag.audit.bean.AuditEncryption;
import com.tencent.cos.xml.model.tag.audit.bean.AuditInput;

/* JADX INFO: loaded from: classes4.dex */
public class PostVideoAudit {
    public VideoAuditInput input = new VideoAuditInput();
    public VideoAuditConf conf = new VideoAuditConf();

    public static class Snapshot {
        public int count;
        public String mode;
        public float timeInterval;
    }

    public static class VideoAuditConf extends AuditConf {
        public String callbackVersion;
        public int detectContent;
        public Snapshot snapshot = new Snapshot();
    }

    public static class VideoAuditInput extends AuditInput {
        public AuditEncryption encryption;
    }
}
