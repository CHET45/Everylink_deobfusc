package com.tencent.cos.xml.model.tag;

import com.github.houbb.heaven.util.util.PlaceholderUtil;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class ReplicationConfiguration {
    public String role;
    public List<Rule> rules;

    public String toString() {
        StringBuilder sb = new StringBuilder("{ReplicationConfiguration:\nRole:");
        sb.append(this.role).append("\n");
        List<Rule> list = this.rules;
        if (list != null) {
            for (Rule rule : list) {
                if (rule != null) {
                    sb.append(rule.toString()).append("\n");
                }
            }
        }
        sb.append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
        return sb.toString();
    }

    public static class Rule {
        public Destination destination;

        /* JADX INFO: renamed from: id */
        public String f1843id;
        public String prefix;
        public String status;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Rule:\nId:");
            sb.append(this.f1843id).append("\nStatus:");
            sb.append(this.status).append("\nPrefix:");
            sb.append(this.prefix).append("\n");
            Destination destination = this.destination;
            if (destination != null) {
                sb.append(destination.toString()).append("\n");
            }
            sb.append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
            return sb.toString();
        }
    }

    public static class Destination {
        public String bucket;
        public String storageClass;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Destination:\nBucket:");
            sb.append(this.bucket).append("\nStorageClass:");
            sb.append(this.storageClass).append("\n}");
            return sb.toString();
        }
    }
}
