package com.tencent.cos.xml.model.tag;

import com.github.houbb.heaven.util.util.PlaceholderUtil;

/* JADX INFO: loaded from: classes4.dex */
public class BucketLoggingStatus {
    public LoggingEnabled loggingEnabled;

    public String toString() {
        StringBuilder sb = new StringBuilder("{BucketLoggingStatus:\n");
        LoggingEnabled loggingEnabled = this.loggingEnabled;
        if (loggingEnabled != null) {
            sb.append(loggingEnabled.toString()).append("\n");
        }
        sb.append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
        return sb.toString();
    }

    public static class LoggingEnabled {
        public String targetBucket;
        public String targetPrefix;

        public String toString() {
            StringBuilder sb = new StringBuilder("{LoggingEnabled:\nTargetBucket:");
            sb.append(this.targetBucket).append("\nTargetPrefix:");
            sb.append(this.targetPrefix).append("\n}");
            return sb.toString();
        }
    }
}
