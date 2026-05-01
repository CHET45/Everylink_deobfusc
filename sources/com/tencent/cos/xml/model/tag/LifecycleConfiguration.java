package com.tencent.cos.xml.model.tag;

import com.github.houbb.heaven.util.util.PlaceholderUtil;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class LifecycleConfiguration {
    public List<Rule> rules;

    public String toString() {
        StringBuilder sb = new StringBuilder("{LifecycleConfiguration:\n");
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
        public AbortIncompleteMultiUpload abortIncompleteMultiUpload;
        public Expiration expiration;
        public Filter filter;

        /* JADX INFO: renamed from: id */
        public String f1835id;
        public NoncurrentVersionExpiration noncurrentVersionExpiration;
        public NoncurrentVersionTransition noncurrentVersionTransition;
        public String status;
        public Transition transition;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Rule:\nId:");
            sb.append(this.f1835id).append("\n");
            Filter filter = this.filter;
            if (filter != null) {
                sb.append(filter.toString()).append("\n");
            }
            sb.append("Status:").append(this.status).append("\n");
            Transition transition = this.transition;
            if (transition != null) {
                sb.append(transition.toString()).append("\n");
            }
            Expiration expiration = this.expiration;
            if (expiration != null) {
                sb.append(expiration.toString()).append("\n");
            }
            NoncurrentVersionExpiration noncurrentVersionExpiration = this.noncurrentVersionExpiration;
            if (noncurrentVersionExpiration != null) {
                sb.append(noncurrentVersionExpiration.toString()).append("\n");
            }
            NoncurrentVersionTransition noncurrentVersionTransition = this.noncurrentVersionTransition;
            if (noncurrentVersionTransition != null) {
                sb.append(noncurrentVersionTransition.toString()).append("\n");
            }
            AbortIncompleteMultiUpload abortIncompleteMultiUpload = this.abortIncompleteMultiUpload;
            if (abortIncompleteMultiUpload != null) {
                sb.append(abortIncompleteMultiUpload.toString()).append("\n");
            }
            sb.append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
            return sb.toString();
        }
    }

    public static class Filter {
        public String prefix;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Filter:\nPrefix:");
            sb.append(this.prefix).append("\n}");
            return sb.toString();
        }
    }

    public static class Transition {
        public String date;
        public int days;
        public String storageClass;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Transition:\nDays:");
            sb.append(this.days).append("\nDate:");
            sb.append(this.date).append("\nStorageClass:");
            sb.append(this.storageClass).append("\n}");
            return sb.toString();
        }
    }

    public static class Expiration {
        public String date;
        public int days;
        public String expiredObjectDeleteMarker;

        public String toString() {
            StringBuilder sb = new StringBuilder("{Expiration:\nDays:");
            sb.append(this.days).append("\nDate:");
            sb.append(this.date).append("\nExpiredObjectDeleteMarker:");
            sb.append(this.expiredObjectDeleteMarker).append("\n}");
            return sb.toString();
        }
    }

    public static class NoncurrentVersionExpiration {
        public int noncurrentDays;

        public String toString() {
            StringBuilder sb = new StringBuilder("{NoncurrentVersionExpiration:\nNoncurrentDays:");
            sb.append(this.noncurrentDays).append("\n}");
            return sb.toString();
        }
    }

    public static class NoncurrentVersionTransition {
        public int noncurrentDays;
        public String storageClass;

        public String toString() {
            StringBuilder sb = new StringBuilder("{NoncurrentVersionTransition:\nNoncurrentDays:");
            sb.append(this.noncurrentDays).append("\nStorageClass:");
            sb.append(this.storageClass).append("\n}");
            return sb.toString();
        }
    }

    public static class AbortIncompleteMultiUpload {
        public int daysAfterInitiation;

        public String toString() {
            StringBuilder sb = new StringBuilder("{AbortIncompleteMultiUpload:\nDaysAfterInitiation:");
            sb.append(this.daysAfterInitiation).append("\n}");
            return sb.toString();
        }
    }
}
