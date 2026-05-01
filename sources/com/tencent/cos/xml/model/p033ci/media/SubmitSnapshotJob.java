package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.p033ci.media.TemplateSnapshot;
import com.tencent.cos.xml.model.tag.CallBackMqConfig;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitSnapshotJob {
    public String callBack;
    public String callBackFormat;
    public CallBackMqConfig callBackMqConfig;
    public String callBackType;
    public SubmitSnapshotJobInput input;
    public SubmitSnapshotJobOperation operation;
    public String tag = BlobConstants.SNAPSHOT_ELEMENT;

    public static class SubmitSnapshotJobInput {
        public String object;
    }

    public static class SubmitSnapshotJobOperation {
        public String jobLevel;
        public SubmitSnapshotJobOutput output;
        public TemplateSnapshot.TemplateSnapshotSnapshot snapshot;
        public String templateId;
        public String userData;
    }

    public static class SubmitSnapshotJobOutput {
        public String bucket;
        public String object;
        public String region;
        public String spriteObject;
    }
}
