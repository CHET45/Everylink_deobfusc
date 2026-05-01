package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.blob.BlobConstants;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateSnapshot {
    public String name;
    public TemplateSnapshotSnapshot snapshot;
    public String tag = BlobConstants.SNAPSHOT_ELEMENT;

    public static class TemplateSnapshotSnapshot {
        public String blackLevel;
        public String cIParam;
        public String count;
        public String height;
        public String isCheckBlack;
        public String isCheckCount;
        public String mode;
        public String pixelBlackThreshold;
        public String snapshotOutMode;
        public TemplateSnapshotSpriteSnapshotConfig spriteSnapshotConfig;
        public String start;
        public String timeInterval;
        public String width;
    }

    public static class TemplateSnapshotSpriteSnapshotConfig {
        public String cellHeight;
        public String cellWidth;
        public String color;
        public String columns;
        public String lines;
        public String margin;
        public String padding;
        public String scaleMethod;
    }
}
