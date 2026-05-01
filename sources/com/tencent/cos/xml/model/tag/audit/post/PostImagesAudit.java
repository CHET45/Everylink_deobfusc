package com.tencent.cos.xml.model.tag.audit.post;

import com.tencent.cos.xml.model.tag.audit.bean.AuditConf;
import com.tencent.cos.xml.model.tag.audit.bean.AuditEncryption;
import com.tencent.cos.xml.model.tag.audit.bean.AuditInput;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class PostImagesAudit {
    public List<ImagesAuditInput> input = new ArrayList();
    public AuditConf conf = new AuditConf();

    public static class ImagesAuditInput extends AuditInput {
        public String content;
        public AuditEncryption encryption;
        public int interval;
        public int largeImageDetect;
        public int maxFrames;
    }
}
